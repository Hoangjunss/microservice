package com.baconbao.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class AppConfig {

    @Bean
    public HttpMessageConverters httpMessageConverters() {
        return new HttpMessageConverters(new MappingJackson2HttpMessageConverter());
    }
}
