package com.github.tantalor93.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String TOPIC_EXCHANGE_NAME = "boot-app-exchange";

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMesaggeConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
