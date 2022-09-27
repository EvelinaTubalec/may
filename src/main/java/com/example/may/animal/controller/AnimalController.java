package com.example.may.animal.controller;

import com.example.may.animal.model.Animal;
import com.example.may.animal.service.AnimalService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Evelina Tubalets
 */
@RestController
@AllArgsConstructor
@RequestMapping("/animals")
public class AnimalController {

    private final AnimalService animalService;

    @GetMapping
    private List<Animal> getAll() {
        return animalService.getAll();
    }
}
