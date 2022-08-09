package com.example.may.user.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Evelina Tubalets
 */
@Data
public class UserResponseDto {

    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;

    private Double coins;
}
