package com.clientes.transferencias.resources.translate;

import com.clientes.transferencias.domain.model.transferencia.Transferencia;
import com.clientes.transferencias.resources.dto.TransferenciaDTO;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TransferenciaTranslator {

    /**
     * Mapeia um objeto de transferencia para o recurso equivalente
     * @param from objeto de dominio de transferencia que ira ser mapeado
     * @return recurso mapeado a partir do dominio
     */
    public static Function<Transferencia, TransferenciaDTO> paraRecurso = from ->
        TransferenciaDTO.builder()
            .id(from.getId())
            .data(from.getData())
            .numeroContaOrigem(from.getNumeroContaOrigem())
            .numeroContaDestino(from.getNumeroContaDestino())
            .valor(from.getValor())
            .situacao(from.getSituacao())
            .build();

    /**
     * Mapeia a listagem de clientes de dominio para o recurso equivalente
     * @param from listagem de dominio de transferencia que ira ser mapeada
     * @return listagem mapeada a partir da listagem dominio
     */
    public static Function<List<Transferencia>, List<TransferenciaDTO>> paraLista = from ->
        from.stream()
            .map(transferencia -> paraRecurso.apply(transferencia))
            .collect(Collectors.toList());

}
