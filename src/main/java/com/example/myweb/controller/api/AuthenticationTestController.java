package com.example.myweb.controller.api;

import com.example.myweb.config.auth.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class AuthenticationTestController {

    //※ Authentication 객체는 두개의 type을 가질 수 있다. UserDetails, OAuth2User

    @GetMapping("/test/login")
    public @ResponseBody String testLogin(Authentication authentication, @AuthenticationPrincipal PrincipalDetails userDetails) {
        // 세션 정보에 접근하는 두가지 방법
        // 1. Authentication 객체에 접근해 (PrincipalDetails)다운 캐스팅으로 PrincipalDetails를가져온다.
        log.info("/test/login =================================================");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal(); // PrincipalDetails 는 UserDetails 를 상속했기 때문에 가능함.
        log.info("authentication : {}", principalDetails.getUser());

        // 2. @AuthenticationPrincipal 어노테이션을 사용해 PrincipalDetails를 가져온다.
        log.info("userDetails : {}", userDetails.getUser());
        return "세선 정보 확인하기";
    }

    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOauthLogin(Authentication authentication, @AuthenticationPrincipal OAuth2User oAuth2User) {
        // 세션 정보에 접근하는 두가지 방법
        // 1. Authentication 객체에 접근해 (OAuth2User)다운 캐스팅으로 PrincipalDetails를가져온다.
        log.info("/test/login =================================================");
        OAuth2User principalDetails = (OAuth2User) authentication.getPrincipal();
        log.info("authentication : {}", principalDetails.getAttributes());

        // 2. @AuthenticationPrincipal 어노테이션을 사용해 OAuth2User를 가져온다.
        log.info("userDetails : {}", oAuth2User.getAttributes());
        return "OAuth 정보 확인하기";
    }
}
