package com.github.matheusbucater.quadras_smc.dto;

import com.github.matheusbucater.quadras_smc.entity.Email;
import com.github.matheusbucater.quadras_smc.enums.EmailStatus;

import java.io.Serializable;

public record EmailQueueDTO(Long id, String to, String subject, String body, EmailStatus email_status) implements Serializable {

    public static EmailQueueDTO from(Email email) {
        return new EmailQueueDTO(
                email.getId(),
                email.getTo(),
                email.getSubject(),
                email.getBody(),
                email.getEmailStatus()
        );
    }
}
