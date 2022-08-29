package com.example.may.cat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Evelina Tubalets
 */
@Data
public class CatResponseDto {

    private UUID id;

    private String name;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;
}
