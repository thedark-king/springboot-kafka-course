package com.learnwithravi.orderconsumer;

import com.learnwithravi.orderconsumer.DTO.Order;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.util.*;

@SpringBootApplication
public class OrderConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderConsumerApplication.class, args);
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "com.learnwithravi.orderconsumer.customeDeserializer.OrderDeserializer");
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "OrderGroup");
        props.setProperty(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, "102423323232233");
        props.setProperty(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, "200");
        props.setProperty(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, "1000");
        props.setProperty(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        //30 partition, 5 consumers in a group, 6MB - 12MB =>(30/5) = 6MB per consumer
        props.setProperty(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, "1024");
        props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, "OrderConsumer");
        props.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "100");
//        props.setProperty(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, RangeAssignor.class.getName()); //Consucative partition topics by default is RangeAssignor
        props.setProperty(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, RoundRobinAssignor.class.getName());
        props.setProperty("auto.commit.offset", "false");

        TruckAssignmentConsumer truckAssignmentConsumer = new TruckAssignmentConsumer();
        truckAssignmentConsumer.truckAssignConsume();
        int count = 0;
        KafkaConsumer<String, Order> consumer = new KafkaConsumer<>(props);
        Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();

        class RebalanceHandler implements ConsumerRebalanceListener {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                System.out.println("Partitions revoked: " + partitions);
                consumer.commitSync(currentOffsets); // safe now
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                System.out.println("Partitions assigned: " + partitions);
            }
        }
        consumer.subscribe(Collections.singletonList("OrderPartitionedTopic"), new RebalanceHandler());


        while (true) {

            ConsumerRecords<String, Order> records = consumer.poll(Duration.ofSeconds(20));
            records.forEach(record -> {
                String customerName = record.key();
                System.out.println(" CustomerName : " + customerName);
                Order order = record.value();
                System.out.println(" Partition : " + record.partition());
                System.out.println(" Item Name : " + order.getItemName() + " Quantity : " + order.getQuantity());
                currentOffsets.put(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset() + 1));

                if (count % 10 == 0) {
                    consumer.commitAsync(currentOffsets,
                            new OffsetCommitCallback() {
                                @Override
                                public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
                                    if (exception != null) {
                                        System.out.println("Commit failed for offsets " + offsets);
                                        exception.printStackTrace();
                                    } else {
                                        System.out.println("Commit succeeded for offsets " + offsets);
                                    }
                                }
                            }
                    );
                }

            });
            consumer.close();
        }
    }
}
