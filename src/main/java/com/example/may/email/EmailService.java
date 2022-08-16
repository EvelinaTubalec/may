package com.example.may.email;

import com.example.may.core.config.EmailProperties;
import com.example.may.user.model.User;
import com.example.may.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDate.now;

/**
 * @author Evelina Tubalets
 */
@Service
@AllArgsConstructor
public class EmailService {

    private final static String emailFrom = "645652fb23-e8813b@inbox.mailtrap.io";
    @Value("${spring.mail.host}")
    private final String host;

    private UserRepository userRepository;
    private EmailProperties properties;

    public void sendCongratulationEmail() {
        final List<User> birthdayUsers = getBirthdayUserEmails();
        birthdayUsers.forEach(this::createCongratulationMessageAndSend);
    }

    private List<User> getBirthdayUserEmails() {
        final int presentDay = now().getDayOfMonth();
        final int presentMonth = now().getMonthValue();
        return userRepository.findAll()
                .stream()
                .filter(it -> it.getDateOfBirth().getDayOfMonth() == presentDay
                        && it.getDateOfBirth().getMonthValue() == presentMonth)
                .collect(Collectors.toList());
    }

    private void createCongratulationMessageAndSend(final User user) {
        final SimpleMailMessage message = createCongratulationMessage(user);
        getJavaMailSender().send(message);
    }

    private SimpleMailMessage createCongratulationMessage(final User user) {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Congratulation Message");
        message.setText(user.getFirstName() + " " + user.getLastName() + ", Happy Birthday!");
        message.setFrom(emailFrom);
        return message;
    }

    private JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
        mailSenderImpl.setHost(host);
        mailSenderImpl.setPort(properties.getPort());
        mailSenderImpl.setUsername(properties.getUsername());
        mailSenderImpl.setPassword(properties.getPassword());
        return mailSenderImpl;
    }
}
