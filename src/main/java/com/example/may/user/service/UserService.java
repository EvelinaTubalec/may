package com.example.may.user.service;

import com.example.may.car.entity.Car;
import com.example.may.cat.entity.Cat;
import com.example.may.user.entity.User;
import com.example.may.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;

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

    @Transactional
    public User findById(final UUID id) {
        // return user with cats and cars
        final User user = getById(id);
        Hibernate.initialize(user.getCats());
        Hibernate.initialize(user.getCars());
        return user;
    }

    public User getUserByCarId(final UUID id) {
        return userRepository.getUserByCarId(id);
    }

    public List<User> getUsersByCatId(final UUID id) {
        return userRepository.getUsersByCatId(id);
    }

    public User save(final User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User update(final UUID id, final User user) {
        final User userById = getById(id);
        updateFields(user, userById);
        return userRepository.save(userById);
    }

    public void deleteById(final UUID userId) {
        userRepository.deleteById(userId);
    }

    @Transactional(isolation = SERIALIZABLE)
    public User transferMoney(final UUID senderId, final UUID receiverId, final Double coins) {
        final User sender = getById(senderId);
        final User receiver = getById(receiverId);

        updateCoinsForSender(sender, coins);
        updateCoinsForReceiver(receiver, coins);
        return receiver;
    }

    private User getById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Incorrect userId"));
    }

    private void updateFields(final User user, final User existingUser) {
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getFirstName());
        existingUser.setEmail(user.getEmail());
        existingUser.setDateOfBirth(user.getDateOfBirth());
        existingUser.setCoins(user.getCoins());
        updateCats(user, existingUser);
        updateCars(user, existingUser);
    }

    private void updateCats(final User user, final User existingUser) {
        final List<Cat> newCats = user.getCats();
        existingUser.setCats(newCats);
    }

    private void updateCars(final User user, final User existingUser) {
        final List<Car> newCars = user.getCars();
        existingUser.setCars(newCars);
    }

    private void updateCoinsForSender(final User sender, final Double coins) {
        final double newCoinsForSender = sender.getCoins() - coins;
        validateTransferOperation(newCoinsForSender);
        sender.setCoins(newCoinsForSender);
        userRepository.save(sender);
    }

    private void validateTransferOperation(double newCoinsForSender) {
        if (newCoinsForSender < 0) {
            throw new ResponseStatusException(BAD_REQUEST, "Can't transfer, not enough money");
        }
    }

    private void updateCoinsForReceiver(final User receiver, final Double coins) {
        final double newCoinsForReceiver = receiver.getCoins() + coins;
        receiver.setCoins(newCoinsForReceiver);
        userRepository.save(receiver);
    }
}
