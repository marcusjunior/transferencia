package com.clientes.transferencias.application.query;

import com.clientes.transferencias.domain.model.transferencia.BuscarTransferenciasPorNumeroConta;
import com.clientes.transferencias.domain.model.transferencia.Transferencia;
import com.clientes.transferencias.domain.model.transferencia.TransferenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TransferenciaQueriesHandler {

    @Autowired
    private TransferenciaRepository repository;

    public TransferenciaQueriesHandler(TransferenciaRepository repository) {
        this.repository = repository;
    }

    /**
     * busca todas a trasferencias relacionadas a uma conta e as ordena de forma decrescente pela data
     * @param buscarTransferenciasPorNumeroConta consulta para buscar transferencias por conta
     * @return listagem com as transferencias encontradas na base
     */
    public Function<BuscarTransferenciasPorNumeroConta, List<Transferencia>> buscarTransferencias = buscarTransferenciasPorNumeroConta -> {

        List<Transferencia> transferencias = new ArrayList<>();

        this.repository
            .buscarPorNumeroContaOrigem(buscarTransferenciasPorNumeroConta.getNumeroConta())
            .stream()
            .map(transferencia -> transferencias.add(transferencia))
            .collect(Collectors.toList());

        this.repository
            .buscarPorNumeroContaDestino(buscarTransferenciasPorNumeroConta.getNumeroConta())
            .stream()
            .map(transferencia -> transferencias.add(transferencia))
            .collect(Collectors.toList());

        transferencias.sort(Comparator.comparing(Transferencia::getData).reversed());

        return transferencias;
    };

    /**
     * Manipula a consulta buscar transferencias por numero conta
     * @param query consulta para buscar transferencias por numero conta
     * @return uma listagem com as transferencias encontradas
     */
    public List<Transferencia> handle(BuscarTransferenciasPorNumeroConta query) {

        return this.buscarTransferencias.apply(query);
    }
}
