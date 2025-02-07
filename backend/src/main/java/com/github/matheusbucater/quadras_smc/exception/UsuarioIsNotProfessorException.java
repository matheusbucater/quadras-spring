package com.github.matheusbucater.quadras_smc.exception;

public class UsuarioIsNotProfessorException extends RuntimeException {

    public UsuarioIsNotProfessorException(Long id) {
        super("Usuario " + id + " não é professor");
    }

    @Override
    public String toString() {
        return "usuario_is_not_professor";
    }
}
