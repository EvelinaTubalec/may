package com.example.may.model.converter;

import com.example.may.model.User;
import com.example.may.model.dto.UserDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Evelina Tubalets
 */
@Component
public class UserConverter {

    public List<User> toModels(final List<UserDto> usersDto) {
        return usersDto.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public User toModel(final UserDto userDto) {
        final String firstName = userDto.getFirstName();
        final String lastName = userDto.getLastName();
        final String email = userDto.getEmail();
        final LocalDate dateOfBirth = userDto.getDateOfBirth();
        final Double coins = userDto.getCoins();

        final User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setDateOfBirth(dateOfBirth);
        user.setCoins(coins);
        return user;
    }
}
