package com.example.may.car.model.dto;

import lombok.Data;

import java.util.UUID;

/**
 * @author Evelina Tubalets
 */
@Data
public class CarRequestDto {

    private String manufacturer;

    private String model;

    private Integer maxSpeed;

    private UUID userId;
}
