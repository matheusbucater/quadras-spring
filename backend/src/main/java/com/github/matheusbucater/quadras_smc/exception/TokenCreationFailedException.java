package com.github.matheusbucater.quadras_smc.exception;

public class TokenCreationFailedException extends RuntimeException {

    public TokenCreationFailedException(String message) {
        super("token creation failed: " + message);
    }

    @Override
    public String toString() {
        return "token_creation_failed";
    }
}
