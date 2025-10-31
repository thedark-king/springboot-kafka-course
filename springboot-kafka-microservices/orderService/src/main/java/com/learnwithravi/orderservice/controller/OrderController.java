package com.learnwithravi.orderservice.controller;

import com.learnwithravi.basedomains.dto.Order;
import com.learnwithravi.basedomains.dto.OrderEvent;
import com.learnwithravi.orderservice.kafka.OrderProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private OrderProducer orderProducer;
    Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping("/orders")
    public String placeOrder(@RequestBody Order order) {
        LOGGER.info("Received Order Request {}", order.toString());
//        order.setOrderId();
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setOrderId(UUID.randomUUID().toString());
        orderEvent.setStatus("PENDING");
        orderEvent.setMessage("order status is in pending state");
        orderProducer.sendOrderEvent(orderEvent);
        return "Order Placed Successfully";
    }


}
