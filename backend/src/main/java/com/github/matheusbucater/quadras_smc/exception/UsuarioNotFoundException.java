package com.github.matheusbucater.quadras_smc.exception;

public class UsuarioNotFoundException extends RuntimeException {

    public UsuarioNotFoundException(Long id) {
        super("Usuario " + id + " nao encontrado.");
    }

    public UsuarioNotFoundException(String email) {
        super("Usuario " + email + " nao encontrado.");
    }

    @Override
    public String toString() {
        return "usuario_not_found";
    }
}
