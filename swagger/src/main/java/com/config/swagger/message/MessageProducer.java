package com.config.swagger.message;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageProducer {
    private static final Logger log = LoggerFactory.getLogger(MessageProducer.class);
    private final RabbitTemplate rabbitTemplate;

    public void sendEmail(Message message,String exchange, String routingKey) {
        log.info("Sending message...");
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        log.info("Send message success");
    }
}