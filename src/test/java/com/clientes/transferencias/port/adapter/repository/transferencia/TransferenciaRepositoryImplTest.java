package com.clientes.transferencias.port.adapter.repository.transferencia;

import com.clientes.transferencias.application.query.TransferenciaQueriesHandlerMockFactory;
import com.clientes.transferencias.domain.model.transferencia.Transferencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.internal.InOrderImpl;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TransferenciaRepositoryImplTest {

    private static final Long NUMERO_CONTA_PADRAO = 1L;
    private static final Integer ZERO = 0;
    private static final Integer UM = 1;

    private TransferenciaJpaRepository transferenciaJpaRepository;
    private TransferenciaRepositoryImpl transferenciaRepository;

    @BeforeEach
    public void initialize() {
        this.transferenciaJpaRepository = mock(TransferenciaJpaRepository.class);
        this.transferenciaRepository = new TransferenciaRepositoryImpl(transferenciaJpaRepository);
    }

    @Test
    public void criarNovaTransferencia() {
        final Transferencia transferenciaEsperado = TransferenciaRepositoryImplMockFactory.transferencia();

        doReturn(transferenciaEsperado)
                .when(this.transferenciaJpaRepository)
                .save(transferenciaEsperado);

        Optional<Transferencia> transferenciaResultado = this.transferenciaRepository.salvar(transferenciaEsperado);

        assertEquals(transferenciaEsperado, transferenciaResultado.get());

        InOrder emOrdem = new InOrderImpl(Collections.singletonList(this.transferenciaJpaRepository));
        emOrdem.verify(this.transferenciaJpaRepository).save(eq(transferenciaEsperado));
        emOrdem.verifyNoMoreInteractions();
    }

    @Test
    public void buscarPorNumeroContaOrigem() {
        final List<Transferencia> transferenciasEsperado =
                TransferenciaQueriesHandlerMockFactory.transferenciasComoOrigem();

        doReturn(transferenciasEsperado)
                .when(this.transferenciaJpaRepository)
                .findByNumeroContaOrigem(NUMERO_CONTA_PADRAO);

        List<Transferencia> transferencias = transferenciaRepository.buscarPorNumeroContaOrigem(NUMERO_CONTA_PADRAO);

        assertEquals(transferencias.get(ZERO).getId(), transferencias.get(ZERO).getId());
        assertEquals(transferenciasEsperado.get(UM).getId(), transferenciasEsperado.get(UM).getId());

        InOrder emOrdem = new InOrderImpl(Collections.singletonList(this.transferenciaJpaRepository));
        emOrdem.verify(this.transferenciaJpaRepository).findByNumeroContaOrigem(eq(NUMERO_CONTA_PADRAO));
        emOrdem.verifyNoMoreInteractions();
    }

    @Test
    public void buscarPorNumeroContaDestino() {
        final List<Transferencia> transferenciasEsperado =
                TransferenciaQueriesHandlerMockFactory.transferenciasComoDestino();

        doReturn(transferenciasEsperado)
                .when(this.transferenciaJpaRepository)
                .findByNumeroContaDestino(NUMERO_CONTA_PADRAO);

        List<Transferencia> transferencias = transferenciaRepository.buscarPorNumeroContaDestino(NUMERO_CONTA_PADRAO);

        assertEquals(transferencias.get(ZERO).getId(), transferencias.get(ZERO).getId());
        assertEquals(transferenciasEsperado.get(UM).getId(), transferenciasEsperado.get(UM).getId());

        InOrder emOrdem = new InOrderImpl(Collections.singletonList(this.transferenciaJpaRepository));
        emOrdem.verify(this.transferenciaJpaRepository).findByNumeroContaDestino(eq(NUMERO_CONTA_PADRAO));
        emOrdem.verifyNoMoreInteractions();
    }

}
