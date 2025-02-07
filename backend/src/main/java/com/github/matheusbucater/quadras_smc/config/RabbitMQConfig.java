package com.github.matheusbucater.quadras_smc.config;

import com.github.matheusbucater.quadras_smc.dto.EmailDTO;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EMAIL_QUEUE = "emailQueue";

    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE, true);
    }

    @Bean
    public MessageConverter messageConverter() {
        Jackson2JsonMessageConverter jsonMessageConverter = new Jackson2JsonMessageConverter();
        jsonMessageConverter.getJavaTypeMapper().addTrustedPackages("com.github.matheusbucater.quadras_smc.dto");
        return jsonMessageConverter;
    }
}
