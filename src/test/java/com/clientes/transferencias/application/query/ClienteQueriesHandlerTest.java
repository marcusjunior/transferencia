package com.clientes.transferencias.application.query;

import com.clientes.transferencias.domain.model.cliente.BuscarClientePorNumeroConta;
import com.clientes.transferencias.domain.model.cliente.Cliente;
import com.clientes.transferencias.domain.model.cliente.ClienteRepository;
import com.clientes.transferencias.port.adapter.repository.cliente.ClienteJpaRepository;
import com.clientes.transferencias.port.adapter.repository.cliente.ClienteRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.internal.InOrderImpl;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ClienteQueriesHandlerTest {

    private static final String MSG_ERRO_REPOSITORIO = "Ocorreu um erro no repositorio";
    private static final Integer ZERO = 0;
    private static final Integer UM = 1;

    private ClienteQueriesHandler clienteQueriesHandler;
    private ClienteJpaRepository clienteJpaRepository;
    private ClienteRepository clienteRepository;

    @BeforeEach
    public void initialize(){
        this.clienteJpaRepository = mock(ClienteJpaRepository.class);
        this.clienteRepository = new ClienteRepositoryImpl(clienteJpaRepository);
        this.clienteQueriesHandler = new ClienteQueriesHandler(clienteRepository);
    }

    @Test
    public void buscarPorNumeroContaSucesso(){

        final Cliente clienteEsperado = ClienteQueriesHandlerMockFactory.cliente();

        final BuscarClientePorNumeroConta consultaPorNumeroConta =
                ClienteQueriesHandlerMockFactory.buscarClientePorNumeroConta();

        doReturn(Optional.of(clienteEsperado))
                .when(this.clienteJpaRepository)
                .findByNumeroConta(eq(consultaPorNumeroConta.getNumeroConta()));

        final Optional<Cliente> clienteResultado = this
                .clienteQueriesHandler
                .handle(consultaPorNumeroConta);

        assertEquals(clienteEsperado, clienteResultado.get());

        InOrder emOrdem = new InOrderImpl(Collections.singletonList(this.clienteJpaRepository));
        emOrdem.verify(this.clienteJpaRepository).findByNumeroConta(eq(consultaPorNumeroConta.getNumeroConta()));
        emOrdem.verifyNoMoreInteractions();
    }

    @Test
    public void buscarPorNumeroContaInexistente(){

        final BuscarClientePorNumeroConta consultaPorNumeroConta =
                ClienteQueriesHandlerMockFactory.buscarClientePorNumeroConta();

        doReturn(Optional.empty())
                .when(this.clienteJpaRepository)
                .findByNumeroConta(eq(consultaPorNumeroConta.getNumeroConta()));

        final Optional<Cliente> clienteResultado = this
                .clienteQueriesHandler
                .handle(consultaPorNumeroConta);

        assertTrue(clienteResultado.isEmpty());

        InOrder emOrdem = new InOrderImpl(Collections.singletonList(this.clienteJpaRepository));
        emOrdem.verify(this.clienteJpaRepository).findByNumeroConta(eq(consultaPorNumeroConta.getNumeroConta()));
        emOrdem.verifyNoMoreInteractions();
    }

    @Test
    public void buscarPorNumeroContaRepositorioComErro(){

        final BuscarClientePorNumeroConta consultaPorNumeroConta =
                ClienteQueriesHandlerMockFactory.buscarClientePorNumeroConta();

        doThrow(new RuntimeException(MSG_ERRO_REPOSITORIO))
                .when(this.clienteJpaRepository)
                .findByNumeroConta(eq(consultaPorNumeroConta.getNumeroConta()));

        try {

            this
                .clienteQueriesHandler
                .handle(consultaPorNumeroConta);

        }catch (Exception ex) {

            assertEquals(ex.getMessage(), MSG_ERRO_REPOSITORIO);

            InOrder emOrdem = new InOrderImpl(Collections.singletonList(this.clienteJpaRepository));
            emOrdem.verify(this.clienteJpaRepository).findByNumeroConta(eq(consultaPorNumeroConta.getNumeroConta()));
            emOrdem.verifyNoMoreInteractions();
        }
    }

    @Test
    public void listarClientes(){

        final List<Cliente> clientesEsperado = ClienteQueriesHandlerMockFactory.clientes();

        doReturn(clientesEsperado)
                .when(this.clienteJpaRepository)
                .findAll();


        final List<Cliente> clientesResultado = this
                .clienteQueriesHandler
                .handle();


        assertEquals(clientesResultado.get(ZERO), clientesEsperado.get(ZERO));
        assertEquals(clientesResultado.get(UM), clientesEsperado.get(UM));

        InOrder emOrdem = new InOrderImpl(Collections.singletonList(this.clienteJpaRepository));
        emOrdem.verify(this.clienteJpaRepository).findAll();
        emOrdem.verifyNoMoreInteractions();
    }
}
