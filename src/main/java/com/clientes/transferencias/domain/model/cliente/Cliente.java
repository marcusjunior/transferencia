package com.clientes.transferencias.domain.model.cliente;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Entity
public class Cliente {

    @Id
    private Long id;

    @NotNull
    @Column(unique = true)
    private Long numeroConta;

    @NotNull
    private String nome;

    @NotNull
    private Double saldo;

}
