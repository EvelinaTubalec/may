package com.example.may.dog.repository;

import com.example.may.dog.model.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author Evelina Tubalets
 */
public interface DogRepository extends JpaRepository<Dog, UUID> {
}
