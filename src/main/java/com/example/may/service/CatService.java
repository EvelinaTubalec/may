package com.example.may.service;

import com.example.may.model.Cat;
import com.example.may.model.converter.CatConverter;
import com.example.may.model.dto.CatDto;
import com.example.may.repository.CatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Evelina Tubalets
 */
@Service
@AllArgsConstructor
public class CatService {

    private final CatRepository catRepository;
    private final CatConverter catConverter;

    public List<Cat> getAll() {
        return catRepository.findAll();
    }

    public Cat save(final CatDto catDto) {
        final Cat cat = catConverter.toModel(catDto);
        return catRepository.save(cat);
    }

    public Cat update(final UUID id, final CatDto catDto) {
        final Cat catFromDb = catRepository.findById(id).orElseThrow(RuntimeException::new);
        catFromDb.setAlias(catDto.getAlias());
        catFromDb.setDateOfBirth(catDto.getDateOfBirth());
        return catRepository.save(catFromDb);
    }

    public void deleteById(final UUID catId) {
        catRepository.deleteById(catId);
    }
}
