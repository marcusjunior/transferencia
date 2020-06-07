package com.clientes.transferencias.application.command;

import com.clientes.transferencias.domain.model.cliente.Cliente;
import com.clientes.transferencias.domain.model.transferencia.RealizarTransferencia;
import com.clientes.transferencias.domain.model.transferencia.Situacao;
import com.clientes.transferencias.domain.model.transferencia.Transferencia;

import java.time.LocalDateTime;

public class TransferenciaCommandsHandlerMockFactory {

    public static Cliente clienteOrigem() {
        return Cliente
                .builder()
                .id(1L)
                .nome("Cliente 1")
                .numeroConta(1L)
                .saldo(800D)
                .build();
    }

    public static Cliente clienteDestino() {
        return Cliente
                .builder()
                .id(2L)
                .nome("Cliente 2")
                .numeroConta(2L)
                .saldo(800D)
                .build();
    }

    public static RealizarTransferencia realizarTransferencia(Double valor){
        return RealizarTransferencia
                .builder()
                .data(LocalDateTime.now())
                .numeroContaOrigem(1L)
                .numeroContaDestino(2L)
                .valor(valor)
                .build();
    }

    public static Transferencia transferencia(Double valor, LocalDateTime data, Situacao situacao) {
        return Transferencia
                .builder()
                .numeroContaOrigem(1L)
                .numeroContaDestino(2L)
                .data(data)
                .valor(valor)
                .situacao(situacao)
                .build();
    }

}
