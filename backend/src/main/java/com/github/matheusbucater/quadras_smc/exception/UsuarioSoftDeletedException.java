package com.github.matheusbucater.quadras_smc.exception;

public class UsuarioSoftDeletedException extends RuntimeException {

    public UsuarioSoftDeletedException(String email) {
        super("Usuario inativo: " + email);
    }

    @Override
    public String toString() {
        return "usuario_soft_deleted";
    }
}
