package com.example.may.cat.service;

import com.example.may.cat.model.Cat;
import com.example.may.cat.repository.CatRepository;
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

    public List<Cat> getAll() {
        return catRepository.findAll();
    }

    public Cat save(final Cat cat) {
        return catRepository.save(cat);
    }

    public Cat update(final UUID id, final Cat cat) {
        final Cat catFromDb = catRepository.findById(id).orElseThrow(RuntimeException::new);
        updateFields(cat, catFromDb);
        return catRepository.save(catFromDb);
    }

    private void updateFields(final Cat cat, final Cat catFromDb) {
        catFromDb.setAlias(cat.getAlias());
        catFromDb.setDateOfBirth(cat.getDateOfBirth());
    }

    public void deleteById(final UUID catId) {
        catRepository.deleteById(catId);
    }
}
