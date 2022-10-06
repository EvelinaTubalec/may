package com.example.may.rabbitmq.config;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Evelina Tubalets
 */
@Configuration
@AllArgsConstructor
public class RabbitMQConfig {

    private final CachingConnectionFactory cachingConnectionFactory;

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(Jackson2JsonMessageConverter converter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        rabbitTemplate.setMessageConverter(converter);
        return rabbitTemplate;
    }

    @Bean
    public Declarables createSendEmailSchema() {
        return new Declarables(
                new TopicExchange("x.send-email"),
                new Queue("q.send-email-message"),
                new Binding(
                        "q.send-email-message", Binding.DestinationType.QUEUE,
                        "x.send-email",
                        "send-email",
                        null));
    }
}
