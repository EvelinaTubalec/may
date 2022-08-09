package com.example.may.service;

import com.example.may.model.Cat;
import com.example.may.model.User;
import com.example.may.model.converter.UserConverter;
import com.example.may.model.dto.UserDto;
import com.example.may.repository.CatRepository;
import com.example.may.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author Evelina Tubalets
 */
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final CatRepository catRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User save(final UserDto userDto) {
        final List<UUID> catIds = userDto.getCatIds();
        final List<Cat> userCats = catRepository.findByIds(catIds);

        final User user = userConverter.toModel(userDto);
        user.getCats().addAll(userCats);
        return userRepository.save(user);
    }

    public User saveDefaultUserWithCat() {
        final User user = new User();
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email");
        user.setDateOfBirth(LocalDate.now());
        user.setCoins(0.0);

        final Cat cat = new Cat();
        cat.setAlias("alias");
        cat.setDateOfBirth(LocalDate.now());

        user.getCats().add(cat);
        return userRepository.save(user);
    }

    public User update(final UUID id, final UserDto userDto) {
        final User userFromDb = userRepository.findById(id).orElseThrow(RuntimeException::new);
        userFromDb.setFirstName(userDto.getFirstName());
        userFromDb.setLastName(userDto.getFirstName());
        userFromDb.setEmail(userDto.getEmail());
        userFromDb.setDateOfBirth(userDto.getDateOfBirth());
        userFromDb.setCoins(userDto.getCoins());

        final List<UUID> catIds = userDto.getCatIds();
        final List<Cat> userCats = catRepository.findByIds(catIds);

        final List<Cat> cats = userFromDb.getCats();
        cats.removeAll(cats);
        cats.addAll(userCats);
        return userRepository.save(userFromDb);
    }

    public void deleteById(final UUID ownerId) {
        userRepository.deleteById(ownerId);
    }
}
