package com.example.may.user.controller;

import com.example.may.user.model.User;
import com.example.may.user.model.converter.UserConverter;
import com.example.may.user.model.dto.UserRequestDto;
import com.example.may.user.model.dto.UserResponseDto;
import com.example.may.user.service.UserService;
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
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserConverter userConverter;

    @GetMapping()
    private List<User> getAll() {
        return userService.getAll();
    }

    @PostMapping()
    private UserResponseDto save(@RequestBody UserRequestDto userRequestDto) {
        final User user = userConverter.toModel(userRequestDto);
        final User savedUser = userService.save(user);
        return userConverter.toDto(savedUser);
    }

    @PutMapping("/{id}")
    private UserResponseDto update(@PathVariable UUID id, @RequestBody UserRequestDto userRequestDto) {
        final User user = userConverter.toModel(userRequestDto);
        final User updatedUser = userService.update(id, user);
        return userConverter.toDto(updatedUser);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable UUID id){
        userService.deleteById(id);
    }
}
