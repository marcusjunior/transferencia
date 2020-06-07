package com.clientes.transferencias.domain.model.cliente;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@EqualsAndHashCode
@Builder
public class CriarCliente {

    @NotNull(message = "O ID deve ser informado")
    private Long id;

    @NotNull(message = "O numero da conta deve ser informado")
    private Long numeroConta;

    @NotNull(message = "O nome deve ser informado")
    private String nome;

    @NotNull(message = "O saldo deve ser informado")
    private Double saldo;

}
