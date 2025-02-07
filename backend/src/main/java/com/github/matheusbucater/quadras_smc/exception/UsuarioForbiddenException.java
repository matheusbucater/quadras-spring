package com.github.matheusbucater.quadras_smc.exception;

public class UsuarioForbiddenException extends RuntimeException {

    public UsuarioForbiddenException(Long id) {
        super("Usuario " + id + " proibido");
    }

    @Override
    public String toString() {
        return "usuario_forbidden";
    }
}
