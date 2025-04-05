package com.company.deliveries.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class DeliveryEventConsumer {

    @KafkaListener(topics = "delivery-events", groupId = "delivery-group")
    public void consume(String message) {
        System.out.println("📥 Consumed Kafka message: " + message);
    }
}
