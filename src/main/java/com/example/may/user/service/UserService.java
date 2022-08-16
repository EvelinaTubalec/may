package com.example.may.user.service;

import com.example.may.car.model.Car;
import com.example.may.cat.model.Cat;
import com.example.may.user.model.User;
import com.example.may.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

/**
 * @author Evelina Tubalets
 */
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public List<Cat> getUserCats(final UUID id) {
        return userRepository.getCatsByUserId(id);
    }

    public List<Car> getUserCars(final UUID id) {
        return userRepository.getUserCars(id);
    }

    public User save(final User user) {
        return userRepository.save(user);
    }

    public User update(final UUID id, final User user) {
        final User userFromDb = getUserFromDb(id);
        updateFields(user, userFromDb);
        return userRepository.save(userFromDb);
    }

    public void deleteById(final UUID userId) {
        userRepository.deleteById(userId);
    }

    public User getUserFromDb(final UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Incorrect userId"));
    }

    private void updateFields(final User user, final User userFromDb) {
        userFromDb.setFirstName(user.getFirstName());
        userFromDb.setLastName(user.getFirstName());
        userFromDb.setEmail(user.getEmail());
        userFromDb.setDateOfBirth(user.getDateOfBirth());
        userFromDb.setCoins(user.getCoins());
        updateCats(user, userFromDb);
    }

    private void updateCats(final User user, final User userFromDb) {
        final List<Cat> newCats = user.getCats();
        final List<Cat> cats = userFromDb.getCats();
        cats.removeAll(cats);
        cats.addAll(newCats);
    }
}
