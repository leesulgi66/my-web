package com.example.myweb.config.oauth;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo{

    private final String id;
    private final String email;
    private final Map<String, Object> attributes;  // oAuth2User.getAttributes()

    public KakaoUserInfo(String id ,Map<String, Object> attributes, Map<String, Object> kakao_account) {
        this.id = id;
        this.attributes = attributes;
        this.email = (String)kakao_account.get("email");
    }

    @Override
    public String getProviderId() {
        return id;
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getName() {
        return (String) attributes.get("nickname");
    }

    @Override
    public String getNickname() {
        return (String) attributes.get("nickname");
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getProfileImage() {
        return (String) attributes.get("profile_image");
    }
}
