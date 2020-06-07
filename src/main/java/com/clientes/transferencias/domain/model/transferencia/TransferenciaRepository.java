package com.clientes.transferencias.domain.model.transferencia;

import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface TransferenciaRepository {

    Optional<Transferencia> salvar(@NonNull Transferencia transferencia);
    List<Transferencia> buscarPorNumeroContaOrigem(Long numeroConta);
    List<Transferencia> buscarPorNumeroContaDestino(Long numeroConta);

}
