package com.learnwithravi.orderconsumer.customeDeserializer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnwithravi.orderconsumer.DTO.Truck;
import org.apache.kafka.common.serialization.Deserializer;

public class TruckDeserializer implements Deserializer<Truck> {

    @Override
    public Truck deserialize(String topic, byte[] data) {
        if (data == null) return null;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return mapper.readValue(data, Truck.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing Truck", e);
        }
    }
}