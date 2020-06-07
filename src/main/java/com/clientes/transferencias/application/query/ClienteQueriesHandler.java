package com.clientes.transferencias.application.query;

import com.clientes.transferencias.domain.model.cliente.BuscarClientePorNumeroConta;
import com.clientes.transferencias.domain.model.cliente.Cliente;
import com.clientes.transferencias.domain.model.cliente.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class ClienteQueriesHandler {

    @Autowired
    private ClienteRepository repository;

    public ClienteQueriesHandler(ClienteRepository repository) {
        this.repository = repository;
    }

    /**
     * Manipula a consulta padrao que lista todos os clientes do repositorio
     * @return uma lista com clientes encontrados na base
     */
    public List<Cliente> handle() {

        return this
                .repository
                .listarTodos();
    }

    /**
     * Manipula a conulsta buscar clientes por numero conta
     * @param query consulta para buscar um cliente pelo numero da conta
     * @return um optional com o objeto de cliente encontrado na base
     */
    public Optional<Cliente> handle(BuscarClientePorNumeroConta query) {

        return this.repository.buscarPorNumeroConta(query.getNumeroConta());
    }
}
