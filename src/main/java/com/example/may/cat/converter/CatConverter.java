package com.example.may.cat.converter;

import com.example.may.cat.dto.CatRequestDto;
import com.example.may.cat.dto.CatResponseDto;
import com.example.may.cat.entity.Cat;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Evelina Tubalets
 */
@Component
public class CatConverter {

    public List<Cat> toModels(final List<CatRequestDto> catsDto) {
        return catsDto.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public Cat toModel(final CatRequestDto catRequestDto) {
        final String name = catRequestDto.getName();
        final LocalDate dateOfBirth = catRequestDto.getDateOfBirth();

        final Cat cat = new Cat();
        cat.setName(name);
        cat.setDateOfBirth(dateOfBirth);
        return cat;
    }

    public List<CatResponseDto> toDtos(final List<Cat> cats) {
        return cats.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public CatResponseDto toDto(final Cat cat) {
        final UUID id = cat.getId();
        final String name = cat.getName();
        final LocalDate dateOfBirth = cat.getDateOfBirth();

        final CatResponseDto catResponseDto = new CatResponseDto();
        catResponseDto.setId(id);
        catResponseDto.setName(name);
        catResponseDto.setDateOfBirth(dateOfBirth);
        return catResponseDto;
    }
}
