package com.example.may.user.converter;

import com.example.may.car.converter.CarConverter;
import com.example.may.car.dto.CarResponseDto;
import com.example.may.car.entity.Car;
import com.example.may.cat.converter.CatConverter;
import com.example.may.cat.dto.CatResponseDto;
import com.example.may.cat.entity.Cat;
import com.example.may.cat.repository.CatRepository;
import com.example.may.user.dto.UserRequestDto;
import com.example.may.user.dto.UserResponseDto;
import com.example.may.user.dto.UserTransferMoneyResponseDto;
import com.example.may.user.dto.UserWithCatsAndCarsResponseDto;
import com.example.may.user.entity.User;
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
    private final CatConverter catConverter;
    private final CarConverter carConverter;

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

        final List<CatResponseDto> catResponseDtoList = catConverter.toDtos(cats);
        final List<CarResponseDto> carResponseDtoList = carConverter.toDtos(cars);

        final UserWithCatsAndCarsResponseDto userResponseDto = new UserWithCatsAndCarsResponseDto();
        userResponseDto.setId(id);
        userResponseDto.setFirstName(firstName);
        userResponseDto.setLastName(lastName);
        userResponseDto.setEmail(email);
        userResponseDto.setDateOfBirth(dateOfBirth);
        userResponseDto.setCoins(coins);
        userResponseDto.setCats(catResponseDtoList);
        userResponseDto.setCars(carResponseDtoList);
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
