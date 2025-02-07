package com.github.matheusbucater.quadras_smc.advice;

import com.github.matheusbucater.quadras_smc.dto.ErrorMessageDTO;
import com.github.matheusbucater.quadras_smc.dto.MessageDTO;
import com.github.matheusbucater.quadras_smc.exception.UsuarioTelefoneAlreadyTakenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UsuarioTelefoneAlreadyTakenAdvice {

    @ExceptionHandler(UsuarioTelefoneAlreadyTakenException.class)
    public ResponseEntity<ErrorMessageDTO> usuarioTelefoneAlreadyTaken(UsuarioTelefoneAlreadyTakenException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessageDTO(ex.toString(), ex.getMessage()));
    }
}
