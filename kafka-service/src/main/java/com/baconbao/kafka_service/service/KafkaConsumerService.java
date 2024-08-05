package com.baconbao.kafka_service.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "your-topic", groupId = "kafka-service-group")
    public void listen(String message) {
        System.out.println("Received Message: " + message);
        // Xử lý tin nhắn ở đây
    }
}