package com.learnwithravi.kafka.service;

import com.learnwithravi.kafka.dto.CreditCard;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CreditCardConsumerService {

    @KafkaListener(topics = {"creditcard-topic"})
    public void consumeCustomerMessage(CreditCard creditCard) {
        System.out.println("Consumed credit card message: " +
                "\n Card Number: " + creditCard.getCardNumber() +
                "\n Full Name: " + creditCard.getFullname() +
                "\n Expiry Date: " + creditCard.getExpiryDate());
    }
}
