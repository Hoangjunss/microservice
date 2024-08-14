package com.baconbao.email_service.openfeign;

import com.baconbao.email_service.dto.ApiResponse;
import com.baconbao.email_service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("user-service")
public interface UserClient {
    @GetMapping("/findbyid")
    ApiResponse<UserDTO> findById(@RequestParam Integer id);
}
