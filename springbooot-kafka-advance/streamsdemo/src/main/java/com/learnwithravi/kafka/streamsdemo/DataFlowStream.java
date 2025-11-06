package com.learnwithravi.kafka.streamsdemo;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

public class DataFlowStream {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, "streams-dataflow-app");
        props.setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.setProperty(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.setProperty(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<String, String> dataFlowInputTopic = streamsBuilder.stream("streams-dataflow-input");
        dataFlowInputTopic.foreach((key, value) -> System.out.println("key: " + key + " value: "+ value));
        dataFlowInputTopic.filter((key, value) -> value.contains("token"))
//                .mapValues((value -> value.toUpperCase()))
//                .map((key, value) -> new KeyValue<>(key, value.toUpperCase()))
                .map((key,value) -> KeyValue.pair("token", value))
                .to("streams-dataflow-output");

        Topology topology = streamsBuilder.build();
        System.out.println("Topology: " + topology.describe());
        KafkaStreams kafkaStreams = new KafkaStreams(topology, props);
        kafkaStreams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));


    }

}
