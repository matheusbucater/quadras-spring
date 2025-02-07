package com.github.matheusbucater.quadras_smc.service;

import com.github.matheusbucater.quadras_smc.config.RabbitMQConfig;
import com.github.matheusbucater.quadras_smc.dto.EmailDTO;
import com.github.matheusbucater.quadras_smc.dto.EmailQueueDTO;
import com.github.matheusbucater.quadras_smc.entity.Email;
import jakarta.mail.MessagingException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// @Component
@Service
public class MessageConsumerService {

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE)
    public void receiveEmail(EmailQueueDTO email) throws MessagingException {
        emailService.sendEmail(email);
    }
}
