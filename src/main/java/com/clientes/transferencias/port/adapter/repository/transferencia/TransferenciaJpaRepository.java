package com.clientes.transferencias.port.adapter.repository.transferencia;

import com.clientes.transferencias.domain.model.transferencia.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferenciaJpaRepository extends JpaRepository<Transferencia, Long> {

    List<Transferencia> findByNumeroContaOrigem(Long numeroContaOrigem);
    List<Transferencia> findByNumeroContaDestino(Long numeroContaOrigem);

}