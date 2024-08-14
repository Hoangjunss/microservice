package com.baconbao.manager_service.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("kafka-service")
public interface KafkaClient {
    @PostMapping("/send")
   void sendMessage(@RequestParam String topic, @RequestParam MessageDTO message);
}
