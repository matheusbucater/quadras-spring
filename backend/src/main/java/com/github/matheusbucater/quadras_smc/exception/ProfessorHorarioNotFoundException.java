package com.github.matheusbucater.quadras_smc.exception;

public class ProfessorHorarioNotFoundException extends RuntimeException {

    public ProfessorHorarioNotFoundException(Long id) {
        super("ProfessorHorario" + id + " nao encontrado.");
    }

    @Override
    public String toString() {
        return "professor_horario_not_found";
    }
}
