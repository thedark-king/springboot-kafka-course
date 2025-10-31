package com.learnwithravi.stockservice.kafka;


import com.learnwithravi.basedomains.dto.OrderEvent;
import com.learnwithravi.stockservice.dto.OrderStocks;
import com.learnwithravi.stockservice.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);
    private StockRepository stockRepository;
    public OrderConsumer(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void orderConsumer(OrderEvent orderstocksEvent){
        LOGGER.info("Received Order Event: {}", orderstocksEvent);
        OrderStocks orderStocks = new OrderStocks();
        orderStocks.setOrderId(orderstocksEvent.getOrderId());
        //Process the order event and update stock accordingly
        LOGGER.info("Processing order for Order ID: {}", orderStocks.getOrderId());
        // After processing, you might want to send an update back to another topic
        orderStocks.setStatus("COMPLETED");
        orderStocks.setMessage("Order has been processed and stock updated.");
        stockRepository.save(orderStocks);
        LOGGER.info("Order Event processed and saved: {}", orderstocksEvent);
    }

}
