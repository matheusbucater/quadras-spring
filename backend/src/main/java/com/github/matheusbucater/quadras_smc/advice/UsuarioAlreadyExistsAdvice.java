package com.github.matheusbucater.quadras_smc.advice;

import com.github.matheusbucater.quadras_smc.dto.ErrorMessageDTO;
import com.github.matheusbucater.quadras_smc.exception.UsuarioAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UsuarioAlreadyExistsAdvice {

    @ExceptionHandler(UsuarioAlreadyExistsException.class)
    public ResponseEntity<ErrorMessageDTO> usuarioAlreadyExists(UsuarioAlreadyExistsException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessageDTO(ex.toString(), ex.getMessage()));
    }
}
