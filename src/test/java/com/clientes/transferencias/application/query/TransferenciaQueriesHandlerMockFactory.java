package com.clientes.transferencias.application.query;

import com.clientes.transferencias.domain.model.transferencia.BuscarTransferenciasPorNumeroConta;
import com.clientes.transferencias.domain.model.transferencia.Situacao;
import com.clientes.transferencias.domain.model.transferencia.Transferencia;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransferenciaQueriesHandlerMockFactory {

    private static final Integer ZERO = 0;
    private static final Integer UM = 1;

    public static List<Transferencia> transferenciasComoOrigem() {

        Transferencia transferencia1 =
            Transferencia
                .builder()
                .id(1L)
                .data(LocalDateTime.now())
                .numeroContaOrigem(1L)
                .numeroContaDestino(2L)
                .valor(300D)
                .situacao(Situacao.CONCLUIDO_COM_SUCESSO)
                .build();

        Transferencia transferencia2 =
                Transferencia
                        .builder()
                        .id(2L)
                        .data(LocalDateTime.now().plusDays(1))
                        .numeroContaOrigem(1L)
                        .numeroContaDestino(5L)
                        .valor(400D)
                        .situacao(Situacao.CONCLUIDO_COM_SUCESSO)
                        .build();

        List<Transferencia> transferencias = new ArrayList<>();
        transferencias.add(transferencia1);
        transferencias.add(transferencia2);

        return transferencias;
    }

    public static List<Transferencia> transferenciasComoDestino() {

        Transferencia transferencia1 =
                Transferencia
                        .builder()
                        .id(3L)
                        .data(LocalDateTime.now().plusDays(2))
                        .numeroContaOrigem(3L)
                        .numeroContaDestino(1L)
                        .valor(1100D)
                        .situacao(Situacao.CONCLUIDO_COM_ERRO)
                        .build();

        Transferencia transferencia2 =
                Transferencia
                        .builder()
                        .id(4L)
                        .data(LocalDateTime.now().plusDays(3))
                        .numeroContaOrigem(3L)
                        .numeroContaDestino(1L)
                        .valor(1000D)
                        .situacao(Situacao.CONCLUIDO_COM_SUCESSO)
                        .build();

        List<Transferencia> transferencias = new ArrayList<>();
        transferencias.add(transferencia1);
        transferencias.add(transferencia2);

        return transferencias;
    }

    public static List<Transferencia> transferencias() {

        List<Transferencia> transferenciasOrigem = transferenciasComoOrigem();
        List<Transferencia> transferenciasDestino = transferenciasComoDestino();

        Transferencia transferencia1 = transferenciasOrigem.get(ZERO);
        Transferencia transferencia2 = transferenciasOrigem.get(UM);
        Transferencia transferencia3 = transferenciasDestino.get(ZERO);
        Transferencia transferencia4 = transferenciasDestino.get(UM);

        List<Transferencia> transferencias = new ArrayList<>();

        transferencias.add(transferencia4);
        transferencias.add(transferencia3);
        transferencias.add(transferencia2);
        transferencias.add(transferencia1);

        return transferencias;
    }

    public static BuscarTransferenciasPorNumeroConta buscarTransferenciasPorNumeroConta() {
        return BuscarTransferenciasPorNumeroConta
                .builder()
                .numeroConta(1L)
                .build();
    }

}
