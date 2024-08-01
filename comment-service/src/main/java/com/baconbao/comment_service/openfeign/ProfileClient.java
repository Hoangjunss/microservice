package com.baconbao.comment_service.openfeign;

import com.baconbao.comment_service.dto.ProfileDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="profile-service")
public interface ProfileClient {
    @GetMapping("/findById")
    ProfileDTO getProfileById(Integer id);
}
