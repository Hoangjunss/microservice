package com.baconbao.notification_service.openFeign;

import com.baconbao.notification_service.dto.ApiResponse;
import com.baconbao.notification_service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/auth/checkId")
    ApiResponse<Boolean> checkId(@RequestParam Integer id);
    @GetMapping("/auth/getCurrentUser")
    ApiResponse<UserDTO> getCurrentUser() ;
}
