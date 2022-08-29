package com.example.may.car.repository;

import com.example.may.car.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * @author Evelina Tubalets
 */
public interface CarRepository extends JpaRepository<Car, UUID> {

    @Query("select car from Car car where car.user.id in :userId")
    List<Car> getCarsByUserId(final UUID userId);
}
