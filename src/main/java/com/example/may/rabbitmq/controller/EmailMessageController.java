package com.example.may.rabbitmq.controller;

import com.example.may.email.config.EmailProperties;
import com.example.may.email.model.EmailMessage;
import com.example.may.email.service.EmailService;
import com.example.may.email.model.BirthdayUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Evelina Tubalets
 */
@Slf4j
@RestController
@RequestMapping("/messages")
@AllArgsConstructor
public class EmailMessageController {

    private final EmailService emailService;
    private final RabbitTemplate rabbitTemplate;

    /**
     * Endpoint which helps to test sending message to rabbitMQ.
     */
    @PostMapping("/emails")
    public void sendEmailMessageToRabbitMQ() {
        final EmailMessage message = createTestMessage();
        rabbitTemplate.convertAndSend("x.send-email","send-email", message);
        log.info("sent message to RabbitMQ");
    }

    private EmailMessage createTestMessage() {
        final EmailProperties emailProperties = emailService.getEmailProperties();
        final BirthdayUser user = new BirthdayUser();
        user.setEmail("email");
        user.setFirstName("firstName");
        user.setLastName("lastName");

        final EmailMessage message = new EmailMessage();
        message.setEmailProperties(emailProperties);
        message.setUsers(List.of(user));
        return message;
    }
}
