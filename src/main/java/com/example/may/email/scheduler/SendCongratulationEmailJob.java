package com.example.may.email.scheduler;

import com.example.may.email.config.EmailProperties;
import com.example.may.email.model.BirthdayUser;
import com.example.may.email.model.EmailMessage;
import com.example.may.email.service.EmailService;
import com.example.may.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Evelina Tubalets
 */
@Slf4j
@Component
@AllArgsConstructor
public class SendCongratulationEmailJob {

    private final EmailService emailService;
    private final RabbitTemplate rabbitTemplate;

    /**
     * Job which every day sends message to RabbitMQ with list of birthday users and email properties for sending
     * congratulation email.
     * Email Service will get this message from RabbitMQ, parse it and send congratulation letters to users emails.
     */
    @Scheduled(cron = "0 0 0 * * *")
    private void send() {
        final EmailMessage message = createMessageToRabbitMQ();
        rabbitTemplate.convertAndSend("x.send-email", "send-email", message);
        log.info("send message to RabbitMQ");
    }

    private EmailMessage createMessageToRabbitMQ() {
        final EmailProperties emailProperties = emailService.getEmailProperties();
        final List<BirthdayUser> birthdayUsers = getBirthdayUsers();

        final EmailMessage request = new EmailMessage();
        request.setUsers(birthdayUsers);
        request.setEmailProperties(emailProperties);
        return request;
    }

    private List<BirthdayUser> getBirthdayUsers() {
        final List<User> birthdayUserEmails = emailService.getBirthdayUserEmails();
        return convertUsers(birthdayUserEmails);
    }

    private List<BirthdayUser> convertUsers(final List<User> users) {
        return users.stream()
                .map(this::convertUser)
                .collect(Collectors.toList());
    }

    private BirthdayUser convertUser(final User user) {
        final String firstName = user.getFirstName();
        final String lastName = user.getLastName();
        final String email = user.getEmail();

        final BirthdayUser birthdayUser = new BirthdayUser();
        birthdayUser.setFirstName(firstName);
        birthdayUser.setLastName(lastName);
        birthdayUser.setEmail(email);
        return birthdayUser;
    }
}
