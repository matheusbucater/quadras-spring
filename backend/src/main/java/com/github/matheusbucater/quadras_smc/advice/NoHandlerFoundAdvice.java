package com.github.matheusbucater.quadras_smc.advice;

import com.github.matheusbucater.quadras_smc.dto.ErrorMessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class NoHandlerFoundAdvice {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorMessageDTO> handlerNotFound(NoHandlerFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessageDTO("no_handler_found", ex.getMessage()));
    }
}
