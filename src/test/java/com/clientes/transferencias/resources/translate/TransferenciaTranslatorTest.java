package com.clientes.transferencias.resources.translate;

import com.clientes.transferencias.domain.model.transferencia.Transferencia;
import com.clientes.transferencias.resources.dto.TransferenciaDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferenciaTranslatorTest {

    private static final Integer ZERO = 0;
    private static final Integer UM = 1;

    @Test
    public void paraRecurso() {

        LocalDateTime data = LocalDateTime.now();

        Transferencia transferencia =  TransferenciaTranslatorMockFactory.transferencia(data);
        TransferenciaDTO transferenciaEsperado = TransferenciaTranslatorMockFactory.transferenciaDTO(data);

        TransferenciaDTO transferenciaResultado =
                TransferenciaTranslator.paraRecurso.apply(transferencia);

        assertEquals(transferenciaEsperado.getId(), transferenciaResultado.getId());
        assertEquals(transferenciaEsperado.getNumeroContaOrigem(), transferenciaResultado.getNumeroContaOrigem());
        assertEquals(transferenciaEsperado.getNumeroContaDestino(), transferenciaResultado.getNumeroContaDestino());
        assertEquals(transferenciaEsperado.getData(), transferenciaResultado.getData());
        assertEquals(transferenciaEsperado.getValor(), transferenciaResultado.getValor());
        assertEquals(transferenciaEsperado.getSituacao(), transferenciaResultado.getSituacao());
    }

    @Test
    public void paraLista() {

        List<Transferencia> transferencias =  TransferenciaTranslatorMockFactory.transferencias();
        List<TransferenciaDTO> transferenciaEsperado = TransferenciaTranslatorMockFactory.transferenciasDTO();

        List<TransferenciaDTO> transferenciasResultado =
                TransferenciaTranslator.paraLista.apply(transferencias);

        assertEquals(transferenciaEsperado.get(ZERO).getId(), transferenciasResultado.get(ZERO).getId());
        assertEquals(transferenciaEsperado.get(UM).getId(), transferenciasResultado.get(UM).getId());
    }

}
