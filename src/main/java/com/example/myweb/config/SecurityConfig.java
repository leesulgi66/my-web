package com.example.myweb.config;

import com.example.myweb.config.auth.PrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    PrincipalDetailService principalDetailService;

    @Bean
    BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //csrf() js로 들어오는 요청을 막아줌. (ajax 요청이 막히기 때문에 임시로 걸어둠)
                .authorizeRequests()
                .antMatchers("/","/auth/**", "/js/**", "/css/**", "/images/**", "/**/*.css")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/auth/loginForm") // login page
                .loginProcessingUrl("/auth/login")  // login 요청주소
                .defaultSuccessUrl("/"); // login 성공시 이동주소
        return http.build();
    }
}
