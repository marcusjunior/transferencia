package com.clientes.transferencias.domain.model.cliente;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@Builder
public class BuscarClientePorNumeroConta {

    @NotNull(message = "O numero da conta deve ser informado")
    private Long numeroConta;
}
