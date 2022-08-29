package com.example.may.car.service;

import com.example.may.car.entity.Car;
import com.example.may.car.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * @author Evelina Tubalets
 */
@Service
@AllArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public List<Car> getAll() {
        return carRepository.findAll();
    }

    public Car save(final Car car) {
        return carRepository.save(car);
    }

    public Car update(final UUID id, final Car car) {
        final Car carById = getById(id);
        updateFields(car, carById);
        return carRepository.save(carById);
    }

    public void deleteById(final UUID carId) {
        carRepository.deleteById(carId);
    }

    private Car getById(final UUID id) {
        return carRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Incorrect carId"));
    }

    private void updateFields(final Car car, final Car carFromDb) {
        carFromDb.setManufacturer(car.getManufacturer());
        carFromDb.setModel(car.getModel());
        carFromDb.setMaxSpeed(car.getMaxSpeed());
        carFromDb.setUser(car.getUser());
    }

    public List<Car> getCars(final UUID id) {
        return carRepository.getCarsByUserId(id);
    }
}
