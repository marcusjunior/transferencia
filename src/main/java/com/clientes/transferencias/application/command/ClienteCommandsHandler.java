package com.clientes.transferencias.application.command;

import com.clientes.transferencias.domain.model.cliente.Cliente;
import com.clientes.transferencias.domain.model.cliente.ClienteRepository;
import com.clientes.transferencias.domain.model.cliente.CriarCliente;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.function.Function;

@Component
public class ClienteCommandsHandler {

    private static final String MENSAGEM_ID_ERRO = "O ID informado já está sendo utilizado";

    @Autowired
    private ClienteRepository repository;

    public ClienteCommandsHandler(ClienteRepository repository) {
        this.repository = repository;
    }

    /**
     * Mapeia o cliente informado no comando para o dominio equivalente
     * @param criarCliente comando de criacao de cliente
     * @return o cliente de dominio equivalente
     */
    private Function<CriarCliente, Cliente> criacaoCliente =  criarCliente ->
            Cliente.builder()
                    .id(criarCliente.getId())
                    .numeroConta(criarCliente .getNumeroConta())
                    .saldo(criarCliente.getSaldo())
                    .nome(criarCliente .getNome())
                    .build();

    /**
     * Manipula o comando de criacao de cliente, verifica se o id esta disponivel
     * e o envia para o repositorio para ser persistido
     * @param command comando os dados para criacao de cliente
     * @throws IllegalArgumentException caso o id ja esteja em uso na base
     * @throws DataIntegrityViolationException caso o numero da conta ja exista na base
     * @return um optional com o cliente persistido
     */
    public Optional<Cliente> handle(CriarCliente command) throws Exception {

        Validate.isTrue(
            repository
                .verificarDisponibilidadeID(command.getId()), MENSAGEM_ID_ERRO);

        return Optional.of(command)
                .map(criacaoCliente)
                .flatMap(repository::salvar);
    }

}
