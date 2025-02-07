package com.github.matheusbucater.quadras_smc.exception;

public class QuadraNotFoundException extends RuntimeException {

    public QuadraNotFoundException(Long id) {
        super("Quadra " + id + " nao encontrada.");
    }

    @Override
    public String toString() {
        return "quadra_not_found";
    }
}
