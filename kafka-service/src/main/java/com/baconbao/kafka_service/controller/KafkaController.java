package com.baconbao.kafka_service.controller;

import com.baconbao.kafka_service.service.KafkaProducerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    private final KafkaProducerService kafkaProducerService;

    public KafkaController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/send")
    public void sendMessage(@RequestParam String topic, @RequestParam String message) {
        kafkaProducerService.sendMessage(topic, message);
    }
}