package com.example.myweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 서버가 응답을 할 때 json 을 자바스크립트에서 처리할 수 있게 할지를 설정.
        config.addAllowedOrigin("\"http://localhost:8000\", \"http://localhost:3000\"");
        config.addAllowedHeader("*"); // 모든 header에 응답을 허용.
        config.addAllowedMethod("*"); // 모든 방식의 method를 허용.
        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}
