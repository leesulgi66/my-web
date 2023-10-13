package com.example.myweb.config.oauth;

public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    String getName();
    String getNickname();
    String getEmail();
    String getProfileImage();
}
