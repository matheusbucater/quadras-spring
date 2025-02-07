package com.github.matheusbucater.quadras_smc.exception;

public class UsuarioAlreadyExistsException extends RuntimeException {

    public UsuarioAlreadyExistsException(String email) {
        super("Usuario " + email + " já existe");
    }

    @Override
    public String toString() {
        return "usuario_already_exists";
    }
}
