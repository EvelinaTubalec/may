package com.example.may.user.controller;

import com.example.may.car.converter.CarConverter;
import com.example.may.car.dto.CarResponseDto;
import com.example.may.car.entity.Car;
import com.example.may.car.service.CarService;
import com.example.may.cat.converter.CatConverter;
import com.example.may.cat.dto.CatResponseDto;
import com.example.may.cat.entity.Cat;
import com.example.may.cat.service.CatService;
import com.example.may.user.converter.UserConverter;
import com.example.may.user.dto.UserRequestDto;
import com.example.may.user.dto.UserResponseDto;
import com.example.may.user.dto.UserTransferMoneyRequestDto;
import com.example.may.user.dto.UserTransferMoneyResponseDto;
import com.example.may.user.dto.UserWithCatsAndCarsResponseDto;
import com.example.may.user.entity.User;
import com.example.may.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * @author Evelina Tubalets
 */
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final CarService carService;
    private final CatService catService;
    private final UserConverter userConverter;
    private final CatConverter catConverter;
    private final CarConverter carConverter;

    @GetMapping
    private List<UserResponseDto> getAll() {
        final List<User> users = userService.getAll();
        return userConverter.toDtos(users);
    }

    @GetMapping("/{id}")
    private UserWithCatsAndCarsResponseDto findById(@PathVariable final UUID id) {
        final User userById = userService.findById(id);
        return userConverter.toUserWithCatAndCarDto(userById);
    }

    @GetMapping("/{id}/cats")
    private List<CatResponseDto> getCats(@PathVariable final UUID id) {
        final List<Cat> userCats = catService.getCats(id);
        return catConverter.toDtos(userCats);
    }

    @GetMapping("/{id}/cars")
    private List<CarResponseDto> getCars(@PathVariable final UUID id) {
        final List<Car> userCars = carService.getCars(id);
        return carConverter.toDtos(userCars);
    }

    @PostMapping
    private UserResponseDto save(@RequestBody final UserRequestDto userRequestDto) {
        final User user = userConverter.toModel(userRequestDto);
        final User savedUser = userService.save(user);
        return userConverter.toUserDto(savedUser);
    }

    @PutMapping("/{id}")
    private UserResponseDto update(@PathVariable final UUID id, @RequestBody final UserRequestDto userRequestDto) {
        final User user = userConverter.toModel(userRequestDto);
        final User updatedUser = userService.update(id, user);
        return userConverter.toUserDto(updatedUser);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable final UUID id) {
        userService.deleteById(id);
    }

    @PostMapping("/actions/transfer-money")
    private UserTransferMoneyResponseDto transferMoney(@RequestBody final UserTransferMoneyRequestDto requestDto) {
        final UUID senderId = requestDto.getSenderId();
        final UUID receiverId = requestDto.getReceiverId();
        final Double coins = requestDto.getCoins();
        final User users = userService.transferMoney(senderId, receiverId, coins);
        return userConverter.toUserTransferMoneyDto(users);
    }
}
