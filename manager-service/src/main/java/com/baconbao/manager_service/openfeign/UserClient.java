package com.baconbao.manager_service.openfeign;

import com.baconbao.manager_service.dto.ApiResponse;
import com.baconbao.manager_service.dto.AuthenticationRequest;
import com.baconbao.manager_service.dto.AuthenticationResponse;
import com.baconbao.manager_service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name ="user-service" )
public interface UserClient {
    @PostMapping("/auth/signup")
    ApiResponse<AuthenticationResponse> signUp(@RequestBody AuthenticationRequest signUpRequest);
    @GetMapping("/auth/getCurrentUser")
    ApiResponse<UserDTO> getCurrentUser() ;
}
