package com.clientes.transferencias.resources.exception;

import org.hibernate.id.IdentifierGenerationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;
import javax.validation.ValidationException;
import java.util.Collections;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String NUMERO_CONTA_ERROR_MESSAGE = "O número da conta informado já está sendo utilizado.";

    /**
     * Manipula as excecoes geradas durante o uso da aplicacao e as converte para um objeto de response com os erros
     * encontrados
     * @param ex excecoes que irao ser manipuladas e convertidas
     * @param request requisicao com os dados de origem da exception e o path da o qual a mesma se originou
     * @return um objeto de resposta com o erros das excecoes manipualadas
     */
    @ExceptionHandler({
        Exception.class,
        IllegalArgumentException.class,
        ValidationException.class
    })
    public final ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {

        if(ex instanceof IllegalArgumentException) {
            return handleIllegalArgumentException(ex, request);

        } else if(ex instanceof ValidationException) {
            return handleValidationException(ex, request);

        } else if(ex instanceof DataIntegrityViolationException) {
            return handleDataIntegrityViolationException(ex, request);

        } else if(ex instanceof IdentifierGenerationException) {
            return handleIdentifierGenerationException(ex, request);

        } else {
            return handleGenericException(ex, request);
        }
    }

    private ResponseEntity<ApiError> handleGenericException(Exception ex, WebRequest request){
        return handleExceptionInternal(
                ex,
                new ApiError(Collections.singletonList(ex.getMessage())),
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request
        );
    }

    private ResponseEntity<ApiError> handleIllegalArgumentException(Exception ex, WebRequest request) {
        return handleExceptionInternal(
                ex,
                new ApiError(Collections.singletonList(ex.getMessage())),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    private ResponseEntity<ApiError> handleDataIntegrityViolationException(Exception ex, WebRequest request) {
        return handleExceptionInternal(
                ex,
                new ApiError(Collections.singletonList(NUMERO_CONTA_ERROR_MESSAGE)),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    private ResponseEntity<ApiError> handleIdentifierGenerationException(Exception ex, WebRequest request) {
        return handleExceptionInternal(
                ex,
                new ApiError(Collections.singletonList(NUMERO_CONTA_ERROR_MESSAGE)),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    private ResponseEntity<ApiError> handleValidationException(Exception ex, WebRequest request) {
        return handleExceptionInternal(
                ex,
                new ApiError(Collections.singletonList(ex.getMessage())),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    private ResponseEntity<ApiError> handleExceptionInternal(Exception ex, ApiError body,
                                                             HttpHeaders headers, HttpStatus status,
                                                             WebRequest request) {

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        return new ResponseEntity<>(body, headers, status);
    }

}