package com.learnwithravi.orderproducer.customdeserializeer;

import io.confluent.common.utils.Utils;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;

public class VIPPartitioner implements Partitioner {
    @Override
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        List<PartitionInfo> orderPartitionedTopic = cluster.availablePartitionsForTopic("OrderPartitionedTopic");
        if(o1.toString().equals("VIPCustomer")){
            return 5;
        }
        return (Math.abs(Utils.murmur2(bytes)) % orderPartitionedTopic.size()-1);
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
