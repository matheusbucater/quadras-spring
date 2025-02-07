package com.github.matheusbucater.quadras_smc.service;

import com.github.matheusbucater.quadras_smc.config.RabbitMQConfig;
import com.github.matheusbucater.quadras_smc.dto.EmailQueueDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageProducerService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(EmailQueueDTO email) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EMAIL_QUEUE,
                email
        );
    }
}
