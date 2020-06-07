package com.clientes.transferencias.resources;

import com.clientes.transferencias.application.command.ClienteCommandsHandler;
import com.clientes.transferencias.application.query.ClienteQueriesHandler;
import com.clientes.transferencias.domain.model.cliente.BuscarClientePorNumeroConta;
import com.clientes.transferencias.domain.model.cliente.CriarCliente;
import com.clientes.transferencias.resources.dto.ClienteDTO;
import com.clientes.transferencias.resources.translate.ClienteTranslator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/clientes")
@Api(tags = "Clientes")
public class ClienteController {

    @Autowired
    private ClienteCommandsHandler clienteCommandsHandler;

    @Autowired
    private ClienteQueriesHandler clienteQueriesHandler;

    public ClienteController(ClienteCommandsHandler clienteCommandsHandler) {
        this.clienteCommandsHandler = clienteCommandsHandler;
    }

    @PostMapping
    @ApiOperation(value = "Criar cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Requisição realizada.", response = ClienteDTO.class),
            @ApiResponse(code = 400, message = "Requisição inválida."),
            @ApiResponse(code = 500, message = "Erro interno.")
    })
    public ResponseEntity criarCliente(@RequestBody @Validated CriarCliente criarCliente) throws Exception {

        return this
                .clienteCommandsHandler
                .handle(criarCliente)
                .map(ResponseEntity::ok)
                .orElseThrow(RuntimeException::new);
    }

    @GetMapping(
            value = "listarTodos",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Listar todos os clientes")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Clientes retornados com sucesso.", response = ClienteDTO.class),
            @ApiResponse(code = 400, message = "Requisição inválida."),
            @ApiResponse(code = 500, message = "Erro interno.")
    })
    public ResponseEntity<List<ClienteDTO>> listarTodos() {

        List<ClienteDTO> clientes =
                ClienteTranslator
                        .paraLista.apply(this.clienteQueriesHandler.handle());

        return ResponseEntity.ok(clientes);
    }

    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Buscar cliente por numero conta")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cliente retornado com sucesso.", response = ClienteDTO.class),
            @ApiResponse(code = 400, message = "Requisição inválida."),
            @ApiResponse(code = 404, message = "Cliente não encontrado."),
            @ApiResponse(code = 500, message = "Erro interno.")
    })
    public ResponseEntity<ClienteDTO> buscarCliente(@Validated BuscarClientePorNumeroConta buscarClientePorNumeroConta) {

        return this
                .clienteQueriesHandler
                .handle(buscarClientePorNumeroConta)
                .map(ClienteTranslator.paraRecurso)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
