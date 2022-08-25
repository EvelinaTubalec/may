package com.example.may.user.model.converter;

import com.example.may.car.model.Car;
import com.example.may.cat.model.Cat;
import com.example.may.cat.repository.CatRepository;
import com.example.may.user.model.User;
import com.example.may.user.model.dto.UserRequestDto;
import com.example.may.user.model.dto.UserResponseDto;
import com.example.may.user.model.dto.UserTransferMoneyResponseDto;
import com.example.may.user.model.dto.UserWithCatsAndCarsResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Evelina Tubalets
 */
@Component
@AllArgsConstructor
public class UserConverter {

    private final CatRepository catRepository;

    public List<User> toModels(final List<UserRequestDto> usersDto) {
        return usersDto.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public User toModel(final UserRequestDto userRequestDto) {
        final String firstName = userRequestDto.getFirstName();
        final String lastName = userRequestDto.getLastName();
        final String email = userRequestDto.getEmail();
        final LocalDate dateOfBirth = userRequestDto.getDateOfBirth();
        final Double coins = userRequestDto.getCoins();
        final List<UUID> catIds = userRequestDto.getCatIds();

        final List<Cat> userCats = catRepository.findByIds(catIds);
        if (userCats.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Incorrect catIds");
        }

        final User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setDateOfBirth(dateOfBirth);
        user.setCoins(coins);
        user.setCats(userCats);
        return user;
    }

    public List<UserResponseDto> toDtos(final List<User> users) {
        return users.stream()
                .map(this::toUserDto)
                .collect(Collectors.toList());
    }

    public UserResponseDto toUserDto(final User user) {
        final UUID id = user.getId();
        final String firstName = user.getFirstName();
        final String lastName = user.getLastName();
        final String email = user.getEmail();
        final LocalDate dateOfBirth = user.getDateOfBirth();
        final Double coins = user.getCoins();

        final UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(id);
        userResponseDto.setFirstName(firstName);
        userResponseDto.setLastName(lastName);
        userResponseDto.setEmail(email);
        userResponseDto.setDateOfBirth(dateOfBirth);
        userResponseDto.setCoins(coins);
        return userResponseDto;
    }

    public UserWithCatsAndCarsResponseDto toUserWithCatAndCarDto(final User user) {
        final UUID id = user.getId();
        final String firstName = user.getFirstName();
        final String lastName = user.getLastName();
        final String email = user.getEmail();
        final LocalDate dateOfBirth = user.getDateOfBirth();
        final Double coins = user.getCoins();
        final List<Cat> cats = user.getCats();
        final List<Car> cars = user.getCars();

        final UserWithCatsAndCarsResponseDto userResponseDto = new UserWithCatsAndCarsResponseDto();
        userResponseDto.setId(id);
        userResponseDto.setFirstName(firstName);
        userResponseDto.setLastName(lastName);
        userResponseDto.setEmail(email);
        userResponseDto.setDateOfBirth(dateOfBirth);
        userResponseDto.setCoins(coins);
        userResponseDto.setCats(cats);
        userResponseDto.setCars(cars);
        return userResponseDto;
    }

    public UserTransferMoneyResponseDto toUserTransferMoneyDto(final User user) {
        final UUID id = user.getId();
        final String firstName = user.getFirstName();
        final String lastName = user.getLastName();
        final Double coins = user.getCoins();

        final UserTransferMoneyResponseDto userResponseDto = new UserTransferMoneyResponseDto();
        userResponseDto.setReceiverId(id);
        userResponseDto.setFirstName(firstName);
        userResponseDto.setLastName(lastName);
        userResponseDto.setCoins(coins);
        return userResponseDto;
    }
}
