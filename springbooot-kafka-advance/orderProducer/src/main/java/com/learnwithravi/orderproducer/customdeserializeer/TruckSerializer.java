package com.learnwithravi.orderproducer.customdeserializeer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnwithravi.orderproducer.DTO.Truck;
import org.apache.kafka.common.serialization.Serializer;

public class TruckSerializer implements Serializer<Truck> {

    private final ObjectMapper mapper = new ObjectMapper();
    @Override
    public byte[] serialize(String topic, Truck truck){
        byte[] serializeTruckResponse = null;
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            serializeTruckResponse = mapper.writeValueAsString(truck).getBytes();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return serializeTruckResponse;
    }
}
