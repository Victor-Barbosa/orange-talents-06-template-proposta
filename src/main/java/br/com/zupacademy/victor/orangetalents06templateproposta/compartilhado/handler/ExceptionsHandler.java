package br.com.zupacademy.victor.orangetalents06templateproposta.compartilhado.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExcepetionResponse> validationHandler(MethodArgumentNotValidException ex){

        var fieldErrors = ex.getBindingResult().getFieldErrors();

        var campos = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        var mensagem = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        return ResponseEntity.status(BAD_REQUEST).body(new ExcepetionResponse(campos, mensagem, String.valueOf(BAD_REQUEST)));

    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExcepetionResponse> responseStatusHandler(ResponseStatusException ex){

        var status = ex.getStatus();
        var mensagem = ex.getReason();

        return ResponseEntity.status(status).body(new ExcepetionResponse("Ocorreu um erro", mensagem, String.valueOf(status)));
    }

}
