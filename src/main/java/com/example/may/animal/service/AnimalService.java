package com.example.may.animal.service;

import com.example.may.animal.model.Animal;
import com.example.may.animal.repository.AnimalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Evelina Tubalets
 */
@Service
@AllArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;

    public List<Animal> getAll() {
        return animalRepository.findAll();
    }
}
