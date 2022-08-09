package com.example.may.controller;

import com.example.may.model.Cat;
import com.example.may.model.User;
import com.example.may.model.converter.UserConverter;
import com.example.may.model.dto.CatDto;
import com.example.may.model.dto.UserDto;
import com.example.may.service.UserService;
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
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    private List<User> getAll() {
        return userService.getAll();
    }

    @PostMapping("/users")
    private User save(@RequestBody UserDto userDto) { return userService.save(userDto);}

    @PostMapping("/users/default")
    private User saveDefaultUserWithCat() {
        return userService.saveDefaultUserWithCat();
    }

    @PutMapping("/users")
    private User update(@RequestParam UUID id, @RequestBody UserDto userDto) {
        return userService.update(id, userDto);
    }

    @DeleteMapping("/users")
    private void delete(@RequestParam UUID id){
        userService.deleteById(id);
    }
}
