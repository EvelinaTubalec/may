package com.example.may.controller;

import com.example.may.model.Cat;
import com.example.may.model.converter.CatConverter;
import com.example.may.model.dto.CatDto;
import com.example.may.service.CatService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * @author Evelina Tubalets
 */
@RestController
@AllArgsConstructor
public class CatController {

    private final CatService catService;

    @GetMapping("/cats")
    private List<Cat> getAll() {
        return catService.getAll();
    }

    @PostMapping("/cats")
    private Cat save(@RequestBody CatDto catDto) {
        return catService.save(catDto);
    }

    @PutMapping("/cats")
    private Cat update(@RequestParam UUID id, @RequestBody CatDto catDto) {
        return catService.update(id, catDto);
    }

    @DeleteMapping ("/cats")
    private void delete(@RequestParam UUID id) {
        catService.deleteById(id);
    }
}
