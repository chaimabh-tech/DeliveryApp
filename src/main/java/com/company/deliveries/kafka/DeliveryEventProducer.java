package com.company.deliveries.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DeliveryEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public DeliveryEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendDeliveryEvent(String eventMessage) {
        kafkaTemplate.send("delivery-events", eventMessage);
    }
}
