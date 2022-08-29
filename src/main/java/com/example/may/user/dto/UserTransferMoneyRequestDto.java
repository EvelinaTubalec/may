package com.example.may.user.dto;

import lombok.Data;

import java.util.UUID;

/**
 * @author Evelina Tubalets
 */
@Data
public class UserTransferMoneyRequestDto {

    private Double coins;

    private UUID receiverId;

    private UUID senderId;
}
