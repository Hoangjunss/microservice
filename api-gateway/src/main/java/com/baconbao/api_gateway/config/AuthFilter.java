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

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class AuthFilter implements GlobalFilter, Ordered {
    UserService userService;
    ObjectMapper objectMapper;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Enter authentication filter....");

        // Get token from authorization header
        List<String> authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);

        if (authHeader == null ) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.getFirst().replace("Bearer ", "");
        log.info("Token: {}", token);


return userService.introspect(token).flatMap(authenticationResponse -> {
    if (authenticationResponse.isValid()){
        return chain.filter(exchange);
    }else    return unauthenticated(exchange.getResponse());
}).onErrorResume(throwable -> unauthenticated(exchange.getResponse()));

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