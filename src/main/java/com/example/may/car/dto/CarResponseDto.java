package com.example.may.car.dto;

import lombok.Data;

import java.util.UUID;

/**
 * @author Evelina Tubalets
 */
@Data
public class CarResponseDto {

    private UUID id;

    private String manufacturer;

    private String model;

    private Integer maxSpeed;
}
