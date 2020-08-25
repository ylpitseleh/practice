package com.ylpitseleh.practice.config.auth.dto;
import com.ylpitseleh.practice.domain.user.Role;
import com.ylpitseleh.practice.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    /**
     * ISSUE
     * 책엔 String userNameAttributeName 이라고 나와있는데 이렇게하면 윈도우 환경 변수 때문에 (cmd에서 echo %userName% 해보면 내 이름 나옴)
     * 윈도우 로컬에 저장된 내 이름이 나온다. 변수명을 userMyNameAtributeName으로 변경했다.
     */
    // OAuth2User에서 반환하는 사용자 정보는 Map이기 때문에 값 하나하나를 변환해야 한다.
    public static OAuthAttributes of(String registrationId, String userMyNameAttributeName, Map<String, Object> attributes) {
        if("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        }

        return ofGoogle(userMyNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userMyNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userMyNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userMyNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userMyNameAttributeName)
                .build();
    }

    /*
    User 엔티티를 생성한다.(생성시점 : 처음 가입할 때)
    가입할 때의 기본 권한을 GUEST로 주기 위해 role 빌더값에는 Role.GUEST를 사용한다.
    OAuthAttributes 클래스 생성이 끝났으면 같은 패키지에 SessionUser 클래스를 생성한다.
    */
    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }
}