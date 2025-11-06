package com.learnwithravi.orderproducer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

public class OrderCallback implements Callback {

    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        System.out.println("Partition " + recordMetadata.partition() + " offset " + recordMetadata.offset());
        System.out.println("Message sent successfully");
        if(e != null) {
            e.printStackTrace();
        }
    }
}
