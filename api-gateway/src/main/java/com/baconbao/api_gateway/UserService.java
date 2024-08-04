package com.baconbao.api_gateway;

import com.baconbao.api_gateway.dto.AuthenticationRequest;
import com.baconbao.api_gateway.dto.AuthenticationResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService
{
    UserClient userClient;
    WebClient webClient;

    public Mono<AuthenticationResponse> introspect(String token) {
        return webClient.post()
                .uri("/auth/isValid")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(token)
                .retrieve()
                .bodyToMono(AuthenticationResponse.class);
    }
}