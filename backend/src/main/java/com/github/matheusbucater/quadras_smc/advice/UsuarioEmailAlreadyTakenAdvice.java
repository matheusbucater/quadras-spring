package com.github.matheusbucater.quadras_smc.advice;

import com.github.matheusbucater.quadras_smc.dto.ErrorMessageDTO;
import com.github.matheusbucater.quadras_smc.dto.MessageDTO;
import com.github.matheusbucater.quadras_smc.exception.UsuarioEmailAlreadyTakenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UsuarioEmailAlreadyTakenAdvice {

    @ExceptionHandler(UsuarioEmailAlreadyTakenException.class)
    public ResponseEntity<ErrorMessageDTO> usuarioEmailAlreadyTaken(UsuarioEmailAlreadyTakenException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessageDTO(ex.toString(), ex.getMessage()));
    }
}
