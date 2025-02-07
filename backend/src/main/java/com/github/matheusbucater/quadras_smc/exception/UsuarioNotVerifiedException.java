package com.github.matheusbucater.quadras_smc.exception;

public class UsuarioNotVerifiedException extends RuntimeException {

    public UsuarioNotVerifiedException(String email) {
        super("Usuario " + email + " nao verificado.");
    }

    @Override
    public String toString() {
        return "usuario_not_verified";
    }
}
