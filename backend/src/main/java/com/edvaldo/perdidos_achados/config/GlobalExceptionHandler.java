package com.edvaldo.perdidos_achados.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.edvaldo.perdidos_achados.exception.usuario.EmailJaCadastradoException;



@RestControllerAdvice
public class GlobalExceptionHandler  {

     @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();

        for (FieldError erro : ex.getBindingResult().getFieldErrors()) {
            erros.put(erro.getField(), erro.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }

    
     @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<Map<String, String>> handleEmailDuplicado(EmailJaCadastradoException ex) {
        Map<String, String> erro = new HashMap<>();
        erro.put("email", ex.getMessage());
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);

    }
    
}
