package com.clientes.transferencias.port.adapter.repository.transferencia;

import com.clientes.transferencias.domain.model.transferencia.Situacao;
import com.clientes.transferencias.domain.model.transferencia.Transferencia;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransferenciaRepositoryImplMockFactory {

    public static List<Transferencia> transferenciasOrigem() {

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

    public static List<Transferencia> transferenciasDestino() {

        Transferencia transferencia1 =
                Transferencia
                        .builder()
                        .id(1L)
                        .data(LocalDateTime.now())
                        .numeroContaOrigem(3L)
                        .numeroContaDestino(1L)
                        .valor(300D)
                        .situacao(Situacao.CONCLUIDO_COM_SUCESSO)
                        .build();

        Transferencia transferencia2 =
                Transferencia
                        .builder()
                        .id(2L)
                        .data(LocalDateTime.now().plusDays(1))
                        .numeroContaOrigem(7L)
                        .numeroContaDestino(1L)
                        .valor(400D)
                        .situacao(Situacao.CONCLUIDO_COM_SUCESSO)
                        .build();

        List<Transferencia> transferencias = new ArrayList<>();
        transferencias.add(transferencia1);
        transferencias.add(transferencia2);

        return transferencias;
    }

    public static Transferencia transferencia() {
        return Transferencia
                .builder()
                .numeroContaOrigem(1L)
                .numeroContaDestino(2L)
                .data(LocalDateTime.now())
                .valor(300D)
                .situacao(Situacao.CONCLUIDO_COM_SUCESSO)
                .build();
    }

}
