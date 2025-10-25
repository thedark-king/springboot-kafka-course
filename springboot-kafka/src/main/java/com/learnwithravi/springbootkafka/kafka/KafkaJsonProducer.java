package com.learnwithravi.springbootkafka.kafka;

import com.learnwithravi.springbootkafka.payload.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class KafkaJsonProducer {

    @Value("${spring.kafka.topic-json.name}")
    private String jsonTopic;

    Logger LOGGER = LoggerFactory.getLogger(KafkaJsonProducer.class);
    private KafkaTemplate<String, User> kafkaTemplate;

    public KafkaJsonProducer(KafkaTemplate<String, User> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    //bin/kafka-console-consumer.sh --topic java_kafka_json_topic --from-beginning --bootstrap-server localhost:9092
    public void sendMessage(User data) {
        LOGGER.info("Sent JSON message: {}", data.toString());
        Message<User> message = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, jsonTopic)
                .build();
        kafkaTemplate.send(message);
    }
}
