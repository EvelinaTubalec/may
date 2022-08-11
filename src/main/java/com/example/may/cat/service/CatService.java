package com.example.may.cat.service;

import com.example.may.cat.model.Cat;
import com.example.may.cat.repository.CatRepository;
import com.example.may.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

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

    public List<User> getCatUsers(final UUID id) {
        return catRepository.getUsersByCatId(id);
    }

    public Cat save(final Cat cat) {
        return catRepository.save(cat);
    }

    public List<Cat> batchInsert(final List<Cat> cats) {
        return catRepository.saveAll(cats);
    }

    public Cat update(final UUID id, final Cat cat) {
        final Cat catFromDb = getCatFromDb(id);
        updateFields(cat, catFromDb);
        return catRepository.save(catFromDb);
    }

    public Cat partialUpdate(final UUID id, final Cat cat) {
        final Cat catFromDb = getCatFromDb(id);
        return partialUpdateFieldsIfNeeded(cat, catFromDb)
                ? catRepository.save(catFromDb)
                : catFromDb;
    }

    public void deleteById(final UUID catId) {
        catRepository.deleteById(catId);
    }

    private Cat getCatFromDb(final UUID id) {
        return catRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Incorrect catId"));
    }

    private void updateFields(final Cat cat, final Cat catFromDb) {
        catFromDb.setName(cat.getName());
        catFromDb.setDateOfBirth(cat.getDateOfBirth());
    }

    private boolean partialUpdateFieldsIfNeeded(final Cat cat, final Cat catFromDb) {
        boolean isNeedToUpdate = false;
        if (isNotBlank(cat.getName())) {
            catFromDb.setName(cat.getName());
            isNeedToUpdate = true;
        }
        if (nonNull(cat.getDateOfBirth())) {
            catFromDb.setDateOfBirth(cat.getDateOfBirth());
            isNeedToUpdate = true;
        }
        return isNeedToUpdate;
    }
}
