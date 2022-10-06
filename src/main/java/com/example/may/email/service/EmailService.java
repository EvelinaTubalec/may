package com.example.may.email.service;

import com.example.may.cloudfoundry.destination.model.Destination;
import com.example.may.cloudfoundry.destination.service.DestinationService;
import com.example.may.core.web.converter.JsonConverter;
import com.example.may.email.config.EmailProperties;
import com.example.may.user.entity.User;
import com.example.may.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.LocalDate.now;

/**
 * @author Evelina Tubalets
 */
@Service
@AllArgsConstructor
public class EmailService {

    public static final String MAIL_DESTINATION_NAME = "mail-destination";

    private final UserRepository userRepository;
    private final DestinationService destinationService;

    public List<User> getBirthdayUserEmails() {
        return userRepository.findAll()
                .stream()
                .filter(this::isBirthdayToday)
                .collect(Collectors.toList());
    }

    public EmailProperties getEmailProperties() {
        final Map<String, String> properties = getPropertiesFromDestinationService();
        return JsonConverter.fromMapToModel(EmailProperties.class, properties);
    }

    private boolean isBirthdayToday(final User user) {
        final int presentDay = now().getDayOfMonth();
        final int presentMonth = now().getMonthValue();
        return user.getDateOfBirth().getDayOfMonth() == presentDay
                && user.getDateOfBirth().getMonthValue() == presentMonth;
    }

    private Map<String, String> getPropertiesFromDestinationService() {
        final Destination destination = destinationService.getByName(MAIL_DESTINATION_NAME);
        return destination.getDestinationConfiguration().getProperties();
    }
}
