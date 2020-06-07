package com.clientes.transferencias.application.command;

import com.clientes.transferencias.domain.model.cliente.Cliente;
import com.clientes.transferencias.domain.model.cliente.ClienteRepository;
import com.clientes.transferencias.domain.model.transferencia.RealizarTransferencia;
import com.clientes.transferencias.domain.model.transferencia.Situacao;
import com.clientes.transferencias.domain.model.transferencia.Transferencia;
import com.clientes.transferencias.domain.model.transferencia.TransferenciaRepository;
import com.clientes.transferencias.port.adapter.repository.cliente.ClienteJpaRepository;
import com.clientes.transferencias.port.adapter.repository.cliente.ClienteRepositoryImpl;
import com.clientes.transferencias.port.adapter.repository.transferencia.TransferenciaJpaRepository;
import com.clientes.transferencias.port.adapter.repository.transferencia.TransferenciaRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.internal.InOrderImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class TransferenciaCommandsHandlerTest {

    private TransferenciaCommandsHandler transferenciaCommandsHandler;
    private ClienteJpaRepository clienteJpaRepository;
    private ClienteRepository clienteRepository;
    private TransferenciaJpaRepository transferenciaJpaRepository;
    private TransferenciaRepository transferenciaRepository;

    private static final String MSG_ERRO_CLIENTE_ORIGEM = "O cliente origem não foi encontrado";
    private static final String MSG_ERRO_CLIENTE_DESTINO = "O cliente destino não foi encontrado";

    @BeforeEach
    public void initialize(){
        this.transferenciaJpaRepository = mock(TransferenciaJpaRepository.class);
        this.transferenciaRepository = new TransferenciaRepositoryImpl(transferenciaJpaRepository);
        this.clienteJpaRepository = mock(ClienteJpaRepository.class);
        this.clienteRepository = new ClienteRepositoryImpl(clienteJpaRepository);
        this.transferenciaCommandsHandler = new TransferenciaCommandsHandler(transferenciaRepository, clienteRepository);
    }

    @Test
    public void realizarTransferencia() {

        final Cliente clienteOrigem = TransferenciaCommandsHandlerMockFactory.clienteOrigem();
        final Cliente clienteDestino = TransferenciaCommandsHandlerMockFactory.clienteDestino();

        final RealizarTransferencia realizarTransferencia =
                TransferenciaCommandsHandlerMockFactory.realizarTransferencia(300D);

        final Transferencia transferenciaEsperada =
                TransferenciaCommandsHandlerMockFactory.transferencia(300D, realizarTransferencia.getData(), Situacao.CONCLUIDO_COM_SUCESSO);

        doReturn(Optional.of(clienteOrigem))
                .when(this.clienteJpaRepository)
                .findByNumeroConta(realizarTransferencia.getNumeroContaOrigem());

        doReturn(Optional.of(clienteDestino))
                .when(this.clienteJpaRepository)
                .findByNumeroConta(realizarTransferencia.getNumeroContaDestino());

        doReturn(transferenciaEsperada)
                .when(this.transferenciaJpaRepository)
                .save(transferenciaEsperada);

        final Optional<Transferencia> transferenciaResultado = this
                .transferenciaCommandsHandler
                .handle(realizarTransferencia);

        assertEquals(transferenciaEsperada, transferenciaResultado.get());

        List<Object> mocks = new ArrayList<>();
        mocks.add(this.clienteJpaRepository);
        mocks.add(this.transferenciaJpaRepository);

        InOrder emOrdem = new InOrderImpl(mocks);
        emOrdem.verify(this.clienteJpaRepository).findByNumeroConta(eq(clienteOrigem.getNumeroConta()));
        emOrdem.verify(this.clienteJpaRepository).findByNumeroConta(eq(clienteDestino.getNumeroConta()));
        emOrdem.verify(this.clienteJpaRepository).save(eq(clienteOrigem));
        emOrdem.verify(this.clienteJpaRepository).save(eq(clienteDestino));
        emOrdem.verify(this.transferenciaJpaRepository).save(eq(transferenciaEsperada));
        emOrdem.verifyNoMoreInteractions();
    }

    @Test
    public void realizarTransferenciaComErroValorExcedido() {

        final Cliente clienteOrigem = TransferenciaCommandsHandlerMockFactory.clienteOrigem();
        final Cliente clienteDestino = TransferenciaCommandsHandlerMockFactory.clienteDestino();

        final RealizarTransferencia realizarTransferencia =
                TransferenciaCommandsHandlerMockFactory.realizarTransferencia(3000D);

        final Transferencia transferenciaEsperada =
                TransferenciaCommandsHandlerMockFactory.transferencia(
                    3000D, realizarTransferencia.getData(), Situacao.CONCLUIDO_COM_ERRO);

        doReturn(Optional.of(clienteOrigem))
                .when(this.clienteJpaRepository)
                .findByNumeroConta(realizarTransferencia.getNumeroContaOrigem());

        doReturn(Optional.of(clienteDestino))
                .when(this.clienteJpaRepository)
                .findByNumeroConta(realizarTransferencia.getNumeroContaDestino());

        doReturn(transferenciaEsperada)
                .when(this.transferenciaJpaRepository)
                .save(transferenciaEsperada);

        final Optional<Transferencia> transferenciaResultado = this
                .transferenciaCommandsHandler
                .handle(realizarTransferencia);

        assertEquals(transferenciaEsperada, transferenciaResultado.get());

        List<Object> mocks = new ArrayList<>();
        mocks.add(this.clienteJpaRepository);
        mocks.add(this.transferenciaJpaRepository);

        InOrder emOrdem = new InOrderImpl(mocks);
        emOrdem.verify(this.clienteJpaRepository).findByNumeroConta(eq(clienteOrigem.getNumeroConta()));
        emOrdem.verify(this.clienteJpaRepository).findByNumeroConta(eq(clienteDestino.getNumeroConta()));
        emOrdem.verify(this.transferenciaJpaRepository).save(eq(transferenciaEsperada));
        emOrdem.verifyNoMoreInteractions();
    }

    @Test
    public void realizarTransferenciaComErroSemSaldoDisponivel() {

        final Cliente clienteOrigem = TransferenciaCommandsHandlerMockFactory.clienteOrigem();
        final Cliente clienteDestino = TransferenciaCommandsHandlerMockFactory.clienteDestino();

        final RealizarTransferencia realizarTransferencia =
                TransferenciaCommandsHandlerMockFactory.realizarTransferencia(950D);

        final Transferencia transferenciaEsperada =
                TransferenciaCommandsHandlerMockFactory.transferencia(
                        950D, realizarTransferencia.getData(), Situacao.CONCLUIDO_COM_ERRO);

        doReturn(Optional.of(clienteOrigem))
                .when(this.clienteJpaRepository)
                .findByNumeroConta(realizarTransferencia.getNumeroContaOrigem());

        doReturn(Optional.of(clienteDestino))
                .when(this.clienteJpaRepository)
                .findByNumeroConta(realizarTransferencia.getNumeroContaDestino());

        doReturn(transferenciaEsperada)
                .when(this.transferenciaJpaRepository)
                .save(transferenciaEsperada);

        final Optional<Transferencia> transferenciaResultado = this
                .transferenciaCommandsHandler
                .handle(realizarTransferencia);

        assertEquals(transferenciaEsperada, transferenciaResultado.get());

        List<Object> mocks = new ArrayList<>();
        mocks.add(this.clienteJpaRepository);
        mocks.add(this.transferenciaJpaRepository);

        InOrder emOrdem = new InOrderImpl(mocks);
        emOrdem.verify(this.clienteJpaRepository).findByNumeroConta(eq(clienteOrigem.getNumeroConta()));
        emOrdem.verify(this.clienteJpaRepository).findByNumeroConta(eq(clienteDestino.getNumeroConta()));
        emOrdem.verify(this.transferenciaJpaRepository).save(eq(transferenciaEsperada));
        emOrdem.verifyNoMoreInteractions();
    }

    @Test
    public void realizarTransferenciaComErroClienteOrigemInexistente() {

        final RealizarTransferencia realizarTransferencia =
                TransferenciaCommandsHandlerMockFactory.realizarTransferencia(950D);

        doThrow(new RuntimeException(MSG_ERRO_CLIENTE_ORIGEM))
                .when(this.clienteJpaRepository)
                .findByNumeroConta(realizarTransferencia.getNumeroContaOrigem());

        try {

            this
                .transferenciaCommandsHandler
                .handle(realizarTransferencia);

        } catch (Exception ex) {
            assertEquals(ex.getMessage(), MSG_ERRO_CLIENTE_ORIGEM);

            List<Object> mocks = new ArrayList<>();
            mocks.add(this.clienteJpaRepository);

            InOrder emOrdem = new InOrderImpl(mocks);
            emOrdem.verify(this.clienteJpaRepository).findByNumeroConta(eq(realizarTransferencia.getNumeroContaOrigem()));
            emOrdem.verifyNoMoreInteractions();

        }
    }

    @Test
    public void realizarTransferenciaComErroClienteDestinoInexistente() {

        final Cliente clienteOrigem = TransferenciaCommandsHandlerMockFactory.clienteOrigem();

        final RealizarTransferencia realizarTransferencia =
                TransferenciaCommandsHandlerMockFactory.realizarTransferencia(950D);

        doReturn(Optional.of(clienteOrigem))
                .when(this.clienteJpaRepository)
                .findByNumeroConta(realizarTransferencia.getNumeroContaOrigem());

        doThrow(new RuntimeException(MSG_ERRO_CLIENTE_DESTINO))
                .when(this.clienteJpaRepository)
                .findByNumeroConta(realizarTransferencia.getNumeroContaDestino());

        try {

            this
                .transferenciaCommandsHandler
                .handle(realizarTransferencia);

        } catch (Exception ex) {
            assertEquals(ex.getMessage(), MSG_ERRO_CLIENTE_DESTINO);

            List<Object> mocks = new ArrayList<>();
            mocks.add(this.clienteJpaRepository);

            InOrder emOrdem = new InOrderImpl(mocks);
            emOrdem.verify(this.clienteJpaRepository).findByNumeroConta(eq(realizarTransferencia.getNumeroContaOrigem()));
            emOrdem.verify(this.clienteJpaRepository).findByNumeroConta(eq(realizarTransferencia.getNumeroContaDestino()));
            emOrdem.verifyNoMoreInteractions();
        }
    }

}
