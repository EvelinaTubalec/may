package com.example.may.repository;

import com.example.may.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Evelina Tubalets
 */
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select user from User user where user.id=:userId")
    Optional<User> findById(final UUID userId);

    @Modifying
    @Transactional
    @Query("delete from User user where user.id=:userId")
    void deleteById(final UUID userId);
}
