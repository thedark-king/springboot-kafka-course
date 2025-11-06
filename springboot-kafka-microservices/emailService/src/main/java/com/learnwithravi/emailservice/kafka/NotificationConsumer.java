package com.learnwithravi.emailservice.kafka;


import com.learnwithravi.basedomains.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    private Logger LOGGER = LoggerFactory.getLogger(NotificationConsumer.class);

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void orderConsumer(OrderEvent orderstocksEvent){
        LOGGER.info("Received Order Event: {}", orderstocksEvent);
    }

}
