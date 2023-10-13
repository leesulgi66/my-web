package com.example.myweb.config.auth;

import com.example.myweb.config.oauth.GoogleUserInfo;
import com.example.myweb.config.oauth.NaverUserInfo;
import com.example.myweb.config.oauth.OAuth2UserInfo;
import com.example.myweb.model.User;
import com.example.myweb.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    //구글로 부터 받은 userRequest 데이터에 대한 후처리용 함수

    @Autowired
    UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("getClientRegistration : "+userRequest.getClientRegistration());
        log.info("getAccessToken : "+userRequest.getAccessToken().getTokenValue());
        log.info("getClientName : "+userRequest.getClientRegistration().getClientName()); // google

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("getAttributes : "+oAuth2User.getAttributes()); // loadUser함수로 user정보를 받아올 수 있다.  ex: {sub=1234, name=오5리, given_name=5리, family_name=오, picture=https//..., email=leesulgi66@naver.com, email_verified=true, locale=ko}

        OAuth2UserInfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            log.info("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            log.info("네이버 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo((Map)(oAuth2User.getAttributes().get("response")));
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            log.info("카카오 로그인 요청");
        }

        assert oAuth2UserInfo != null;
        User user = userService.OauthUserSave(oAuth2UserInfo);

        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }
}
