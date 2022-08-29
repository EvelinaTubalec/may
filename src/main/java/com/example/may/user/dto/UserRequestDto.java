package com.example.may.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author Evelina Tubalets
 */
@Data
public class UserRequestDto {

    private String firstName;

    private String lastName;

    private String email;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;

    private Double coins;

    private List<UUID> catIds;
}
