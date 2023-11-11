package com.example.myweb.config.jwt;

import com.example.myweb.model.User;
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

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_REFRESH_HEADER = "Refresh";

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
        if(cookies != null) {
            for(Cookie c : cookies) {
                //System.out.println((c.getName()));  // 쿠키 이름 가져오기
                //System.out.println((c.getValue()) );  // 쿠키 값 가져오기
                if(c.getName().equals("Authorization")){
                    if(StringUtils.hasText(c.getValue()) && tokenProvider.validateToken(c.getValue()) == TokenProvider.JwtCode.ACCESS) {
                        Authentication authentication = tokenProvider.getAuthentication(c.getValue());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        log.info("Cookie : Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
                    }else if(StringUtils.hasText(c.getValue()) && tokenProvider.validateToken(c.getValue()) == TokenProvider.JwtCode.EXPIRED){
                        log.info("만료된 토큰으로 로그인");
                        String refreshToken = tokenProvider.getRefresh(c.getValue());
                        log.info("리프레시 토큰 확인 : "+refreshToken);
                        if(tokenProvider.validateToken(refreshToken) == TokenProvider.JwtCode.ACCESS) {
                            log.info("리프레쉬 토큰 사용 가능 - 새로운 토큰 발급");
                            Authentication authentication = tokenProvider.getAuthentication(refreshToken);
                            User principal = (User) authentication.getPrincipal();
                            String reJwt = tokenProvider.createToken(principal);

                            Cookie cookie = new Cookie(JwtFilter.AUTHORIZATION_HEADER, reJwt);
                            cookie.setPath("/");
                            cookie.setMaxAge(3600);

                            response.addCookie(cookie);
                        }
                    } else {
                        log.info("cookie에 유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
                    }
                }
            }
        }
        //-------------------------------------------------------------------------------------------------

        if(jwt != null) {
            if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt) == TokenProvider.JwtCode.ACCESS) {
                Authentication authentication = tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("header : Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
            }else if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt) == TokenProvider.JwtCode.EXPIRED){
                log.info("만료된 토큰으로 로그인");
                String refreshToken = tokenProvider.getRefresh(jwt);
                log.info("리프레시 토큰 확인 : "+refreshToken);
                if(tokenProvider.validateToken(refreshToken) == TokenProvider.JwtCode.ACCESS) {
                    log.info("리프레쉬 토큰 사용 가능 - 새로운 토큰 발급");
                    Authentication authentication = tokenProvider.getAuthentication(refreshToken);
                    User principal = (User) authentication.getPrincipal();
                    String reJwt = tokenProvider.createToken(principal);

                    response.addHeader(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + reJwt);
                }
            }else {
                log.info("header에 유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
            }
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
