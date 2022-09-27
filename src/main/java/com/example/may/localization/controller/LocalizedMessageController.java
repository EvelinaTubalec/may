package com.example.may.localization.controller;

import com.example.may.localization.service.LocalizedMessageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Evelina Tubalets
 */
@RestController
@AllArgsConstructor
public class LocalizedMessageController {

    private final LocalizedMessageService messageService;

    @GetMapping("/messages")
    public String getMessage() {
        return messageService.getMessage();
    }
}
