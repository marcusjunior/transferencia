package com.clientes.transferencias.resources;

import com.clientes.transferencias.application.command.TransferenciaCommandsHandler;
import com.clientes.transferencias.application.query.TransferenciaQueriesHandler;
import com.clientes.transferencias.domain.model.transferencia.BuscarTransferenciasPorNumeroConta;
import com.clientes.transferencias.domain.model.transferencia.RealizarTransferencia;
import com.clientes.transferencias.domain.model.transferencia.Transferencia;
import com.clientes.transferencias.resources.dto.ClienteDTO;
import com.clientes.transferencias.resources.dto.TransferenciaDTO;
import com.clientes.transferencias.resources.translate.TransferenciaTranslator;
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
@RequestMapping(value = "/v1/transferencia")
@Api(tags = "Transferencias")
public class TransferenciaController {

    @Autowired
    private TransferenciaCommandsHandler transferenciaCommandsHandler;

    @Autowired
    private TransferenciaQueriesHandler transferenciaQueriesHandler;

    public TransferenciaController(TransferenciaCommandsHandler transferenciaCommandsHandler) {
        this.transferenciaCommandsHandler = transferenciaCommandsHandler;
    }

    @PostMapping
    @ApiOperation(value = "Realizar transferencia")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Requisição realizada.", response = Transferencia.class),
            @ApiResponse(code = 400, message = "Requisição inválida."),
            @ApiResponse(code = 500, message = "Erro interno.")
    })
    public ResponseEntity realizarTransferencia(@RequestBody @Validated RealizarTransferencia realizarTransferencia) throws Exception {

        return this
                .transferenciaCommandsHandler
                .handle(realizarTransferencia)
                .map(ResponseEntity::ok)
                .orElseThrow(RuntimeException::new);
    }

    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Buscar transferencias por numero conta")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Transferencias retornadas com sucesso.", response = ClienteDTO.class),
            @ApiResponse(code = 400, message = "Requisição inválida."),
            @ApiResponse(code = 404, message = "Transferencias não encontradas."),
            @ApiResponse(code = 500, message = "Erro interno.")
    })
    public ResponseEntity<List<TransferenciaDTO>> buscarTransferencias(@Validated BuscarTransferenciasPorNumeroConta buscarTransferenciasPorNumeroConta) {

        List<TransferenciaDTO> transferencias =
            TransferenciaTranslator
                .paraLista.apply(
                    this
                        .transferenciaQueriesHandler
                        .handle(buscarTransferenciasPorNumeroConta)
                );

        return ResponseEntity.ok(transferencias);
    }

}
