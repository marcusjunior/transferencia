package com.clientes.transferencias.domain.model.transferencia;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
@Builder
public class RealizarTransferencia {

    @NotNull(message = "O numero conta origem deve ser informado")
    private Long numeroContaOrigem;

    @NotNull(message = "O numero conta destino deve ser informado")
    private Long numeroContaDestino;

    @NotNull(message = "A data deve ser informada")
    private LocalDateTime data;

    @NotNull(message = "O valor deve ser informado")
    private Double valor;

}
