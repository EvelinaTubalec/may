package com.example.may.user.repository;

import com.example.may.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author Evelina Tubalets
 */
public interface UserRepository extends JpaRepository<User, UUID> {
}
