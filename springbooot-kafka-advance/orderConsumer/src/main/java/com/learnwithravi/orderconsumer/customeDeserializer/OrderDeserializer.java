package com.learnwithravi.orderconsumer.customeDeserializer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnwithravi.orderconsumer.DTO.Order;
import org.apache.kafka.common.serialization.Deserializer;

public class OrderDeserializer implements Deserializer<Order> {

    @Override
    public Order deserialize(String topic, byte[] data) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Order order = null;
        try {
            order = objectMapper.readValue(data, Order.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return order;
    }
}


