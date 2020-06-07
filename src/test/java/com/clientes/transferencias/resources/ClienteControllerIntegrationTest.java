package com.clientes.transferencias.resources;

import com.clientes.transferencias.domain.model.cliente.Cliente;
import com.clientes.transferencias.port.adapter.repository.cliente.ClienteJpaRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClienteControllerIntegrationTest {

    private final static String URL_BASE = "/v1/clientes";
    private final static String NUMERO_CONTA_QUERY = "?numeroConta={numeroConta}";
    private final static String LISTAR_PATH = "/listarTodos";
    private static final Integer ZERO = 0;
    private static final Integer UM = 1;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ClienteJpaRepository clienteJpaRepository;

    @AfterEach
    public void clean(){
        this.clienteJpaRepository.deleteAll();
    }

    @Test
    public void criarNovoClienteComSucesso() {
        Cliente clienteEsperado = ClienteControllerIntegrationMockFactory.cliente();

        HttpEntity<Cliente> httpEntity = new HttpEntity<>(clienteEsperado);

        ResponseEntity<Cliente> resultado =
            testRestTemplate.postForEntity(URL_BASE, httpEntity, Cliente.class);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(clienteEsperado, resultado.getBody());
    }

    @Test
    public void criarNovoClienteComErro() {
        Cliente clienteEsperado = ClienteControllerIntegrationMockFactory.cliente();
        this.clienteJpaRepository.save(clienteEsperado);

        HttpEntity<Cliente> httpEntity = new HttpEntity<>(clienteEsperado);

        ResponseEntity<Cliente> resultado =
                testRestTemplate.postForEntity(URL_BASE, httpEntity, Cliente.class);

        assertEquals(HttpStatus.BAD_REQUEST, resultado.getStatusCode());
    }

    @Test
    public void buscarClientePorNumeroContaComSucesso() {
        Cliente clienteEsperado = ClienteControllerIntegrationMockFactory.cliente();
        this.clienteJpaRepository.save(clienteEsperado);

        ResponseEntity<Cliente> resultado =
            testRestTemplate
                .getForEntity(
                    URL_BASE + NUMERO_CONTA_QUERY,
                        Cliente.class, clienteEsperado.getNumeroConta());

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(clienteEsperado, resultado.getBody());
    }

    @Test
    public void buscarClientePorNumeroContaInexistente() {
        Cliente clienteEsperado = ClienteControllerIntegrationMockFactory.cliente();

        ResponseEntity<Cliente> resultado =
            testRestTemplate
                .getForEntity(
                    URL_BASE + NUMERO_CONTA_QUERY,
                    Cliente.class, clienteEsperado.getNumeroConta());

        assertEquals(HttpStatus.NOT_FOUND, resultado.getStatusCode());
        assertNull(resultado.getBody());
    }

    @Test
    public void listarClientes() {
        List<Cliente> clientesEsperado = ClienteControllerIntegrationMockFactory.clientes();
        this.clienteJpaRepository.save(clientesEsperado.get(ZERO));
        this.clienteJpaRepository.save(clientesEsperado.get(UM));

        ParameterizedTypeReference<List<Cliente>> tipoResultado =
                new ParameterizedTypeReference<List<Cliente>>() {};

        ResponseEntity<List<Cliente>> resultado =
                testRestTemplate.exchange(
                        URL_BASE + LISTAR_PATH, HttpMethod.GET,null, tipoResultado);


        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(clientesEsperado.get(ZERO), resultado.getBody().get(ZERO));
        assertEquals(clientesEsperado.get(UM), resultado.getBody().get(UM));
    }

}
