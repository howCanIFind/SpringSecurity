package com.example.securityoauth2prac.oauth2;

import lombok.Getter;

import java.util.Map;
import java.util.function.Function;

@Getter
public enum ProviderType {
    KAKAO("KAKAO", KakaoOAuth2UserInfo::new);

    private final String providerType;
    private final Function<Map<String, Object>, OAuth2UserInfo> oAuth2UserInfoFactory;

    ProviderType(String providerType,
                 Function<Map<String, Object>, OAuth2UserInfo> oAuth2UserInfoFactory) {
        this.providerType = providerType;
        this.oAuth2UserInfoFactory = oAuth2UserInfoFactory;
    }

    public OAuth2UserInfo getOauth2UserInfo(
            Map<String, Object> attributes) {
        return getOAuth2UserInfoFactory().apply(attributes);
    }
}
