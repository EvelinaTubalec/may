package com.example.may.cat.model.converter;

import com.example.may.cat.model.Cat;
import com.example.may.cat.model.dto.CatRequestDto;
import com.example.may.cat.model.dto.CatResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Evelina Tubalets
 */
@Component
@AllArgsConstructor
public class CatConverter {

    public List<Cat> toModels(final List<CatRequestDto> catsDto) {
        return catsDto.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public Cat toModel(final CatRequestDto catRequestDto) {
        final String alias = catRequestDto.getAlias();
        final LocalDate dateOfBirth = catRequestDto.getDateOfBirth();

        final Cat cat = new Cat();
        cat.setAlias(alias);
        cat.setDateOfBirth(dateOfBirth);
        return cat;
    }

    public CatResponseDto toDto(final Cat cat) {
        final UUID id = cat.getId();
        final String alias = cat.getAlias();
        final LocalDate dateOfBirth = cat.getDateOfBirth();

        final CatResponseDto catResponseDto = new CatResponseDto();
        catResponseDto.setId(id);
        catResponseDto.setAlias(alias);
        catResponseDto.setDateOfBirth(dateOfBirth);
        return catResponseDto;
    }
}
