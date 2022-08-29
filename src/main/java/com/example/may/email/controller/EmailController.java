package com.example.may.email.controller;

import com.example.may.email.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Evelina Tubalets
 */
@RestController
@RequestMapping("/emails")
@AllArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/actions/send-message")
    private void sendEmail() {
        emailService.sendCongratulationEmail();
    }
}
