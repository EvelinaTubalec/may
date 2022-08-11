package com.example.may.core.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Evelina Tubalets
 */
@Data
@Builder
public class ExceptionResponse {

    private LocalDateTime timestamp;

    private Integer status;

    private String message;
}
