package com.learnwithravi.kafka.service;

import com.learnwithravi.kafka.dto.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserConsumerService {

    @KafkaListener(topics = {"user-topic"})
    public void consumeCustomerMessage(User user) {
        System.out.println("Consumed customer message: " +
                "\n Name: " + user.getName() +
                "\n Age: " + user.getAge() +
                "\n Favorite Genre: " + user.getFavGenre());
    }
}
