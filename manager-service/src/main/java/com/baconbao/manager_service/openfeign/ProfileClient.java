package com.baconbao.manager_service.openfeign;

import com.baconbao.manager_service.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "profile-service")

public interface ProfileClient {
    @GetMapping("/profile/user/checkIdProfile")
    ApiResponse<Boolean> checkIdProfile(@RequestParam Integer id);
}
