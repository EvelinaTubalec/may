package com.example.may.car.repository;

import com.example.may.car.model.Car;
import com.example.may.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

/**
 * @author Evelina Tubalets
 */
public interface CarRepository extends JpaRepository<Car, UUID> {

    @Query("select user from User user inner join user.cars car where car.id in :carId")
    User getUserByCarId(final UUID carId);
}
