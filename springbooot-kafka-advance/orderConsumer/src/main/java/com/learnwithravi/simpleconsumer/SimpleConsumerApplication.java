package com.learnwithravi.simpleconsumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@SpringBootApplication
public class SimpleConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleConsumerApplication.class, args);
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "OrderGroup");
        props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        List<PartitionInfo> simpleConsumerTopic = consumer.partitionsFor("SimpleConsumerTopic");
        ArrayList<TopicPartition> partitions = new ArrayList<>();

//        partitions.add(new TopicPartition("orders", 0));
//        partitions.add(new TopicPartition("orders", 1));
//        OR
        for (PartitionInfo partitionInfo : simpleConsumerTopic) {
            partitions.add(new TopicPartition("SimpleConsumerTopic", partitionInfo.partition()));
        }
        consumer.assign(partitions);
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(2000));
            records.forEach(record -> {
                System.out.printf("Topic: %s\n", record.topic());
                String key = record.key();
                String value = record.value();
                System.out.println(" Key : " + key + " Value : " + value);
            });
        }
//        consumer.close();
    }
}
