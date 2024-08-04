package com.baconbao.api_gateway;

import com.baconbao.api_gateway.dto.AuthenticationRequest;
import com.baconbao.api_gateway.dto.AuthenticationResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

public interface UserClient {
    @PostExchange(url = "/auth/isValid", contentType = MediaType.APPLICATION_JSON_VALUE)
    AuthenticationResponse isValid(@RequestBody String token);
}