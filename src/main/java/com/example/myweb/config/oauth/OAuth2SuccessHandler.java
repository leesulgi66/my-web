package com.example.myweb.config.oauth;

import com.example.myweb.config.auth.PrincipalDetails;
import com.example.myweb.config.jwt.JwtFilter;
import com.example.myweb.config.jwt.TokenProvider;
import com.example.myweb.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private TokenProvider tokenProvider;
    private UserService userService;
    public OAuth2SuccessHandler(TokenProvider tokenProvider,UserService userService) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        SecurityContextHolder.getContext().setAuthentication(authentication);
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        //jwt token
        String jwt = tokenProvider.createToken(authentication, principal);
        //refresh token
        String RFToken = tokenProvider.createRefreshToken(principal);
        //refresh token save
        userService.refreshSave(principal.getUser().getId() ,RFToken);

        Cookie cookie = new Cookie(JwtFilter.AUTHORIZATION_HEADER, jwt);
        cookie.setPath("/");
        cookie.setMaxAge(864);

        response.addHeader(JwtFilter.AUTHORIZATION_HEADER, "Bearer "+jwt);
        response.addCookie(cookie);

        response.sendRedirect("/");
    }
}
