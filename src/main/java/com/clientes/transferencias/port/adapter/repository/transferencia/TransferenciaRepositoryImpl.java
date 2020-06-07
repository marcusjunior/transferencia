package com.clientes.transferencias.port.adapter.repository.transferencia;

import com.clientes.transferencias.domain.model.transferencia.Transferencia;
import com.clientes.transferencias.domain.model.transferencia.TransferenciaRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class TransferenciaRepositoryImpl implements TransferenciaRepository {

    @Autowired
    private TransferenciaJpaRepository transferenciaJpaRepository;


    public TransferenciaRepositoryImpl(TransferenciaJpaRepository transferenciaJpaRepository) {
        this.transferenciaJpaRepository = transferenciaJpaRepository;
    }

    /**
     * Salva uma transferencia no repositorio
     * @param transferencia transferencia que sera persistida
     * @return um optional com o objeto persistido
     */
    @Override
    public Optional<Transferencia> salvar(@NotNull Transferencia transferencia) {
        return Optional.ofNullable(transferenciaJpaRepository.save(transferencia));
    }

    /**
     * Busca as transferencias relacionados a um numero de conta quando origem
     * @param numeroConta numero da conta para consulta no repositorio
     * @return uma listagem com as transferencias encontradas
     */
    @Override
    public List<Transferencia> buscarPorNumeroContaOrigem(Long numeroConta) {
        return transferenciaJpaRepository.findByNumeroContaOrigem(numeroConta);
    }

    /**
     * Busca as transferencias relacionados a um numero de conta quando destino
     * @param numeroConta numero da conta para consulta no repositorio
     * @return uma listagem com as transferencias encontradas
     */
    @Override
    public List<Transferencia> buscarPorNumeroContaDestino(Long numeroConta) {
        return transferenciaJpaRepository.findByNumeroContaDestino(numeroConta);
    }

}
