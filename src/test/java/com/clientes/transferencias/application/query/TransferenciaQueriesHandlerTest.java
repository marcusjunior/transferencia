package com.clientes.transferencias.application.query;

import com.clientes.transferencias.domain.model.transferencia.BuscarTransferenciasPorNumeroConta;
import com.clientes.transferencias.domain.model.transferencia.Transferencia;
import com.clientes.transferencias.domain.model.transferencia.TransferenciaRepository;
import com.clientes.transferencias.port.adapter.repository.transferencia.TransferenciaJpaRepository;
import com.clientes.transferencias.port.adapter.repository.transferencia.TransferenciaRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.internal.InOrderImpl;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class TransferenciaQueriesHandlerTest {

    private static final Integer ZERO = 0;
    private static final Integer UM = 1;
    private static final Integer DOIS = 2;
    private static final Integer TRES = 3;

    private TransferenciaQueriesHandler transferenciaQueriesHandler;
    private TransferenciaJpaRepository transferenciaJpaRepository;
    private TransferenciaRepository transferenciaRepository;

    private static final String MSG_ERRO_CLIENTE_ORIGEM = "O cliente origem não foi encontrado";
    private static final String MSG_ERRO_CLIENTE_DESTINO = "O cliente destino não foi encontrado";

    @BeforeEach
    public void initialize(){
        this.transferenciaJpaRepository = mock(TransferenciaJpaRepository.class);
        this.transferenciaRepository = new TransferenciaRepositoryImpl(transferenciaJpaRepository);
        this.transferenciaQueriesHandler = new TransferenciaQueriesHandler(transferenciaRepository);
    }

    @Test
    public void buscarTransferenciasPorNumeroConta(){

        final List<Transferencia> transferenciasOrigem =
                TransferenciaQueriesHandlerMockFactory.transferenciasComoOrigem();

        final List<Transferencia> transferenciasDestino =
                TransferenciaQueriesHandlerMockFactory.transferenciasComoDestino();

        final List<Transferencia> transferenciasEsperado =
                TransferenciaQueriesHandlerMockFactory.transferencias();

        final BuscarTransferenciasPorNumeroConta buscarTransferenciasPorNumeroConta =
                TransferenciaQueriesHandlerMockFactory.buscarTransferenciasPorNumeroConta();

        doReturn(transferenciasOrigem)
                .when(this.transferenciaJpaRepository)
                .findByNumeroContaOrigem(buscarTransferenciasPorNumeroConta.getNumeroConta());

        doReturn(transferenciasDestino)
                .when(this.transferenciaJpaRepository)
                .findByNumeroContaDestino(buscarTransferenciasPorNumeroConta.getNumeroConta());

        final List<Transferencia> transferenciasResultado = this
                .transferenciaQueriesHandler
                .handle(buscarTransferenciasPorNumeroConta);

        assertEquals(transferenciasEsperado.get(ZERO).getId(), transferenciasResultado.get(ZERO).getId());
        assertEquals(transferenciasEsperado.get(UM).getId(), transferenciasResultado.get(UM).getId());
        assertEquals(transferenciasEsperado.get(DOIS).getId(), transferenciasResultado.get(DOIS).getId());
        assertEquals(transferenciasEsperado.get(TRES).getId(), transferenciasResultado.get(TRES).getId());

        InOrder emOrdem = new InOrderImpl(Collections.singletonList(this.transferenciaJpaRepository));
        emOrdem.verify(this.transferenciaJpaRepository).findByNumeroContaOrigem(buscarTransferenciasPorNumeroConta.getNumeroConta());
        emOrdem.verify(this.transferenciaJpaRepository).findByNumeroContaDestino(buscarTransferenciasPorNumeroConta.getNumeroConta());
        emOrdem.verifyNoMoreInteractions();
    }
}
