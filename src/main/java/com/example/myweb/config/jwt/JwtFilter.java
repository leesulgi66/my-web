package com.example.myweb.config.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private TokenProvider tokenProvider;

    public JwtFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = resolveToken(request);
        String requestURI = request.getRequestURI();

        // 임시 쿠키 처리 ----------------------------------------------------------------------------------
        Cookie[] cookies = request.getCookies();
        for(Cookie c : cookies) {
            //System.out.println((c.getName()));  // 쿠키 이름 가져오기
            //System.out.println((c.getValue()) );  // 쿠키 값 가져오기
            if(c.getName().equals("Authorization")){
                if(StringUtils.hasText(c.getValue()) && tokenProvider.validateToken(c.getValue()) == TokenProvider.JwtCode.ACCESS) {
                    Authentication authentication = tokenProvider.getAuthentication(c.getValue());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("Cookie : Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
                }else {
                    log.info("Cookie : 유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
                }
            }
        }
        //-------------------------------------------------------------------------------------------------

        if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt) == TokenProvider.JwtCode.ACCESS) {
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
        }else {
            log.info("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
