package com.example.may.config;

import com.example.may.email.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Evelina Tubalets
 */
@Component
@AllArgsConstructor
public class Scheduler {

    private final EmailService emailService;

    @Scheduled(cron = "0 0 0 * * *")
    private void sendCongratulationEmailTask() {
        emailService.sendCongratulationEmail();
    }
}
