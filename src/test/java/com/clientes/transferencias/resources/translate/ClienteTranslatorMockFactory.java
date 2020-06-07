package com.clientes.transferencias.resources.translate;

import com.clientes.transferencias.domain.model.cliente.Cliente;
import com.clientes.transferencias.resources.dto.ClienteDTO;

import java.util.ArrayList;
import java.util.List;

public class ClienteTranslatorMockFactory {

    public static ClienteDTO clienteDTO() {
        return ClienteDTO
                .builder()
                .id(1L)
                .nome("Cliente 1")
                .numeroConta(1L)
                .saldo(800D)
                .build();
    }

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

    public static List<ClienteDTO> clientesDTO() {

        ClienteDTO cliente1 =
                ClienteDTO
                        .builder()
                        .id(1L)
                        .nome("Cliente 1")
                        .numeroConta(1L)
                        .saldo(800D)
                        .build();

        ClienteDTO cliente2 =
                ClienteDTO
                        .builder()
                        .id(2L)
                        .nome("Cliente 2")
                        .numeroConta(2L)
                        .saldo(500D)
                        .build();

        List<ClienteDTO> clientes = new ArrayList<>();
        clientes.add(cliente1);
        clientes.add(cliente2);

        return clientes;
    }
}
