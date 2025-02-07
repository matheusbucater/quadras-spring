package com.github.matheusbucater.quadras_smc.advice;

import com.github.matheusbucater.quadras_smc.dto.ErrorMessageDTO;
import com.github.matheusbucater.quadras_smc.exception.ReservaNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ReservaNotFoundAdvice {

    @ExceptionHandler(ReservaNotFoundException.class)
    public ResponseEntity<ErrorMessageDTO> usuarioNotFound(ReservaNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessageDTO(ex.toString(), ex.getMessage()));
    }
}
