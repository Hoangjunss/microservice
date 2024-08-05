package com.baconbao.api_gateway;

import com.baconbao.api_gateway.dto.AuthenticationRequest;
import com.baconbao.api_gateway.dto.AuthenticationResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService
{
   private final WebClient webClient;


    public AuthenticationResponse introspect(String token) {
        return webClient.post()
                .uri("/isValid")
                .bodyValue(token)
                .retrieve()
                .bodyToMono(AuthenticationResponse.class)
                .block(); // Chuyển đổi sang đồng bộ
    }
}