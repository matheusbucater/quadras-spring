package com.github.matheusbucater.quadras_smc.advice;

import com.github.matheusbucater.quadras_smc.dto.ErrorMessageDTO;
import com.github.matheusbucater.quadras_smc.exception.UsuarioForbiddenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UsuarioForbiddenAdvice {

    @ExceptionHandler(UsuarioForbiddenException.class)
    public ResponseEntity<ErrorMessageDTO> usuarioForbidden(UsuarioForbiddenException ex) {

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorMessageDTO(ex.toString(), ex.getMessage()));
    }
}
