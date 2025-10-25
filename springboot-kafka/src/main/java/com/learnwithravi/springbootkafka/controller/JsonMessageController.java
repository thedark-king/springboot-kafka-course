package com.learnwithravi.springbootkafka.controller;

import com.learnwithravi.springbootkafka.kafka.KafkaJsonProducer;
import com.learnwithravi.springbootkafka.payload.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/kafka/json")
public class JsonMessageController {

    private KafkaJsonProducer kafkaJsonProducer;

    public JsonMessageController(KafkaJsonProducer kafkaJsonProducer) {
        this.kafkaJsonProducer = kafkaJsonProducer;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publishMessage(@RequestBody User user) {
        kafkaJsonProducer.sendMessage(user);
        return ResponseEntity.ok("Json message published successfully");
    }

}
