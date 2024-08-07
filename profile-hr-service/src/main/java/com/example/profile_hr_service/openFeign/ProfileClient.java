package com.example.profile_hr_service.openFeign;

import com.example.profile_hr_service.dto.ProfileDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "profile-service   ")
public interface ProfileClient {
   @GetMapping("/checkIdProfile")
   Boolean checkIdProfile(Integer id);
}
