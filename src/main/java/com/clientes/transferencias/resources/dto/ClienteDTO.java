package com.clientes.transferencias.resources.dto;

import lombok.*;

@Getter
@Builder
public class ClienteDTO {

    private Long id;
    private Long numeroConta;
    private String nome;
    private Double saldo;

}
