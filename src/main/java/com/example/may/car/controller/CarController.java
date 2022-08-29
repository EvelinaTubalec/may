package com.example.may.car.controller;

import com.example.may.car.converter.CarConverter;
import com.example.may.car.dto.CarRequestDto;
import com.example.may.car.dto.CarResponseDto;
import com.example.may.car.entity.Car;
import com.example.may.car.service.CarService;
import com.example.may.user.converter.UserConverter;
import com.example.may.user.dto.UserResponseDto;
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
@RequestMapping("/cars")
@AllArgsConstructor
public class CarController {

    private final CarService carService;
    private final UserService userService;
    private final CarConverter carConverter;
    private final UserConverter userConverter;

    @GetMapping
    private List<CarResponseDto> getAll() {
        final List<Car> cars = carService.getAll();
        return carConverter.toDtos(cars);
    }

    @GetMapping("/{id}/user")
    private UserResponseDto getCarsUser(@PathVariable final UUID id) {
        final User carsUser = userService.getUserByCarId(id);
        return userConverter.toUserDto(carsUser);
    }

    @PostMapping
    private CarResponseDto save(@RequestBody final CarRequestDto carRequestDto) {
        final Car car = carConverter.toModel(carRequestDto);
        final Car savedCar = carService.save(car);
        return carConverter.toDto(savedCar);
    }

    @PutMapping("/{id}")
    private CarResponseDto update(@PathVariable final UUID id, @RequestBody final CarRequestDto carRequestDto) {
        final Car car = carConverter.toModel(carRequestDto);
        final Car updatedCar = carService.update(id, car);
        return carConverter.toDto(updatedCar);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable final UUID id) {
        carService.deleteById(id);
    }
}
