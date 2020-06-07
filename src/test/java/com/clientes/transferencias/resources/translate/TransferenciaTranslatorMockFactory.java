package com.clientes.transferencias.resources.translate;

import com.clientes.transferencias.domain.model.transferencia.Situacao;
import com.clientes.transferencias.domain.model.transferencia.Transferencia;
import com.clientes.transferencias.resources.dto.TransferenciaDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransferenciaTranslatorMockFactory {

    public static Transferencia transferencia(LocalDateTime data) {
        return Transferencia
                .builder()
                .id(1L)
                .numeroContaOrigem(1L)
                .numeroContaDestino(2L)
                .data(data)
                .valor(300D)
                .situacao(Situacao.CONCLUIDO_COM_SUCESSO)
                .build();
    }

    public static List<Transferencia> transferencias() {

        Transferencia transferencia1 =
                Transferencia
                        .builder()
                        .id(1L)
                        .data(LocalDateTime.now().plusDays(2))
                        .numeroContaOrigem(3L)
                        .numeroContaDestino(1L)
                        .valor(1100D)
                        .situacao(Situacao.CONCLUIDO_COM_ERRO)
                        .build();

        Transferencia transferencia2 =
                Transferencia
                        .builder()
                        .id(2L)
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

    public static TransferenciaDTO transferenciaDTO(LocalDateTime data) {
        return TransferenciaDTO
                .builder()
                .id(1L)
                .numeroContaOrigem(1L)
                .numeroContaDestino(2L)
                .data(data)
                .valor(300D)
                .situacao(Situacao.CONCLUIDO_COM_SUCESSO)
                .build();
    }

    public static List<TransferenciaDTO> transferenciasDTO() {

        TransferenciaDTO transferencia1 =
                TransferenciaDTO
                        .builder()
                        .id(1L)
                        .data(LocalDateTime.now().plusDays(2))
                        .numeroContaOrigem(3L)
                        .numeroContaDestino(1L)
                        .valor(1100D)
                        .situacao(Situacao.CONCLUIDO_COM_ERRO)
                        .build();

        TransferenciaDTO transferencia2 =
                TransferenciaDTO
                        .builder()
                        .id(2L)
                        .data(LocalDateTime.now().plusDays(3))
                        .numeroContaOrigem(3L)
                        .numeroContaDestino(1L)
                        .valor(1000D)
                        .situacao(Situacao.CONCLUIDO_COM_SUCESSO)
                        .build();

        List<TransferenciaDTO> transferencias = new ArrayList<>();
        transferencias.add(transferencia1);
        transferencias.add(transferencia2);

        return transferencias;
    }

}
