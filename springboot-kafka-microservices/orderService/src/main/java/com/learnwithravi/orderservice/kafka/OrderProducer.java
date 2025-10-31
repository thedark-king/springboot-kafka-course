package com.learnwithravi.orderservice.kafka;

import com.learnwithravi.basedomains.dto.OrderEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private NewTopic topic;
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;
    private Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);

    public OrderProducer(NewTopic topic, KafkaTemplate kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderEvent(OrderEvent orderEvent) {
        LOGGER.info(String.format("Order Event sent -> %s", orderEvent.toString()));

        //Create Message
        Message<OrderEvent> eventMessage = MessageBuilder
                        .withPayload(orderEvent)
                        .setHeader(KafkaHeaders.TOPIC, topic.name())
                        .build();
        kafkaTemplate.send(eventMessage);
    }


}
