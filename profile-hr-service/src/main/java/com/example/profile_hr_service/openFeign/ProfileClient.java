package com.example.profile_hr_service.openFeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "profile-service   ")
public interface ProfileClient {
   @GetMapping("/checkIdProfile")
   Boolean checkIdProfile(Integer id);
}
