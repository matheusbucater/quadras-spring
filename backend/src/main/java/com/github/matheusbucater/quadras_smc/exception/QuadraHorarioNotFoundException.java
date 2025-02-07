package com.github.matheusbucater.quadras_smc.exception;

public class QuadraHorarioNotFoundException extends RuntimeException {

    public QuadraHorarioNotFoundException(Long id) {
        super("QuadraHorario " + id + " nao encontrado.");
    }

    @Override
    public String toString() {
        return "quadra_horario_not_found";
    }
}
