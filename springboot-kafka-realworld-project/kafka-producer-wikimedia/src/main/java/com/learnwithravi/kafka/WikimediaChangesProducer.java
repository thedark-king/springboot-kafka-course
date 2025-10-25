package com.learnwithravi.kafka;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import okhttp3.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class WikimediaChangesProducer {

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaChangesProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public WikimediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    //kafka-console-consumer.sh --topic wikimedia_recentchange --from-beginning --bootstrap-server localhost:9092
    public void sendMessage() throws InterruptedException {

        EventHandler handler = new WikimediaChangesHandler(kafkaTemplate, topicName);
        String url = "https://stream.wikimedia.org/v2/stream/recentchange";

// Set required headers
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", "KafkaProducerApp/1.0 (ravi@example.com)"); // must include contact info

        EventSource eventSource = new EventSource.Builder(handler, URI.create(url))
                .headers(Headers.of(headers))
                .build();

        eventSource.start();
        LOGGER.info("Sending Wikimedia changes event");
// Keep running for 10 minutes
        TimeUnit.MINUTES.sleep(10);
        eventSource.close();
    }
}