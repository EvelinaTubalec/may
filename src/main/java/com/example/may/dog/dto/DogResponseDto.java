package com.example.may.dog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Evelina Tubalets
 */
@Data
public class DogResponseDto {

    private UUID id;

    private String name;

    private String breed;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;
}
