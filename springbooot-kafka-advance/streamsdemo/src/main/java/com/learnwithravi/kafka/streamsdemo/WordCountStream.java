package com.learnwithravi.kafka.streamsdemo;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Arrays;
import java.util.Properties;

public class WordCountStream {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, "stream-dataflow-app");
        props.setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.setProperty(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.setProperty(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
//        props.setProperty(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, "0"); //disable caching for development purpose
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 1000); // flush every second for demo

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> stream = builder.stream("streams-wordcount-input");
        KGroupedStream<String, String> kGroupedStream = stream.flatMapValues(value -> Arrays.asList(value.toLowerCase().split(" ")))
                .groupBy((key, value) -> value);
        System.out.println("key" + "value");
        KTable<String, Long> count = kGroupedStream.count();
        count.toStream().to("streams-wordcount-output", Produced.with(Serdes.String(), Serdes.Long())); //Because KStream returns String and Long

        Topology topology = builder.build();
        System.out.println("Topology: " + topology.describe());


        KafkaStreams kafkaStreams = new KafkaStreams(topology, props);
        kafkaStreams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));


    }

}
