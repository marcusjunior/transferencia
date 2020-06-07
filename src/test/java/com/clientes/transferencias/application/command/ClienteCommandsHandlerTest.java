package com.clientes.transferencias.application.command;

import com.clientes.transferencias.domain.model.cliente.Cliente;
import com.clientes.transferencias.domain.model.cliente.ClienteRepository;
import com.clientes.transferencias.domain.model.cliente.CriarCliente;
import com.clientes.transferencias.port.adapter.repository.cliente.ClienteJpaRepository;
import com.clientes.transferencias.port.adapter.repository.cliente.ClienteRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.internal.InOrderImpl;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ClienteCommandsHandlerTest {

    private ClienteCommandsHandler clienteCommandsHandler;
    private ClienteJpaRepository clienteJpaRepository;
    private ClienteRepository clienteRepository;

    private static final String MSG_ERRO_NUMERO_CONTA = "O número da conta informado já está sendo utilizado.";
    private static final String MSG_ERRO_ID_UTILIZADO = "O ID informado já está sendo utilizado";

    @BeforeEach
    public void initialize(){
        this.clienteJpaRepository = mock(ClienteJpaRepository.class);
        this.clienteRepository = new ClienteRepositoryImpl(clienteJpaRepository);
        this.clienteCommandsHandler = new ClienteCommandsHandler(clienteRepository);
    }

    @Test
    public void criarNovoClienteSucesso() throws Exception {

        final Cliente clienteEsperado = ClienteCommandsHandlerMockFactory.cliente();

        final CriarCliente criarCliente = ClienteCommandsHandlerMockFactory.criarCliente();

        doReturn(false)
                .when(this.clienteJpaRepository)
                .existsById(criarCliente.getId());


        doReturn(clienteEsperado)
                .when(this.clienteJpaRepository)
                .save(clienteEsperado);

        final Optional<Cliente> clienteResultado = this
                .clienteCommandsHandler
                .handle(criarCliente);

        assertEquals(clienteEsperado, clienteResultado.get());

        InOrder emOrdem = new InOrderImpl(Collections.singletonList(this.clienteJpaRepository));
        emOrdem.verify(this.clienteJpaRepository).existsById(eq(criarCliente.getId()));
        emOrdem.verify(this.clienteJpaRepository).save(eq(clienteEsperado));
        emOrdem.verifyNoMoreInteractions();
    }

    @Test
    public void criarNovoClienteErroClienteIdJaUtilizado() {

        final CriarCliente criarCliente = ClienteCommandsHandlerMockFactory.criarCliente();

        doReturn(true)
                .when(this.clienteJpaRepository)
                .existsById(criarCliente.getId());

        try{

            this
                .clienteCommandsHandler
                .handle(criarCliente);

        }catch(Exception ex){
            assertEquals(ex.getMessage(), MSG_ERRO_ID_UTILIZADO);

            InOrder emOrdem = new InOrderImpl(Collections.singletonList(this.clienteJpaRepository));
            emOrdem.verify(this.clienteJpaRepository).existsById(eq(criarCliente.getId()));
            emOrdem.verifyNoMoreInteractions();
        }
    }

    @Test
    public void criarNovoClienteErroContaJaUtilizada() {

        final Cliente clienteEsperado = ClienteCommandsHandlerMockFactory.cliente();

        final CriarCliente criarCliente = ClienteCommandsHandlerMockFactory.criarCliente();

        doReturn(false)
                .when(this.clienteJpaRepository)
                .existsById(criarCliente.getId());

        doThrow(new DataIntegrityViolationException(MSG_ERRO_NUMERO_CONTA))
                .when(this.clienteJpaRepository)
                .save(clienteEsperado);

        try{

            this
                .clienteCommandsHandler
                .handle(criarCliente);

        }catch(Exception ex){
            assertEquals(ex.getMessage(), MSG_ERRO_NUMERO_CONTA);

            InOrder emOrdem = new InOrderImpl(Collections.singletonList(this.clienteJpaRepository));
            emOrdem.verify(this.clienteJpaRepository).existsById(eq(criarCliente.getId()));
            emOrdem.verify(this.clienteJpaRepository).save(eq(clienteEsperado));
            emOrdem.verifyNoMoreInteractions();
        }
    }

}
