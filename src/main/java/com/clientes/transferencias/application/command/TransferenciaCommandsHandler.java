package com.clientes.transferencias.application.command;

import com.clientes.transferencias.domain.model.cliente.Cliente;
import com.clientes.transferencias.domain.model.cliente.ClienteRepository;
import com.clientes.transferencias.domain.model.transferencia.RealizarTransferencia;
import com.clientes.transferencias.domain.model.transferencia.Situacao;
import com.clientes.transferencias.domain.model.transferencia.Transferencia;
import com.clientes.transferencias.domain.model.transferencia.TransferenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

@Component
public class TransferenciaCommandsHandler {

    private static final String MENSAGEM_ERRO_NAO_ENCONTRADO = "não foi encontrado";
    private static final Double VALOR_MAXIMO_TRANSFERENCIA = 1000D;

    @Autowired
    private TransferenciaRepository transferenciaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public TransferenciaCommandsHandler(TransferenciaRepository transferenciaRepository, ClienteRepository clienteRepository) {
        this.transferenciaRepository = transferenciaRepository;
        this.clienteRepository = clienteRepository;
    }

    /**
     * Realiza a transferencia de saldo entre dois clientes e prepara o resultado da transferencia
     * para ser enviado ao repositorio.
     * @param realizarTransferencia comando os dados para a realizar transferencia
     * @throws RuntimeException caso o cliente de origem não exista na base
     * @throws RuntimeException caso o cliente de destino não exista na base
     * @return um objeto de transferencia para ser persistido
     */
    private Function<RealizarTransferencia, Transferencia> realizacaoTransferencia = realizarTransferencia -> {

        Cliente clienteOrigem =
            clienteRepository
                .buscarPorNumeroConta(realizarTransferencia.getNumeroContaOrigem())
                .orElseThrow(() -> new RuntimeException("O cliente origem " + MENSAGEM_ERRO_NAO_ENCONTRADO));

        Cliente clienteDestino =
            clienteRepository
                .buscarPorNumeroConta(realizarTransferencia.getNumeroContaDestino())
                .orElseThrow(() -> new RuntimeException("O cliente destino " + MENSAGEM_ERRO_NAO_ENCONTRADO));

        Boolean transferenciaHabilitada =
                this.validarTransferencia.apply(clienteOrigem, realizarTransferencia.getValor());

        Boolean transferenciaRealizada = false;

        if(transferenciaHabilitada) {
            Double novoSaldoOrigem = clienteOrigem.getSaldo() - realizarTransferencia.getValor();
            clienteOrigem.setSaldo(
                    new BigDecimal(novoSaldoOrigem).setScale(2, RoundingMode.HALF_UP).doubleValue());
            this.clienteRepository.salvar(clienteOrigem);

            Double novoSaldoDestino = clienteDestino.getSaldo() + realizarTransferencia.getValor();
            clienteDestino.setSaldo(
                    new BigDecimal(novoSaldoDestino).setScale(2, RoundingMode.HALF_UP).doubleValue());
            this.clienteRepository.salvar(clienteDestino);

            transferenciaRealizada = true;
        }

        Situacao situacao =
                transferenciaRealizada
                        ? Situacao.CONCLUIDO_COM_SUCESSO : Situacao.CONCLUIDO_COM_ERRO;

        return Transferencia.builder()
                    .data(realizarTransferencia.getData())
                    .numeroContaOrigem(realizarTransferencia.getNumeroContaOrigem())
                    .numeroContaDestino(realizarTransferencia.getNumeroContaDestino())
                    .valor(realizarTransferencia.getValor())
                    .situacao(situacao)
                    .build();
    };

    /**
     * Valida se a transferencia pode ser realizado com base no saldo disponivel e valor limite
     * @param clienteOrigem cliente de origem da transferencia
     * @param valorTransferencia valor que irá ser transferido
     * @return verdadeiro ou falso dependendo do resultado validacao
     */
    public BiFunction<Cliente, Double, Boolean> validarTransferencia = (clienteOrigem, valorTransferencia) -> {

        Boolean saldoDisponivel = clienteOrigem.getSaldo() >= valorTransferencia;

        Boolean valorLimiteNaoExcedido = valorTransferencia <= VALOR_MAXIMO_TRANSFERENCIA;

        return saldoDisponivel && valorLimiteNaoExcedido;

    };

    /**
     * Manipula o comando de realizar transferencia e envia o resultado para o repositorio
     * @param command comando os dados para realizar transferencia
     * @return um optional com o objeto de transferencia persistido
     */
    public synchronized Optional<Transferencia> handle(RealizarTransferencia command) {

        return Optional.of(command)
                .map(realizacaoTransferencia)
                .flatMap(transferenciaRepository::salvar);
    }
}
