package com.example.myweb.controller.api;

import com.example.myweb.config.auth.PrincipalDetails;
import com.example.myweb.config.jwt.JwtFilter;
import com.example.myweb.config.jwt.TokenProvider;
import com.example.myweb.dto.LoginDto;
import com.example.myweb.dto.TokenDto;
import com.example.myweb.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class LoginAuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserService userService;

    public LoginAuthController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, UserService userService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userService = userService;
    }

    @PostMapping("authenticate")
    public ResponseEntity<TokenDto> authorize(@RequestBody LoginDto loginDto, HttpServletResponse res) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

        String jwt = tokenProvider.createToken(authentication, principal);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer "+jwt);
        Cookie cookie = new Cookie(JwtFilter.AUTHORIZATION_HEADER, jwt);
        cookie.setPath("/");
        cookie.setMaxAge(5*(1000*60));

        res.addCookie(cookie);

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }
}
