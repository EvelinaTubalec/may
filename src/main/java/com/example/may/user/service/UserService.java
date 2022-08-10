package com.example.may.user.service;

import com.example.may.car.model.Car;
import com.example.may.cat.model.Cat;
import com.example.may.user.model.User;
import com.example.may.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
        final User userFromDb = userRepository.findById(id).orElseThrow(RuntimeException::new);
        updateFields(user, userFromDb);
        return userRepository.save(userFromDb);
    }

    public void deleteById(final UUID ownerId) {
        userRepository.deleteById(ownerId);
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
