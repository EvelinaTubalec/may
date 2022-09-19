package com.example.may.mail.controller;

import com.example.may.mail.service.MailService;
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
public class MailController {

    private final MailService mailService;

    @GetMapping("/actions/send-message-test")
    private void sendTestEmail() {
        mailService.sendTestEmail();
    }

    @GetMapping("/actions/send-message")
    private void sendEmail() {
        mailService.sendCongratulationEmail();
    }
}
