package com.learnwithravi.kafka;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

public record WikimediaChangesHandler(KafkaTemplate<String, String> kafkaTemplate,
                                      @Value("${spring.kafka.topic.name}") String topic) implements EventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaChangesHandler.class);

    public WikimediaChangesHandler(KafkaTemplate<String, String> kafkaTemplate, String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void onOpen() {
        LOGGER.info("SSE connection opened");
    }

    @Override
    public void onClosed() {
        LOGGER.info("SSE connection closed");
    }

    @Override
    public void onMessage(String event, MessageEvent messageEvent) {
        LOGGER.info("Event data: {}", messageEvent.getData());
        kafkaTemplate.send(topic, messageEvent.getData());
    }

    @Override
    public void onComment(String comment) {
        LOGGER.debug("Comment: {}", comment);
    }

    @Override
    public void onError(Throwable t) {
        LOGGER.error("Error in SSE stream", t);
    }
}