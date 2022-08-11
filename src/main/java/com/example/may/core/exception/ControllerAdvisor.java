package com.example.may.core.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

/**
 * @author Evelina Tubalets
 */
@ControllerAdvice
public class ControllerAdvisor {

    public static final int BAD_REQUEST_STATUS = 400;
    public static final int NOT_FOUND_STATUS = 404;

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ExceptionResponse> handleNullPointerException() {
        final ExceptionResponse response = ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(BAD_REQUEST_STATUS)
                .message("Cannot find the element with this id")
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Object handleFileNotFoundException() {
        return ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(NOT_FOUND_STATUS)
                .message("Cannot find file")
                .build();
    }
}
