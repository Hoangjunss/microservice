package com.baconbao.api_gateway.config;

import com.baconbao.api_gateway.UserService;
import com.baconbao.api_gateway.dto.AuthenticationResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor

public class AuthFilter implements GlobalFilter, Ordered {
    private final UserService userService;
    private final     ObjectMapper objectMapper;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Enter authentication filter....");
        String path = exchange.getRequest().getURI().getPath();
        log.info("path"+path);
        // Skip authentication for /auth/** endpoints
        if (path.startsWith("/auth")) {
            return chain.filter(exchange);
        }


        // Get token from authorization header
        List<String> authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
       log.info("auth" + String.valueOf(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)+""));
        if (authHeader == null ) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.getFirst().replace("Bearer ", "");
        log.info("Token: {}", token);

        return Mono.fromCallable(() -> userService.introspect(token))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(authenticationResponse -> log.info("authResponse: {}", authenticationResponse))
                .flatMap(authenticationResponse -> {
                    if (authenticationResponse.isValid()) {
                        log.info("isvalid");
                        return chain.filter(exchange);
                    } else {
                        log.info("!isvalid");
                        return unauthenticated(exchange.getResponse());
                    }
                })
                .onErrorResume(e -> {
                    log.error("Error during introspection: ", e);
                    return unauthenticated(exchange.getResponse());
                });

    }

    @Override
    public int getOrder() {
        return -1;
    }
    Mono<Void> unauthenticated(ServerHttpResponse response){
       AuthenticationResponse authenticationResponse=AuthenticationResponse.builder().error("Unauthenticated").statusCode(1041)
                .build();

        String body = null;
        try {
            body = objectMapper.writeValueAsString(authenticationResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return response.writeWith(
                Mono.just(response.bufferFactory().wrap(body.getBytes())));
    }


}