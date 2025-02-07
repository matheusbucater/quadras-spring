package com.github.matheusbucater.quadras_smc.service;

import com.github.matheusbucater.quadras_smc.dto.EmailQueueDTO;
import com.github.matheusbucater.quadras_smc.entity.Email;
import com.github.matheusbucater.quadras_smc.enums.EmailStatus;
import com.github.matheusbucater.quadras_smc.exception.EmailAlreadySendException;
import com.github.matheusbucater.quadras_smc.exception.EmailNotFoundException;
import com.github.matheusbucater.quadras_smc.repository.EmailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailRepository emailRepository;

    public void sendEmail(EmailQueueDTO data) throws MessagingException {

        Email email = this.emailRepository.findById(data.id())
                .orElseThrow(() -> new EmailNotFoundException(data.id()));

        if (email.getEmailStatus().equals(EmailStatus.SEND)) {
            throw new EmailAlreadySendException(email.getId());
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email.getTo());
            helper.setSubject(email.getSubject());
            helper.setText(email.getBody(), true);

            mailSender.send(message);

            email.setEmailStatus(EmailStatus.SEND);
            email.setObs("OK");
            emailRepository.save(email);
        } catch (Exception e) {
            email.setEmailStatus(EmailStatus.FAILED);
            email.setObs(e.getMessage());
            emailRepository.save(email);

            throw e;
        }
    }
}
