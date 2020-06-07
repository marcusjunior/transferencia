package com.clientes.transferencias.domain.model.cliente;

import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository {

    Optional<Cliente> salvar(@NonNull Cliente cliente);
    List<Cliente> listarTodos();
    Optional<Cliente> buscarPorNumeroConta(Long numeroConta);
    public Boolean verificarDisponibilidadeID(Long id);

}
