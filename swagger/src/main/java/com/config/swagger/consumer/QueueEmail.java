package com.config.swagger.consumer;

import com.config.swagger.message.Message;
import com.config.swagger.message.MessageProducer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.config.swagger.statics.Queue.CONSUMER_EMAIL;

@AllArgsConstructor
@Data
@Log4j2
@Component
public class QueueEmail {
    private final MessageProducer messageProducer;

    @RabbitListener(queues = CONSUMER_EMAIL)
    public void consumerEmail(Message message) {
        log.info("\n\n\t\t\uD83D\uDE80 \uD83D\uDEF8 \uD83C\uDF0C \"{}\" started as service \"{}\", " +
                        "listening to queue \"{}\" @ rabbitmq vhost \"{}\" \uD83C\uDF0C \uD83D\uDEF8 \uD83D\uDE80\n"+"",
                "Swagger_demo", CONSUMER_EMAIL, "dev",message.getEmailAddress());
    }

}
