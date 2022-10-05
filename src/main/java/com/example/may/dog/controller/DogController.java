package com.example.may.dog.controller;

import com.example.may.dog.converter.DogConverter;
import com.example.may.dog.dto.DogRequestDto;
import com.example.may.dog.dto.DogResponseDto;
import com.example.may.dog.model.Dog;
import com.example.may.dog.service.DogService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Evelina Tubalets
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dogs")
public class DogController {

    private final DogService dogService;
    private final DogConverter dogConverter;

    @GetMapping
    private List<DogResponseDto> getAll() {
        final List<Dog> dogs = dogService.getAll();
        return dogConverter.toDtos(dogs);
    }

    @PostMapping
    private DogResponseDto save(@RequestBody final DogRequestDto dogRequestDto) {
        final Dog dog = dogConverter.toModel(dogRequestDto);
        final Dog savedDog = dogService.save(dog);
        return dogConverter.toDto(savedDog);
    }
}
