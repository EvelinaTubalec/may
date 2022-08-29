package com.example.may.user.dto;

import lombok.Data;

import java.util.UUID;

/**
 * @author Evelina Tubalets
 */
@Data
public class UserTransferMoneyResponseDto {

    private UUID receiverId;

    private String firstName;

    private String lastName;

    private Double coins;
}
