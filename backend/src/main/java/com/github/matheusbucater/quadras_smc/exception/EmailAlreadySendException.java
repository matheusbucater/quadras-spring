package com.github.matheusbucater.quadras_smc.exception;

public class EmailAlreadySendException extends RuntimeException {

    public EmailAlreadySendException(Long id) {
        super("Email " + id + " already sent");
    }

    @Override
    public String toString() {
        return "email_already_sent";
    }
}
