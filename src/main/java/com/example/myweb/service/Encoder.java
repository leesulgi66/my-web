package com.example.myweb.service;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Encoder {
    @Bean
    BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }
}
