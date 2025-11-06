package com.learnwithravi.kafka.controller;

import com.learnwithravi.kafka.dto.User;
import com.learnwithravi.kafka.service.UserProducerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userapi/v1")
public class UserController {

    private final UserProducerService userProducerService;

    public UserController(UserProducerService userProducerService) {
        this.userProducerService = userProducerService;
    }

    @PostMapping("/publishUserData")
    public void sendUSerDetails(@RequestBody User user){
        userProducerService.sendUserData(user);
    }

}
