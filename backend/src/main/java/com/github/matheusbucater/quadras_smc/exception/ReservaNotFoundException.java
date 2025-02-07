package com.github.matheusbucater.quadras_smc.exception;

public class ReservaNotFoundException extends RuntimeException {

    public ReservaNotFoundException(Long id) {
        super("Reserva" + id + " nao encontrado.");
    }

    @Override
    public String toString() {
        return "reserva_not_found";
    }

}
