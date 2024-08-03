package com.baconbao.api_gateway.config;

import com.baconbao.api_gateway.UserClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfig {
    @Bean
    WebClient webClient(){
        return WebClient.builder()
                .baseUrl("http://localhost:8088")
                .build();
    }

    @Bean
    UserClient userClient(WebClient webClient){
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient)).build();

        return httpServiceProxyFactory.createClient(UserClient.class);
    }
}