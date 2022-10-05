package com.example.may.email.service;

import com.example.may.cloudfoundry.destination.model.Destination;
import com.example.may.cloudfoundry.destination.service.DestinationService;
import com.example.may.core.web.converter.JsonConverter;
import com.example.may.email.config.model.EmailProperties;
import com.example.may.user.entity.User;
import com.example.may.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.time.LocalDate.now;

/**
 * @author Evelina Tubalets
 */
@Service
@AllArgsConstructor
public class EmailService {

    public static final String MAIL_DESTINATION_NAME = "mail-destination";

    private final UserRepository userRepository;
    private final EmailProperties properties;
    private final DestinationService destinationService;

    public void sendCongratulationEmail() {
        final List<User> birthdayUsers = getBirthdayUserEmails();
        birthdayUsers.forEach(this::createCongratulationMessageAndSend);
    }

    public void sendTestEmail() {
        final SimpleMailMessage testMessage = createTestMessage();
        final JavaMailSender mailSender = getMailSender();
        mailSender.send(testMessage);
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
        final EmailProperties properties = getEmailProperties();
        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(properties.getHost());
        mailSender.setPort(properties.getPort());
        mailSender.setUsername(properties.getUsername());
        mailSender.setPassword(properties.getPassword());
        return mailSender;
    }

    private EmailProperties getEmailProperties() {
        final Map<String, String> properties = getPropertiesFromDestinationService();
        return JsonConverter.fromMapToModel(EmailProperties.class, properties);
    }

    private Map<String, String> getPropertiesFromDestinationService() {
        final Destination destination = destinationService.getByName(MAIL_DESTINATION_NAME);
        return destination.getDestinationConfiguration().getProperties();
    }
}
