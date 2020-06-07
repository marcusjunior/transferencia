package com.clientes.transferencias.port.adapter.repository.cliente;

import com.clientes.transferencias.domain.model.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteJpaRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByNumeroConta(Long numeroConta);
    boolean existsById(Long id);

}