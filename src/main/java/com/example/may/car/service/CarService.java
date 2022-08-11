package com.example.may.car.service;

import com.example.may.car.model.Car;
import com.example.may.car.repository.CarRepository;
import com.example.may.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

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

    public User getCarsUser(final UUID id) {
        return carRepository.getUserByCarId(id);
    }

    public Car save(final Car car) {
        return carRepository.save(car);
    }

    public Car update(final UUID id, final Car car) {
        final Car carFromDb = carRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Incorrect carId"));
        updateFields(car, carFromDb);
        return carRepository.save(carFromDb);
    }

    public void deleteById(final UUID carId) {
        carRepository.deleteById(carId);
    }

    private void updateFields(final Car car, final Car carFromDb) {
        carFromDb.setManufacturer(car.getManufacturer());
        carFromDb.setModel(car.getModel());
        carFromDb.setMaxSpeed(car.getMaxSpeed());
        carFromDb.setUser(car.getUser());
    }
}
