package com.clientes.transferencias.port.adapter.repository.cliente;

import com.clientes.transferencias.domain.model.cliente.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteRepositoryImplMockFactory {

    public static Cliente cliente() {
        return Cliente
                .builder()
                .id(1L)
                .nome("Cliente 1")
                .numeroConta(1L)
                .saldo(800D)
                .build();
    }

    public static List<Cliente> clientes() {

        Cliente cliente1 =
            Cliente
                .builder()
                .id(1L)
                .nome("Cliente 1")
                .numeroConta(1L)
                .saldo(800D)
                .build();

        Cliente cliente2 =
            Cliente
                .builder()
                .id(2L)
                .nome("Cliente 2")
                .numeroConta(2L)
                .saldo(500D)
                .build();

        List<Cliente> clientes = new ArrayList<>();
        clientes.add(cliente1);
        clientes.add(cliente2);

        return clientes;
    }
}
