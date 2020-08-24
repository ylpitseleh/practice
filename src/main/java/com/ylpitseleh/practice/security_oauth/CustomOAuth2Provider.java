package com.ylpitseleh.practice.security_oauth;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

/**
스프링 부트에서는 google및 facebook에 대한 OAuth2정보를 기본적으로 제공합니다.
하지만 Kakao와 NAVER는 스프링 부트에서 기본적인 정보를 제공하지 않으므로 위와 같이 따로 해당 정보를 제공하는 클래스를 작성하여야 합니다.
 */
public enum CustomOAuth2Provider {

    KAKAO {
        @Override
        public ClientRegistration.Builder getBuilder(String registrationId) {
            ClientRegistration.Builder builder = getBuilder(registrationId, ClientAuthenticationMethod.POST, DEFAULT_LOGIN_REDIRECT_URL);
            builder.scope("profile");
            builder.authorizationUri("https://kauth.kakao.com/oauth/authorize");
            builder.tokenUri("https://kauth.kakao.com/oauth/token");
            builder.userInfoUri("https://kapi.kakao.com/v2/user/me");
            builder.userNameAttributeName("id");
            builder.clientName("Kakao");
            return builder;
        }
    },

    NAVER {
        @Override
        public ClientRegistration.Builder getBuilder(String registrationId) {
            ClientRegistration.Builder builder = getBuilder(registrationId, ClientAuthenticationMethod.POST, DEFAULT_LOGIN_REDIRECT_URL);
            builder.scope("profile");
            builder.authorizationUri("https://nid.naver.com/oauth2.0/authorize");
            builder.tokenUri("https://nid.naver.com/oauth2.0/token");
            builder.userInfoUri("https://openapi.naver.com/v1/nid/me");
            builder.userNameAttributeName("id");
            builder.clientName("Naver");
            return builder;
        }
    };

    private static final String DEFAULT_LOGIN_REDIRECT_URL = "{baseUrl}/login/oauth2/code/{registrationId}";

    protected final ClientRegistration.Builder getBuilder(String registrationId, ClientAuthenticationMethod method, String redirectUri) {
        ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(registrationId);
        builder.clientAuthenticationMethod(method);
        builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
        builder.redirectUriTemplate(redirectUri);
        return builder;
    }

    public abstract ClientRegistration.Builder getBuilder(String registrationId);
}
