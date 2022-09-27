package com.example.may.cat.controller;

import com.example.may.cat.converter.CatConverter;
import com.example.may.cat.dto.CatRequestDto;
import com.example.may.cat.dto.CatResponseDto;
import com.example.may.cat.entity.Cat;
import com.example.may.cat.service.CatService;
import com.example.may.user.converter.UserConverter;
import com.example.may.user.dto.UserResponseDto;
import com.example.may.user.entity.User;
import com.example.may.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * @author Evelina Tubalets
 */
@RestController
@AllArgsConstructor
@RequestMapping("/cats")
public class CatController {

    private final CatService catService;
    private final UserService userService;
    private final CatConverter catConverter;
    private final UserConverter userConverter;

    @GetMapping
    private List<CatResponseDto> getAll() {
        final List<Cat> cats = catService.getAll();
        return catConverter.toDtos(cats);
    }

    @GetMapping("/{id}/users")
    private List<UserResponseDto> getUsers(@PathVariable final UUID id) {
        final List<User> catUsers = userService.getUsersByCatId(id);
        return userConverter.toDtos(catUsers);
    }

    @PostMapping
    private CatResponseDto save(@RequestBody final CatRequestDto catRequestDto) {
        final Cat cat = catConverter.toModel(catRequestDto);
        final Cat savedCat = catService.save(cat);
        return catConverter.toDto(savedCat);
    }

    @PostMapping("/batch")
    private List<CatResponseDto> batchInsert(@RequestBody final List<CatRequestDto> catRequestDto) {
        final List<Cat> cats = catConverter.toModels(catRequestDto);
        final List<Cat> savedCat = catService.batchInsert(cats);
        return catConverter.toDtos(savedCat);
    }

    @PutMapping("/{id}")
    private CatResponseDto update(@PathVariable final UUID id, @RequestBody final CatRequestDto catRequestDto) {
        final Cat cat = catConverter.toModel(catRequestDto);
        final Cat updatedCat = catService.update(id, cat);
        return catConverter.toDto(updatedCat);
    }

    @PatchMapping("/{id}")
    private CatResponseDto partialUpdate(@PathVariable final UUID id, @RequestBody final CatRequestDto catRequestDto) {
        final Cat cat = catConverter.toModel(catRequestDto);
        final Cat updatedCat = catService.partialUpdate(id, cat);
        return catConverter.toDto(updatedCat);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable final UUID id) {
        catService.deleteById(id);
    }
}
