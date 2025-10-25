package com.learnwithravi.springbootkafka.kafka;

import com.learnwithravi.springbootkafka.payload.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class kaJsonConsumer {

    private Logger LOGGER = LoggerFactory.getLogger(kaJsonConsumer.class);

    @KafkaListener(topics = "${spring.kafka.topic-json.name}", groupId = "${spring.kafka.consumer.client-id}")
    public void consumeJsonMessage(User user) {
        LOGGER.info("Received JSON message: {}", user.toString());
    }
}
