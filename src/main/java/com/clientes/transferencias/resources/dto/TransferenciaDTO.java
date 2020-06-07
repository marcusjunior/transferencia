package com.clientes.transferencias.resources.dto;

import com.clientes.transferencias.domain.model.transferencia.Situacao;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
public class TransferenciaDTO {

    private Long id;
    private LocalDateTime data;
    private Long numeroContaOrigem;
    private Long numeroContaDestino;
    private Double valor;
    private Situacao situacao;

}


