package com.example.may.car.model.converter;

import com.example.may.car.model.Car;
import com.example.may.car.model.dto.CarRequestDto;
import com.example.may.car.model.dto.CarResponseDto;
import com.example.may.user.model.User;
import com.example.may.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Evelina Tubalets
 */
@Component
@AllArgsConstructor
public class CarConverter {

    public final UserRepository userRepository;

    public List<Car> toModels(final List<CarRequestDto> carsDto) {
        return carsDto.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public Car toModel(final CarRequestDto carRequestDto) {
        final String manufacturer = carRequestDto.getManufacturer();
        final String model = carRequestDto.getModel();
        final Integer maxSpeed = carRequestDto.getMaxSpeed();
        final UUID userId = carRequestDto.getUserId();
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Incorrect userId"));

        final Car car = new Car();
        car.setManufacturer(manufacturer);
        car.setModel(model);
        car.setMaxSpeed(maxSpeed);
        car.setUser(user);
        return car;
    }

    public List<CarResponseDto> toDtos(final List<Car> cars) {
        return cars.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public CarResponseDto toDto(final Car car) {
        final UUID id = car.getId();
        final String manufacturer = car.getManufacturer();
        final String model = car.getModel();
        final Integer maxSpeed = car.getMaxSpeed();

        final CarResponseDto carResponseDto = new CarResponseDto();
        carResponseDto.setId(id);
        carResponseDto.setManufacturer(manufacturer);
        carResponseDto.setModel(model);
        carResponseDto.setMaxSpeed(maxSpeed);
        return carResponseDto;
    }
}
