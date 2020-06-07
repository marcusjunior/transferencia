package com.clientes.transferencias.resources;

import com.clientes.transferencias.application.command.TransferenciaCommandsHandlerMockFactory;
import com.clientes.transferencias.domain.model.cliente.Cliente;
import com.clientes.transferencias.domain.model.transferencia.RealizarTransferencia;
import com.clientes.transferencias.domain.model.transferencia.Situacao;
import com.clientes.transferencias.domain.model.transferencia.Transferencia;
import com.clientes.transferencias.port.adapter.repository.cliente.ClienteJpaRepository;
import com.clientes.transferencias.port.adapter.repository.transferencia.TransferenciaJpaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransferenciaControllerIntegrationTest {

    private final static String URL_BASE = "/v1/transferencia";
    private final static String NUMERO_CONTA_QUERY = "?numeroConta={numeroConta}";
    private final static Long NUMERO_CONTA = 1L;
    private static final Integer ZERO = 0;
    private static final Integer UM = 1;
    private static final Integer DOIS = 2;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private TransferenciaJpaRepository transferenciaJpaRepository;

    @Autowired
    private ClienteJpaRepository clienteJpaRepository;

    @AfterEach
    public void clean(){
        this.transferenciaJpaRepository.deleteAll();
        this.clienteJpaRepository.deleteAll();
    }

    @Test
    public void realizarTransferenciaComSucesso() {

        final Cliente clienteOrigem = TransferenciaCommandsHandlerMockFactory.clienteOrigem();
        final Cliente clienteDestino = TransferenciaCommandsHandlerMockFactory.clienteDestino();

        this.clienteJpaRepository.save(clienteOrigem);
        this.clienteJpaRepository.save(clienteDestino);

        RealizarTransferencia realizarTransferencia =
                TransferenciaControllerIntegrationMockFactory.realizarTransferencia(300D);

        Transferencia transferenciaEsperado =
                TransferenciaControllerIntegrationMockFactory.transferencia(
                        300D, realizarTransferencia.getData(), Situacao.CONCLUIDO_COM_SUCESSO);

        HttpEntity<RealizarTransferencia> httpEntity = new HttpEntity<>(realizarTransferencia);

        ResponseEntity<Transferencia> resultado =
                testRestTemplate.postForEntity(URL_BASE, httpEntity, Transferencia.class);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(transferenciaEsperado.getNumeroContaOrigem(), resultado.getBody().getNumeroContaOrigem());
        assertEquals(transferenciaEsperado.getNumeroContaDestino(), resultado.getBody().getNumeroContaDestino());
        assertEquals(transferenciaEsperado.getValor(), resultado.getBody().getValor());
        assertEquals(transferenciaEsperado.getData(), resultado.getBody().getData());
        assertEquals(transferenciaEsperado.getSituacao(), resultado.getBody().getSituacao());
    }

    @Test
    public void realizarTransferenciaComErro() {

        final Cliente clienteOrigem = TransferenciaCommandsHandlerMockFactory.clienteOrigem();
        final Cliente clienteDestino = TransferenciaCommandsHandlerMockFactory.clienteDestino();

        this.clienteJpaRepository.save(clienteOrigem);
        this.clienteJpaRepository.save(clienteDestino);

        RealizarTransferencia realizarTransferencia =
                TransferenciaControllerIntegrationMockFactory.realizarTransferencia(3000D);

        Transferencia transferenciaEsperado =
                TransferenciaControllerIntegrationMockFactory.transferencia(
                        3000D, realizarTransferencia.getData(), Situacao.CONCLUIDO_COM_ERRO);

        HttpEntity<RealizarTransferencia> httpEntity = new HttpEntity<>(realizarTransferencia);

        ResponseEntity<Transferencia> resultado =
                testRestTemplate.postForEntity(URL_BASE, httpEntity, Transferencia.class);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(transferenciaEsperado.getNumeroContaOrigem(), resultado.getBody().getNumeroContaOrigem());
        assertEquals(transferenciaEsperado.getNumeroContaDestino(), resultado.getBody().getNumeroContaDestino());
        assertEquals(transferenciaEsperado.getValor(), resultado.getBody().getValor());
        assertEquals(transferenciaEsperado.getData(), resultado.getBody().getData());
        assertEquals(transferenciaEsperado.getSituacao(), resultado.getBody().getSituacao());

    }

    @Test
    public void BuscarTransferenciasPorNumeroConta() {

        final Cliente clienteOrigem = TransferenciaCommandsHandlerMockFactory.clienteOrigem();
        final Cliente clienteDestino = TransferenciaCommandsHandlerMockFactory.clienteDestino();

        this.clienteJpaRepository.save(clienteOrigem);
        this.clienteJpaRepository.save(clienteDestino);

        Transferencia transferencia =
                TransferenciaControllerIntegrationMockFactory.transferenciaInserir(
                        3000D, LocalDateTime.now(), Situacao.CONCLUIDO_COM_ERRO);

        Transferencia transferencia1 =
                TransferenciaControllerIntegrationMockFactory.transferenciaInserir(
                        300D, LocalDateTime.now().plusDays(1), Situacao.CONCLUIDO_COM_SUCESSO);

        Transferencia transferencia2 =
                TransferenciaControllerIntegrationMockFactory.transferenciaInserir(
                        200D, LocalDateTime.now().plusDays(2), Situacao.CONCLUIDO_COM_SUCESSO);

        this.transferenciaJpaRepository.save(transferencia);
        this.transferenciaJpaRepository.save(transferencia1);
        this.transferenciaJpaRepository.save(transferencia2);

        List<Transferencia> transferenciasEsperado = new ArrayList<>();
        transferenciasEsperado.add(transferencia2);
        transferenciasEsperado.add(transferencia1);
        transferenciasEsperado.add(transferencia);

        ParameterizedTypeReference<List<Transferencia>> tipoResultado =
                new ParameterizedTypeReference<List<Transferencia>>() {};

        ResponseEntity<List<Transferencia>> resultado =
                testRestTemplate.exchange(
                        URL_BASE + NUMERO_CONTA_QUERY, HttpMethod.GET, null, tipoResultado, NUMERO_CONTA);


        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(transferenciasEsperado.get(ZERO).getId(), resultado.getBody().get(ZERO).getId());
        assertEquals(transferenciasEsperado.get(UM).getId(), resultado.getBody().get(UM).getId());
        assertEquals(transferenciasEsperado.get(DOIS).getId(), resultado.getBody().get(DOIS).getId());
    }

}
