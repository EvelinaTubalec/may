package com.example.may.dog.service;

import com.example.may.dog.model.Dog;
import com.example.may.dog.repository.DogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Evelina Tubalets
 */
@Service
@AllArgsConstructor
public class DogService {

    private final DogRepository dogRepository;

    public List<Dog> getAll() {
        return dogRepository.findAll();
    }

    public Dog save(final Dog dog) {
        return dogRepository.save(dog);
    }
}
