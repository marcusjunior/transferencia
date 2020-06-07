package com.clientes.transferencias.port.adapter.repository.cliente;

import com.clientes.transferencias.domain.model.cliente.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.internal.InOrderImpl;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ClienteRepositoryImplTest {

    private static final Long NUMERO_CONTA_PADRAO = 1L;
    private static final Integer ZERO = 0;
    private static final Integer UM = 1;

    private ClienteRepositoryImpl clienteRepository;
    private ClienteJpaRepository clienteJpaRepository;

    @BeforeEach
    public void initialize() {
        this.clienteJpaRepository = mock(ClienteJpaRepository.class);
        this.clienteRepository = new ClienteRepositoryImpl(clienteJpaRepository);
    }

    @Test
    public void criarNovoCliente() {
        final Cliente clienteEsperado = ClienteRepositoryImplMockFactory.cliente();

        doReturn(clienteEsperado)
                .when(this.clienteJpaRepository)
                .save(clienteEsperado);

        Optional<Cliente> clienteResultado = this.clienteRepository.salvar(clienteEsperado);

        assertEquals(clienteEsperado, clienteResultado.get());

        InOrder emOrdem = new InOrderImpl(Collections.singletonList(this.clienteJpaRepository));
        emOrdem.verify(this.clienteJpaRepository).save(eq(clienteEsperado));
        emOrdem.verifyNoMoreInteractions();
    }

    @Test
    public void buscarPorNumeroConta() {
        final Cliente clienteEsperado = ClienteRepositoryImplMockFactory.cliente();

        doReturn(Optional.of(clienteEsperado))
                .when(this.clienteJpaRepository)
                .findByNumeroConta(NUMERO_CONTA_PADRAO);

        Optional<Cliente> clienteResultado = clienteRepository.buscarPorNumeroConta(NUMERO_CONTA_PADRAO);

        assertEquals(clienteEsperado, clienteResultado.get());

        InOrder emOrdem = new InOrderImpl(Collections.singletonList(this.clienteJpaRepository));
        emOrdem.verify(this.clienteJpaRepository).findByNumeroConta(eq(NUMERO_CONTA_PADRAO));
        emOrdem.verifyNoMoreInteractions();
    }

    @Test
    public void buscarPorNumeroContaErro() {
        doReturn(Optional.empty())
                .when(this.clienteJpaRepository)
                .findByNumeroConta(NUMERO_CONTA_PADRAO);

        Optional<Cliente> clienteResultado = clienteRepository.buscarPorNumeroConta(NUMERO_CONTA_PADRAO);

        assertTrue(clienteResultado.isEmpty());

        InOrder emOrdem = new InOrderImpl(Collections.singletonList(this.clienteJpaRepository));
        emOrdem.verify(this.clienteJpaRepository).findByNumeroConta(eq(NUMERO_CONTA_PADRAO));
        emOrdem.verifyNoMoreInteractions();
    }


    @Test
    public void listarClientes() {
        final List<Cliente> clientesEsperado = ClienteRepositoryImplMockFactory.clientes();

        doReturn(clientesEsperado)
                .when(this.clienteJpaRepository)
                .findAll();

        List<Cliente> clientes = clienteRepository.listarTodos();

        assertEquals(clientes.get(ZERO).getId(), clientesEsperado.get(ZERO).getId());
        assertEquals(clientes.get(ZERO).getNome(), clientesEsperado.get(ZERO).getNome());
        assertEquals(clientes.get(ZERO).getNumeroConta(), clientesEsperado.get(ZERO).getNumeroConta());
        assertEquals(clientes.get(ZERO).getSaldo(), clientesEsperado.get(ZERO).getSaldo());

        assertEquals(clientes.get(UM).getId(), clientesEsperado.get(UM).getId());
        assertEquals(clientes.get(UM).getNome(), clientesEsperado.get(UM).getNome());
        assertEquals(clientes.get(UM).getNumeroConta(), clientesEsperado.get(UM).getNumeroConta());
        assertEquals(clientes.get(UM).getSaldo(), clientesEsperado.get(UM).getSaldo());

        InOrder emOrdem = new InOrderImpl(Collections.singletonList(this.clienteJpaRepository));
        emOrdem.verify(this.clienteJpaRepository).findAll();
        emOrdem.verifyNoMoreInteractions();
    }

}
