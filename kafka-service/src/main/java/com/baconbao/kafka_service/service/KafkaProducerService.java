package com.baconbao.kafka_service.service;

import com.baconbao.kafka_service.dto.MessageDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    @Autowired
    private final KafkaTemplate<String, MessageDTO> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, MessageDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, MessageDTO message) {
        kafkaTemplate.send(topic, message);
    }
}