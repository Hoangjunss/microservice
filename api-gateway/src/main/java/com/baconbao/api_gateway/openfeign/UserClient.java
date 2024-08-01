package com.baconbao.api_gateway.openfeign;


import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/ourUserDetailsService")
    UserDetails getUserDetails(String username);

}
