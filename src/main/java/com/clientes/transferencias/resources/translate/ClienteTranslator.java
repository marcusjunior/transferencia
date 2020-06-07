package com.clientes.transferencias.resources.translate;

import com.clientes.transferencias.domain.model.cliente.Cliente;
import com.clientes.transferencias.resources.dto.ClienteDTO;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ClienteTranslator {

    /**
     * Mapeia um objeto de domnio de cliente para o recurso equivalente
     * @param from objeto de dominio de cliente que ira ser mapeado
     * @return recurso mapeado a partir do dominio
     */
    public static Function<Cliente, ClienteDTO> paraRecurso = from ->
            ClienteDTO.builder()
                .id(from.getId())
                .nome(from.getNome())
                .numeroConta(from.getNumeroConta())
                .saldo(from.getSaldo())
                .build();

    /**
     * Mapeia a listagem de clientes de dominio para o recurso equivalente
     * @param from listagem de dominio de cliente que ira ser mapeada
     * @return listagem mapeado a partir da listagem de dominio
     */
    public static Function<List<Cliente>, List<ClienteDTO>> paraLista = from ->
        from.stream()
            .map(cliente -> paraRecurso.apply(cliente))
            .collect(Collectors.toList());

}
