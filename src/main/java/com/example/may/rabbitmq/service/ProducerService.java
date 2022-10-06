package com.example.may.rabbitmq.service;

import com.example.may.email.config.EmailProperties;
import com.example.may.email.model.BirthdayUser;
import com.example.may.email.model.EmailMessage;
import com.example.may.email.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Evelina Tubalets
 */
@Slf4j
@Service
@AllArgsConstructor
public class ProducerService {

    private final EmailService emailService;
    private final RabbitTemplate rabbitTemplate;

    public void sendTestEmailMessage() {
        final EmailMessage message = createTestMessage();
        rabbitTemplate.convertAndSend("x.send-email", "send-email", message);
        log.info("sent message to RabbitMQ");
    }

    public void sendCongratulationEmailMessage(final List<BirthdayUser> birthdayUsers) {
        final EmailMessage message = createMessageToRabbitMQ(birthdayUsers);
        rabbitTemplate.convertAndSend("x.send-email", "send-email", message);
        log.info("send message to RabbitMQ");
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

    private EmailMessage createMessageToRabbitMQ(final List<BirthdayUser> birthdayUsers) {
        final EmailProperties emailProperties = emailService.getEmailProperties();
        final EmailMessage request = new EmailMessage();
        request.setUsers(birthdayUsers);
        request.setEmailProperties(emailProperties);
        return request;
    }
}
