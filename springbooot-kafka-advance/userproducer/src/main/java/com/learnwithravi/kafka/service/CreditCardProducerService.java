package com.learnwithravi.kafka.service;

import com.learnwithravi.kafka.dto.CreditCard;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CreditCardProducerService {

    KafkaTemplate<String, CreditCard> kafkaTemplate;

    public CreditCardProducerService(KafkaTemplate<String, CreditCard> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserData(CreditCard creditCard) {
        System.out.println("sending credit card data to " + creditCard.getCardNumber() + " with card holder name " + creditCard.getFullname()
                + " and expiry date " + creditCard.getExpiryDate());

        kafkaTemplate.send("creditcard-topic", creditCard);
    }
}
