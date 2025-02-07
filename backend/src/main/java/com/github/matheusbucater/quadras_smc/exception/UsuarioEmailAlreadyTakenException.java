package com.github.matheusbucater.quadras_smc.exception;

public class UsuarioEmailAlreadyTakenException extends RuntimeException {

    public UsuarioEmailAlreadyTakenException(String email) {
        super("Endereço de email já está em uso: " + email);
    }

    @Override
    public String toString() {
        return "usuario_email_already_taken";
    }
}
