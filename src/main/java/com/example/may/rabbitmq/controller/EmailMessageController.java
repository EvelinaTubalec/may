package com.example.may.rabbitmq.controller;

import com.example.may.rabbitmq.service.ProducerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Evelina Tubalets
 */
@Slf4j
@RestController
@RequestMapping("/messages")
@AllArgsConstructor
public class EmailMessageController {

    private final ProducerService producerService;

    /**
     * Endpoint which helps to test sending message to rabbitMQ.
     */
    @PostMapping("/emails")
    public void sendEmailMessageToRabbitMQ() {
        producerService.sendTestEmailMessage();
    }
}
