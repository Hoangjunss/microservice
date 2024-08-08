package com.baconbao.manager_service.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "profile-service")
public interface ProfileClient {
    @GetMapping("/checkIdProfile")
    Boolean checkIdProfile(Integer id);
}
