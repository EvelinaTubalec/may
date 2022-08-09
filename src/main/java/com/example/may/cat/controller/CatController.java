package com.example.may.cat.controller;

import com.example.may.cat.model.Cat;
import com.example.may.cat.model.converter.CatConverter;
import com.example.may.cat.model.dto.CatRequestDto;
import com.example.may.cat.model.dto.CatResponseDto;
import com.example.may.cat.service.CatService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping()
    private List<Cat> getAll() {
        return catService.getAll();
    }

    @PostMapping()
    private CatResponseDto save(@RequestBody CatRequestDto catRequestDto) {
        final Cat cat = catConverter.toModel(catRequestDto);
        final Cat savedCat = catService.save(cat);
        return catConverter.toDto(savedCat);
    }

    @PutMapping("/{id}")
    private CatResponseDto update(@PathVariable UUID id, @RequestBody CatRequestDto catRequestDto) {
        final Cat cat = catConverter.toModel(catRequestDto);
        final Cat updatedCat = catService.update(id, cat);
        return catConverter.toDto(updatedCat);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable UUID id) {
        catService.deleteById(id);
    }
}
