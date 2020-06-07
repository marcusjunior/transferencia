package com.clientes.transferencias.port.adapter.repository.cliente;

import com.clientes.transferencias.domain.model.cliente.Cliente;
import com.clientes.transferencias.domain.model.cliente.ClienteRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class ClienteRepositoryImpl implements ClienteRepository {

    @Autowired
    private ClienteJpaRepository clienteJpagRepository;


    public ClienteRepositoryImpl(ClienteJpaRepository clienteJpagRepository) {
        this.clienteJpagRepository = clienteJpagRepository;
    }

    /**
     * Salva um cliente no repositorio
     * @param cliente cliente que sera persistido
     * @return um optional com o objeto persistido
     */
    @Override
    public Optional<Cliente> salvar(@NotNull Cliente cliente) {
        return Optional.ofNullable(clienteJpagRepository.save(cliente));
    }

    /**
     * Lista todos os clientes no repositorio
     * @return uma listagem com todos os clientes encontrados
     */
    @Override
    public List<Cliente> listarTodos() {

        return clienteJpagRepository.findAll();
    }

    /**
     * Busca um cliente pelo numero da conta
     * @param numeroConta numero da conta para consulta no repositorio
     * @return um optional com o objeto de cliente encontrado
     */
    @Override
    public Optional<Cliente> buscarPorNumeroConta(Long numeroConta) {
        return clienteJpagRepository.findByNumeroConta(numeroConta);
    }

    /**
     * Verifica se um determinando ID ja existe no repositorio
     * @param id utilizado para consulta no repositorio
     * @return um booleano com o resultado da consulta
     */
    @Override
    public Boolean verificarDisponibilidadeID(Long id) {
        return !clienteJpagRepository.existsById(id);
    }
}
