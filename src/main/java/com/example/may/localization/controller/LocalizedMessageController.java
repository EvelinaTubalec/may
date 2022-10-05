package com.example.may.localization.controller;

import com.example.may.localization.service.LocalizedMessageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Evelina Tubalets
 */
@RestController
@AllArgsConstructor
@RequestMapping("/localized-messages")
public class LocalizedMessageController {

    private final LocalizedMessageService messageService;

    @GetMapping()
    public String getMessage() {
        return messageService.getMessage();
    }
}
