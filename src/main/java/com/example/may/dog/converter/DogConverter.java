package com.example.may.dog.converter;

import com.example.may.dog.dto.DogRequestDto;
import com.example.may.dog.dto.DogResponseDto;
import com.example.may.dog.model.Dog;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Evelina Tubalets
 */
@Component
public class DogConverter {

    public List<Dog> toModels(final List<DogRequestDto> dogsDto) {
        return dogsDto.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public Dog toModel(final DogRequestDto dogRequestDto) {
        final String name = dogRequestDto.getName();
        final String breed = dogRequestDto.getBreed();
        final LocalDate dateOfBirth = dogRequestDto.getDateOfBirth();

        final Dog dog = new Dog();
        dog.setName(name);
        dog.setBreed(breed);
        dog.setDateOfBirth(dateOfBirth);
        return dog;
    }

    public List<DogResponseDto> toDtos(final List<Dog> dogs) {
        return dogs.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public DogResponseDto toDto(final Dog dog) {
        final UUID id = dog.getId();
        final String name = dog.getName();
        final String breed = dog.getBreed();
        final LocalDate dateOfBirth = dog.getDateOfBirth();

        final DogResponseDto dogResponseDto = new DogResponseDto();
        dogResponseDto.setId(id);
        dogResponseDto.setName(name);
        dogResponseDto.setBreed(breed);
        dogResponseDto.setDateOfBirth(dateOfBirth);
        return dogResponseDto;
    }
}
