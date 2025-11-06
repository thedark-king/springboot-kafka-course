package com.learnwithravi.orderproducer.customdeserializeer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import com.learnwithravi.orderproducer.DTO.Order;


public class OrderSerializer implements Serializer<Order> {
    @Override
    public byte[] serialize(String topic, Order data) {
        byte[] serializedOrderResponse = null;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            try {
                serializedOrderResponse = mapper.writeValueAsString(data).getBytes();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return serializedOrderResponse;
    }
}
