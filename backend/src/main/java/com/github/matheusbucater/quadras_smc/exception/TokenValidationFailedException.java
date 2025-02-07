package com.github.matheusbucater.quadras_smc.exception;

public class TokenValidationFailedException extends RuntimeException {

    public TokenValidationFailedException(String message) {
        super("token validation failed: " + message);
    }

    @Override
    public String toString() {
        return "token_validation_failed";
    }
}
