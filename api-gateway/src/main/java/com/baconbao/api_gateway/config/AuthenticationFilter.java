package com.baconbao.api_gateway.config;

import com.baconbao.api_gateway.UserService;
import com.baconbao.api_gateway.dto.AuthenticationResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public AuthenticationFilter(@Lazy UserService userService, ObjectMapper objectMapper) {
        super(Config.class);
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    Mono<Void> unauthenticated(ServerHttpResponse response) {
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder().error("Unauthenticated")
                .statusCode(1041).build();

        String body = null;
        try {
            body = objectMapper.writeValueAsString(authenticationResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return response.writeWith(Mono.just(response.bufferFactory().wrap(body.getBytes())));
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();

            // Skip authentication for /auth/** endpoints
            if (path.startsWith("/auth")) {
                return chain.filter(exchange);
            }

            // Get token from authorization header
            List<String> authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || authHeader.isEmpty()) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            String[] parts = path.split("/");

            String token = authHeader.get(0).substring(7);
            return userService.isValid(token)
                    .flatMap(authenticationResponse -> {
                        AuthenticationResponse authenticationResponse1 = authenticationResponse.getData();
                        log.info(authenticationResponse.getData().toString()+" filter exchange");
                        if (authenticationResponse.getData().isVaild()) {
                            switch (authenticationResponse.getData().getRole()) {
                                case "admin":
                                    if (parts[2].startsWith("/admin")) {
                                        return chain.filter(exchange);
                                    } else
                                        return unauthenticated(exchange.getResponse());
                                case "user":
                                    if (parts[2].startsWith("/user")) {
                                        return chain.filter(exchange);
                                    } else
                                        return unauthenticated(exchange.getResponse());
                                case "manager":
                                    if (parts[2].startsWith("/manager")) {
                                        return chain.filter(exchange);
                                    } else
                                        return unauthenticated(exchange.getResponse());
                                case "hr":
                                    if (parts[2].startsWith("/hr")) {
                                        return chain.filter(exchange);
                                    } else
                                        return unauthenticated(exchange.getResponse());
                            }
                            return chain.filter(exchange);
                        } else {

                            return unauthenticated(exchange.getResponse());
                        }
                    })
                    .onErrorResume(e -> {

                        return unauthenticated(exchange.getResponse());
                    });
        };
    }

    public static class Config {
    }
}
