package com.example.may.email.service;

import com.example.may.email.config.model.EmailProperties;
import com.example.may.user.entity.User;
import com.example.may.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
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
public class EmailService {

    private final String host;
    private final UserRepository userRepository;
    private final EmailProperties properties;

    public EmailService(
            @Value("${spring.mail.host}") final String host,
            final UserRepository userRepository,
            final EmailProperties properties) {
        this.host = host;
        this.userRepository = userRepository;
        this.properties = properties;
    }

    public void sendCongratulationEmail() {
        final List<User> birthdayUsers = getBirthdayUserEmails();
        birthdayUsers.forEach(this::createCongratulationMessageAndSend);
    }

    private List<User> getBirthdayUserEmails() {
        return userRepository.findAll()
                .stream()
                .filter(this::isBirthdayToday)
                .collect(Collectors.toList());
    }

    private boolean isBirthdayToday(final User user) {
        final int presentDay = now().getDayOfMonth();
        final int presentMonth = now().getMonthValue();
        return user.getDateOfBirth().getDayOfMonth() == presentDay
                && user.getDateOfBirth().getMonthValue() == presentMonth;
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

    private JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(properties.getPort());
        mailSender.setUsername(properties.getUsername());
        mailSender.setPassword(properties.getPassword());
        return mailSender;
    }
}
