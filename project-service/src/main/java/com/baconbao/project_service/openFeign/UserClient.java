package com.baconbao.project_service.openFeign;

import com.baconbao.project_service.dto.ApiResponse;
import com.baconbao.project_service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("user-service")
public interface UserClient {
    @GetMapping("/auth/getCurrentUser")
    ApiResponse<UserDTO> getCurrentUser() ;}
