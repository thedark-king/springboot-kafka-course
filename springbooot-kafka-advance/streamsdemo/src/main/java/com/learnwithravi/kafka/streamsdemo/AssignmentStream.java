package com.learnwithravi.kafka.streamsdemo;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

public class AssignmentStream {
    public static void main(String[] args) {

        Properties props = new Properties();
        props.setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.setProperty(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.setProperty(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, "assignment-stream-app");

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> stream = builder.stream("assignment-input-topic");
        stream.map((key, value) -> {
            Integer intValue = Integer.parseInt(value);
            String result = null;
            if (intValue % 2 != 0) {
                intValue = intValue * 3;
                result = "Odd number processed: " + value + " * 3 = " + intValue;
                System.out.print(result);
            } else {
                result = "Even number processed: " + value + " * 1 = " + intValue;
                System.out.println(result);
            }
            return new KeyValue<>(key, result);
        }).to("assignment-output-topic");

        Topology topology = builder.build();
        System.out.println("Topology: " + topology.describe());
        KafkaStreams kafkaStreams = new KafkaStreams(topology, props);
        kafkaStreams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
    }
}
