package com.example.may.core.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * @author Evelina Tubalets
 */
@Data
@Builder
public class ExceptionResponse {

    private LocalDateTime timestamp;

    private HttpStatus status;

    private String message;
}
