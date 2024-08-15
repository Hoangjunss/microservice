package com.baconbao.manager_service.openfeign;

import com.baconbao.manager_service.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("notification-service")
public interface NotificationClient {
    @PostMapping("/notification/create")
    ApiResponse<String> create(@RequestBody MessageDTO messageDTO);
}
