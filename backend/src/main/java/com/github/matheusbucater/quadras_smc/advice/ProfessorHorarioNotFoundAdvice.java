package com.github.matheusbucater.quadras_smc.advice;

import com.github.matheusbucater.quadras_smc.dto.ErrorMessageDTO;
import com.github.matheusbucater.quadras_smc.exception.ProfessorHorarioNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProfessorHorarioNotFoundAdvice {

    @ExceptionHandler(ProfessorHorarioNotFoundException.class)
    public ResponseEntity<ErrorMessageDTO> professorHorarioNotFound(ProfessorHorarioNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessageDTO(ex.toString(), ex.getMessage()));
    }
}
