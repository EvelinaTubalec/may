package com.example.may.cat.service;

import com.example.may.cat.entity.Cat;
import com.example.may.cat.repository.CatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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

    public List<Cat> batchInsert(final List<Cat> cats) {
        return catRepository.saveAll(cats);
    }

    public Cat update(final UUID id, final Cat cat) {
        final Cat catById = getById(id);
        updateFields(cat, catById);
        return catRepository.save(catById);
    }

    public Cat partialUpdate(final UUID id, final Cat cat) {
        final Cat catById = getById(id);
        return partialUpdateFieldsIfNeeded(cat, catById)
                ? catRepository.save(catById)
                : catById;
    }

    public void deleteById(final UUID catId) {
        catRepository.deleteById(catId);
    }

    private Cat getById(final UUID id) {
        return catRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Incorrect catId"));
    }

    private void updateFields(final Cat cat, final Cat catById) {
        catById.setName(cat.getName());
        catById.setDateOfBirth(cat.getDateOfBirth());
    }

    private boolean partialUpdateFieldsIfNeeded(final Cat cat, final Cat catById) {
        boolean isNeedToUpdate = false;
        if (isNotBlank(cat.getName())) {
            catById.setName(cat.getName());
            isNeedToUpdate = true;
        }
        if (nonNull(cat.getDateOfBirth())) {
            catById.setDateOfBirth(cat.getDateOfBirth());
            isNeedToUpdate = true;
        }
        return isNeedToUpdate;
    }

    public List<Cat> getCats(final UUID id) {
        return catRepository.getCatsByUserId(id);
    }
}
