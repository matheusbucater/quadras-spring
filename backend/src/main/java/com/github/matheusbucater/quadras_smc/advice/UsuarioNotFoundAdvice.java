package com.github.matheusbucater.quadras_smc.advice;

import com.github.matheusbucater.quadras_smc.dto.ErrorMessageDTO;
import com.github.matheusbucater.quadras_smc.dto.MessageDTO;
import com.github.matheusbucater.quadras_smc.exception.UsuarioNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UsuarioNotFoundAdvice {

    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<ErrorMessageDTO> usuarioNotFound(UsuarioNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessageDTO(ex.toString(), ex.getMessage()));
    }
}
