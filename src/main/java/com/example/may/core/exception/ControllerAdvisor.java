package com.example.may.core.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Evelina Tubalets
 */
@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleNullPointerException() {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("timestamp", LocalDateTime.now());
        responseBody.put("status", 404);
        responseBody.put("message", "Cannot find the element with this id");
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }
}
