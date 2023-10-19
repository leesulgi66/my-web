package com.example.myweb.config;

import com.example.myweb.config.auth.PrincipalDetailsService;
import com.example.myweb.config.auth.PrincipalOauth2UserService;
import com.example.myweb.config.jwt.JwtAccessDeniedHandler;
import com.example.myweb.config.jwt.JwtAuthenticationEntryPoint;
import com.example.myweb.config.jwt.JwtSecurityConfig;
import com.example.myweb.config.jwt.TokenProvider;
import com.example.myweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private PrincipalDetailsService principalDetailsService;
    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;
    @Autowired
    private CorsConfig corsConfig;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //csrf() js로 들어오는 요청을 막아줌. (ajax 요청이 막히기 때문에 임시로 걸어둠)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //인증 처리 관점에서 세션을 생성하지 않음
                .and()
                .formLogin().disable() // 기본 폼로그인 사용하지 않음 -> 때문에 기본적인 UsernamePasswordAuthenticationFilter가 작동을 하지 않음. 새로운 필터를 등록해야 함.
                .httpBasic().disable() // 아이디와 비밀번호가 노출되는 basic방식 사용하지 않음
                .apply(new MyCustomDsl()) // 커스텀 필터 등록
                .and()
                .apply(new JwtSecurityConfig(tokenProvider))
                .and()
                .authorizeRequests()
//                .antMatchers("/","/auth/**", "/js/**", "/css/**", "/images/**", "/**/*.css")
//                .permitAll()
                .antMatchers("/board/**")
                .authenticated()
                .anyRequest()
                .permitAll()
//                .access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
                .and()
                .formLogin().disable();
//                .loginPage("/auth/loginForm") // login page
//                .loginProcessingUrl("/auth/login")  // login 요청주소
//                .defaultSuccessUrl("/") // login 성공시 이동주소
//                .and()
//                .oauth2Login()
//                .loginPage("/auth/loginForm")
//                .userInfoEndpoint()
//                .userService(principalOauth2UserService);
        return http.build();
    }

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http
                    .addFilter(corsConfig.corsFilter());
//                    .addFilter(new JwtAuthenticationFilter(authenticationManager))
//                    .addFilter(new JwtAuthorizationFilter(authenticationManager, userService));
        }
    }
}
