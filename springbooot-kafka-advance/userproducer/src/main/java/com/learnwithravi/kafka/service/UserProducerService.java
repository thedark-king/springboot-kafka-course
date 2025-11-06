package com.learnwithravi.kafka.service;

import com.learnwithravi.kafka.dto.User;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserProducerService {

    KafkaTemplate<String, User> kafkaTemplate;
    public UserProducerService(KafkaTemplate<String, User> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserData(User user) {
        System.out.println("Sending user data to " + user.getName() + " with age " + user.getAge()
        + " and favorite genre " + user.getFavGenre());

        kafkaTemplate.send("user-topic", user);
    }
}
