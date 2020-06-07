package com.clientes.transferencias.resources.translate;

import com.clientes.transferencias.domain.model.cliente.Cliente;
import com.clientes.transferencias.resources.dto.ClienteDTO;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClienteTranslatorTest {

    private static final Integer ZERO = 0;
    private static final Integer UM = 1;

    @Test
    public void paraRecurso() {

        Cliente cliente =  ClienteTranslatorMockFactory.cliente();
        ClienteDTO clienteEsperado = ClienteTranslatorMockFactory.clienteDTO();

        ClienteDTO clienteResultado =
                ClienteTranslator.paraRecurso.apply(cliente);

        assertEquals(clienteEsperado.getId(), clienteResultado.getId());
        assertEquals(clienteEsperado.getNumeroConta(), clienteResultado.getNumeroConta());
        assertEquals(clienteEsperado.getNome(), clienteResultado.getNome());
        assertEquals(clienteEsperado.getSaldo(), clienteResultado.getSaldo());
    }

    @Test
    public void paraLista() {

        List<Cliente> clientes =  ClienteTranslatorMockFactory.clientes();
        List<ClienteDTO> clientesEsperado = ClienteTranslatorMockFactory.clientesDTO();

        List<ClienteDTO> clientesResultado =
                ClienteTranslator.paraLista.apply(clientes);

        assertEquals(clientesEsperado.get(ZERO).getId(), clientesResultado.get(ZERO).getId());
        assertEquals(clientesEsperado.get(UM).getId(), clientesResultado.get(UM).getId());
    }

}
