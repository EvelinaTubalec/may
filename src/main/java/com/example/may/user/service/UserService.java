package com.example.may.user.service;

import com.example.may.car.model.Car;
import com.example.may.cat.model.Cat;
import com.example.may.user.model.User;
import com.example.may.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        return userRepository.getCarsByUserId(id);
    }

    @Transactional
    public User findById(final UUID id) {
        // return user with cats and cars
        final User user = getUserFromDb(id);
        Hibernate.initialize(user.getCats());
        Hibernate.initialize(user.getCars());
        return user;
    }

    public User save(final User user) {
        final User save = userRepository.save(user);
        return save;
    }

    @Transactional
    public User update(final UUID id, final User user) {
        final User userFromDb = getUserFromDb(id);
        updateFields(user, userFromDb);
        return userRepository.save(userFromDb);
    }

    public void deleteById(final UUID userId) {
        userRepository.deleteById(userId);
    }

    private User getUserFromDb(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Incorrect userId"));
    }

    public User transferMoney(final UUID translatorId, final UUID receiverId, final Double coins) {
        final User translator = getUserFromDb(translatorId);
        final User receiver = getUserFromDb(receiverId);

        updateCoinsForTranslator(translator, coins);
        updateCoinsForReceiver(receiver, coins);
        return receiver;
    }

    private void updateFields(final User user, final User userFromDb) {
        userFromDb.setFirstName(user.getFirstName());
        userFromDb.setLastName(user.getFirstName());
        userFromDb.setEmail(user.getEmail());
        userFromDb.setDateOfBirth(user.getDateOfBirth());
        userFromDb.setCoins(user.getCoins());
        updateCats(user, userFromDb);
        updateCars(user, userFromDb);
    }

    private void updateCats(final User user, final User userFromDb) {
        final List<Cat> newCats = user.getCats();
        final List<Cat> cats = userFromDb.getCats();
        cats.removeAll(cats);
        cats.addAll(newCats);
    }

    private void updateCars(final User user, final User userFromDb) {
        final List<Car> newCars = user.getCars();
        final List<Car> cars = userFromDb.getCars();
        cars.removeAll(cars);
        cars.addAll(newCars);
    }

    private void updateCoinsForTranslator(final User translator, final Double coins) {
        final double newCoinsForTranslator = translator.getCoins() - coins;
        if (newCoinsForTranslator < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't transfer, not enough money");
        }
        translator.setCoins(newCoinsForTranslator);
        userRepository.save(translator);
    }

    private void updateCoinsForReceiver(final User receiver, final Double coins) {
        final double newCoinsForReceiver = receiver.getCoins() + coins;
        receiver.setCoins(newCoinsForReceiver);
        userRepository.save(receiver);
    }
}
