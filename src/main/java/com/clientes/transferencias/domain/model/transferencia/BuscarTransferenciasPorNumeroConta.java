package com.clientes.transferencias.domain.model.transferencia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@Builder
public class BuscarTransferenciasPorNumeroConta {

    @NotNull(message = "O numero da conta deve ser informado")
    private Long numeroConta;

}
