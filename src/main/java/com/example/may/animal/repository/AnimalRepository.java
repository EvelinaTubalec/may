package com.example.may.animal.repository;

import com.example.may.animal.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author Evelina Tubalets
 */
public interface AnimalRepository extends JpaRepository<Animal, UUID> {
}
