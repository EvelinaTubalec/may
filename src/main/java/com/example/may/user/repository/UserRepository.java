package com.example.may.user.repository;

import com.example.may.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * @author Evelina Tubalets
 */
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("select user from User user inner join user.cats cat where cat.id in :catId")
    List<User> getUsersByCatId(final UUID catId);

    @Query("select user from User user inner join user.cars car where car.id in :carId")
    User getUserByCarId(final UUID carId);
}
