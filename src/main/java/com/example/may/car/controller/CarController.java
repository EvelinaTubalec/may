package com.example.may.car.controller;

import com.example.may.car.model.Car;
import com.example.may.car.model.converter.CarConverter;
import com.example.may.car.model.dto.CarRequestDto;
import com.example.may.car.model.dto.CarResponseDto;
import com.example.may.car.service.CarService;
import com.example.may.user.model.User;
import com.example.may.user.model.converter.UserConverter;
import com.example.may.user.model.dto.UserResponseDto;
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
    private final CarConverter carConverter;
    private final UserConverter userConverter;

    @GetMapping
    private List<CarResponseDto> getAll() {
        final List<Car> cars = carService.getAll();
        return carConverter.toDtos(cars);
    }

    @GetMapping("/{id}/user")
    private UserResponseDto getCarsUser(@PathVariable UUID id) {
        final User carsUser = carService.getCarsUser(id);
        return userConverter.toUserDto(carsUser);
    }

    @PostMapping
    private CarResponseDto save(@RequestBody CarRequestDto carRequestDto) {
        final Car car = carConverter.toModel(carRequestDto);
        final Car savedCar = carService.save(car);
        return carConverter.toDto(savedCar);
    }

    @PutMapping("/{id}")
    private CarResponseDto update(@PathVariable UUID id, @RequestBody CarRequestDto carRequestDto) {
        final Car car = carConverter.toModel(carRequestDto);
        final Car updatedCar = carService.update(id, car);
        return carConverter.toDto(updatedCar);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable UUID id) {
        carService.deleteById(id);
    }
}
