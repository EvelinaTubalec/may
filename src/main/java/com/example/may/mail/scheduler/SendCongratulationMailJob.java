package com.example.may.mail.scheduler;

import com.example.may.mail.service.MailService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Evelina Tubalets
 */
@Component
@AllArgsConstructor
public class SendCongratulationMailJob {

    private final MailService mailService;

    @Scheduled(cron = "0 0 0 * * *")
    private void send() {
        mailService.sendCongratulationEmail();
    }
}
