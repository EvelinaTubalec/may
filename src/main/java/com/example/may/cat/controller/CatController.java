package com.example.may.cat.controller;

import com.example.may.cat.model.Cat;
import com.example.may.cat.model.converter.CatConverter;
import com.example.may.cat.model.dto.CatRequestDto;
import com.example.may.cat.model.dto.CatResponseDto;
import com.example.may.cat.service.CatService;
import com.example.may.user.model.User;
import com.example.may.user.model.converter.UserConverter;
import com.example.may.user.model.dto.UserResponseDto;
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
@RequestMapping("/cats")
@AllArgsConstructor
public class CatController {

    private final CatService catService;
    private final CatConverter catConverter;
    private final UserConverter userConverter;

    @GetMapping
    private List<CatResponseDto> getAll() {
        final List<Cat> cats = catService.getAll();
        return catConverter.toDtos(cats);
    }

    @GetMapping("/{id}/users")
    private List<UserResponseDto> getCatUsers(@PathVariable UUID id) {
        final List<User> catUsers = catService.getCatUsers(id);
        return userConverter.toDtos(catUsers);
    }

    @PostMapping
    private CatResponseDto save(@RequestBody CatRequestDto catRequestDto) {
        final Cat cat = catConverter.toModel(catRequestDto);
        final Cat savedCat = catService.save(cat);
        return catConverter.toDto(savedCat);
    }

    @PostMapping("/batch")
    private List<CatResponseDto> batchInsert(@RequestBody List<CatRequestDto> catRequestDto) {
        final List<Cat> cats = catConverter.toModels(catRequestDto);
        final List<Cat> savedCat = catService.batchInsert(cats);
        return catConverter.toDtos(savedCat);
    }

    @PutMapping("/{id}")
    private CatResponseDto update(@PathVariable UUID id, @RequestBody CatRequestDto catRequestDto) {
        final Cat cat = catConverter.toModel(catRequestDto);
        final Cat updatedCat = catService.update(id, cat);
        return catConverter.toDto(updatedCat);
    }

    @PatchMapping("/{id}")
    private CatResponseDto partialUpdate(@PathVariable UUID id, @RequestBody CatRequestDto catRequestDto) {
        final Cat cat = catConverter.toModel(catRequestDto);
        final Cat updatedCat = catService.partialUpdate(id, cat);
        return catConverter.toDto(updatedCat);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable UUID id) {
        catService.deleteById(id);
    }
}
