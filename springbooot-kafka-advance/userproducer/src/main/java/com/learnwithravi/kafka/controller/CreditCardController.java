package com.learnwithravi.kafka.controller;

import com.learnwithravi.kafka.dto.CreditCard;
import com.learnwithravi.kafka.service.CreditCardProducerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/credit/api/v1")
public class CreditCardController {

    private final CreditCardProducerService creditCardProducerService;

    public CreditCardController(CreditCardProducerService creditCardProducerService) {
        this.creditCardProducerService = creditCardProducerService;
    }

    @PostMapping("/publishUserData")
    public void sendUSerDetails(@RequestBody CreditCard creditCard){
        creditCardProducerService.sendUserData(creditCard);
    }

}
