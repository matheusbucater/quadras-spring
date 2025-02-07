package com.github.matheusbucater.quadras_smc.controller;

import com.github.matheusbucater.quadras_smc.dto.EmailDTO;
import com.github.matheusbucater.quadras_smc.dto.EmailQueueDTO;
import com.github.matheusbucater.quadras_smc.dto.MessageDTO;
import com.github.matheusbucater.quadras_smc.entity.Email;
import com.github.matheusbucater.quadras_smc.enums.EmailStatus;
import com.github.matheusbucater.quadras_smc.repository.EmailRepository;
import com.github.matheusbucater.quadras_smc.service.MessageProducerService;
import com.github.matheusbucater.quadras_smc.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/email")
public class EmailController {


    @Autowired
    private MessageProducerService messageProducerService;
    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailRepository emailRepository;

    @GetMapping("/queue")
    public ResponseEntity<MessageDTO> queueEmail(@RequestParam String token) {

        EmailDTO data = this.tokenService.validateEmailToken(token);

        EmailQueueDTO email = EmailQueueDTO.from(
                this.emailRepository.save(
                        new Email(
                                EmailStatus.QUEUED,
                                "matheusp.bucater@gmail.com",
                                data.to(),
                                data.subject(),
                                data.body()
                        )
                )
        );

        this.messageProducerService.sendMessage(email);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageDTO("Email enviado!"));
    }
}
