package com.example.may.model.converter;

import com.example.may.model.Cat;
import com.example.may.model.User;
import com.example.may.model.dto.CatDto;
import com.example.may.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Evelina Tubalets
 */
@Component
@AllArgsConstructor
public class CatConverter {

    public List<Cat> toModels(final List<CatDto> catsDto) {
        return catsDto.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public Cat toModel(final CatDto catDto) {
        final String alias = catDto.getAlias();
        final LocalDate dateOfBirth = catDto.getDateOfBirth();

        final Cat cat = new Cat();
        cat.setAlias(alias);
        cat.setDateOfBirth(dateOfBirth);
        return cat;
    }
}
