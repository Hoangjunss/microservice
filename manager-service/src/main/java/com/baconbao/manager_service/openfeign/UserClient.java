package com.baconbao.manager_service.openfeign;

import com.baconbao.manager_service.dto.ApiResponse;
import com.baconbao.manager_service.dto.AuthenticationRequest;
import com.baconbao.manager_service.dto.AuthenticationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name ="user-service" )
public interface UserClient {
    @PostMapping("/signup")
    ApiResponse<AuthenticationResponse> signUp(@RequestBody AuthenticationRequest signUpRequest);

}
