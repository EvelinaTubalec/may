package com.example.may.cat.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Evelina Tubalets
 */
@Data
public class CatRequestDto {

    private String alias;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;
}
