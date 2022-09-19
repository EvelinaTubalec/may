package com.example.may.mail.service;

import com.example.may.mail.config.model.MailProperties;
import com.example.may.user.entity.User;
import com.example.may.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.time.LocalDate.now;

/**
 * @author Evelina Tubalets
 */
@Service
@AllArgsConstructor
public class MailService {

    private final UserRepository userRepository;
    private final MailProperties properties;
    private final MailPropertyReceiver propertyReceiver;

    public void sendCongratulationEmail() {
        final List<User> birthdayUsers = getBirthdayUserEmails();
        birthdayUsers.forEach(this::createCongratulationMessageAndSend);
    }

    public void sendTestEmail() {
        final SimpleMailMessage testMessage = createTestMessage();
        getMailSender().send(testMessage);
    }

    private List<User> getBirthdayUserEmails() {
        return userRepository.findAll().stream().filter(this::isBirthdayToday).collect(Collectors.toList());
    }

    private boolean isBirthdayToday(final User user) {
        final int presentDay = now().getDayOfMonth();
        final int presentMonth = now().getMonthValue();
        return user.getDateOfBirth().getDayOfMonth() == presentDay && user.getDateOfBirth().getMonthValue() == presentMonth;
    }

    private void createCongratulationMessageAndSend(final User user) {
        final SimpleMailMessage message = createCongratulationMessage(user);
        getMailSender().send(message);
    }

    private SimpleMailMessage createCongratulationMessage(final User user) {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Congratulation Message");
        message.setText(format("%s %s, Happy Birthday!", user.getFirstName(), user.getLastName()));
        message.setFrom(properties.getEmailFrom());
        return message;
    }

    private SimpleMailMessage createTestMessage() {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("toEmail");
        message.setSubject("Test Message");
        message.setText("Hello!");
        message.setFrom(properties.getEmailFrom());
        return message;
    }

    private JavaMailSender getMailSender() {
        final MailProperties properties = propertyReceiver.getEmailPropertiesFromDestinationService();
        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(properties.getHost());
        mailSender.setPort(properties.getPort());
        mailSender.setUsername(properties.getUsername());
        mailSender.setPassword(properties.getPassword());
        return mailSender;
    }
}
