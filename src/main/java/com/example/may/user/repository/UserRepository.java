package com.example.may.user.repository;

import com.example.may.car.model.Car;
import com.example.may.cat.model.Cat;
import com.example.may.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * @author Evelina Tubalets
 */
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("select cat from Cat cat inner join cat.users user where user.id in :userId")
    List<Cat> getCatsByUserId(final UUID userId);

    @Query("select car from Car car where car.user.id in :userId")
    List<Car> getCarsByUserId(final UUID userId);
}
