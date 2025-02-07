package com.github.matheusbucater.quadras_smc.exception;

public class EmailNotFoundException extends RuntimeException {

    public EmailNotFoundException(Long id) {
        super("Email not found: " + id);
    }

    @Override
    public String toString() {
        return "email_not_found";
    }
}
