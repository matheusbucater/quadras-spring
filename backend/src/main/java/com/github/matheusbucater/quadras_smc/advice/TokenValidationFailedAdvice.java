package com.github.matheusbucater.quadras_smc.advice;

import com.github.matheusbucater.quadras_smc.dto.ErrorMessageDTO;
import com.github.matheusbucater.quadras_smc.exception.TokenCreationFailedException;
import com.github.matheusbucater.quadras_smc.exception.TokenValidationFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TokenValidationFailedAdvice {

    @ExceptionHandler(TokenValidationFailedException.class)
    public ResponseEntity<ErrorMessageDTO> tokenValidationFailed(TokenValidationFailedException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessageDTO(ex.toString(), ex.getMessage()));
    }
}
