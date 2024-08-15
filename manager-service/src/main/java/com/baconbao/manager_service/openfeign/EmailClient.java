package com.baconbao.manager_service.openfeign;

import com.baconbao.manager_service.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("email-service")
public interface EmailClient {
    @PostMapping("/email/create")
    ApiResponse<String> send(@RequestBody MessageDTO messageDTO);
}
