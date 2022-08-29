package com.example.may.email.scheduler;

import com.example.may.email.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Evelina Tubalets
 */
@Component
@AllArgsConstructor
public class SendCongratulationEmailJob {

    private final EmailService emailService;

    @Scheduled(cron = "0 0 0 * * *")
    private void send() {
        emailService.sendCongratulationEmail();
    }
}
