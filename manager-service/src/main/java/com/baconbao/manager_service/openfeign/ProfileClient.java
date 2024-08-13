package com.baconbao.manager_service.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "profile-service")

public interface ProfileClient {
    @GetMapping("/user/checkIdProfile")
    Boolean checkIdProfile(@RequestParam Integer id);
}
