package com.github.matheusbucater.quadras_smc.exception;

public class UsuarioTelefoneAlreadyTakenException extends RuntimeException {

    public UsuarioTelefoneAlreadyTakenException(String telefone) {
        super("Telefone já está em uso: " + telefone);
    }

    @Override
    public String toString() {
        return "usuario_telefone_already_taken";
    }
}
