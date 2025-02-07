package com.github.matheusbucater.quadras_smc.exception;

public class UsuarioAlreadyVerifiedException extends RuntimeException {

    public UsuarioAlreadyVerifiedException(String email) {
        super("Usuario " + email + " já está verificado.");
    }

    @Override
    public String toString() {
        return "usuario_already_verified";
    }
}
