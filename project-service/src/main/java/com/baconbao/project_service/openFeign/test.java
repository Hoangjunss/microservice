package com.baconbao.project_service.openFeign;

import com.baconbao.project_service.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("notification-service")
public interface  test {
    @GetMapping(value = "/notification/getAll")
    ApiResponse<String> ok();
}
