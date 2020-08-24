package com.ylpitseleh.practice.security_oauth;

import com.ylpitseleh.practice.service_oauth.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.ylpitseleh.practice.security_oauth.SocialType.*;

/**
OAuth2 인증 설정의 가장 핵심되는 부분입니다.
antMatchers 메서드를 이용하여 매칭되는 url를 정의하였습니다.
/facebook, /google URL에서는 권한이 있을 때 url에 접근할 수 있도록 설정했습니다.
login 성공 시 loginSuccess로 실패시, loginFailure로 redirect되게끔 설정하였습니다.
만약 권한이 없을 때 나온 error는 /login으로 가게끔 설정하였습니다.
*/
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().
                antMatchers("/", "/oauth2/**", "/login/**", "/css/**",
                "/images/**", "/js/**", "/console/**", "/favicon.ico/**").permitAll().
                //antMatchers("/facebook").hasAuthority(FACEBOOK.getRoleType()).
                antMatchers("/google").hasAuthority(GOOGLE.getRoleType()).
                antMatchers("/kakao").hasAuthority(KAKAO.getRoleType()).
                antMatchers("/naver").hasAuthority(NAVER.getRoleType())
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .userInfoEndpoint().userService(new CustomOAuth2UserService()) // 네이버 USER INFO의 응답을 처리하기 위한 설정
                .and()
                .defaultSuccessUrl("/loginSuccess") .
                failureUrl("/loginFailure")
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));
    }

    /**
     authenticationEntryPoint에서 스프링에서 제공하는 form login 템플릿이 아닌 /login 메서드에서 제공하는 템플릿으로 화면에 나타나게끔 지정했습니다.
     clientRegistrationRepository 메서드를 통하여 facebook, kakao, google의 인증 정보들이 메모리상에 상주하게끔 설정했습니다.
     CustomUserOAuth2UserService 클래스를 만들어서 설정에 추가했습니다. NAVER에서는 HTTP response body에 유저 정보에 대한 것을 response 속성 안에 id, user_name 등의 유저 정보를 넣기 때문에 id값을 불러오려면 별도의 처리를 해야되기 때문입니다.
     */
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(
            OAuth2ClientProperties oAuth2ClientProperties,
            @Value("${custom.oauth2.kakao.client-id}") String kakaoClientId,
            @Value("${custom.oauth2.kakao.client-secret}") String kakaoClientSecret,
            @Value("${custom.oauth2.naver.client-id}") String naverClientId,
            @Value("${custom.oauth2.naver.client-secret}") String naverClientSecret) {
        List<ClientRegistration> registrations = oAuth2ClientProperties
                .getRegistration().keySet().stream()
                .map(client -> getRegistration(oAuth2ClientProperties, client))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        registrations.add(CustomOAuth2Provider.KAKAO.getBuilder("kakao")
                .clientId(kakaoClientId)
                .clientSecret(kakaoClientSecret)
                .jwkSetUri("temp") .build());

        registrations.add(CustomOAuth2Provider.NAVER.getBuilder("naver")
                .clientId(naverClientId)
                .clientSecret(naverClientSecret)
                .jwkSetUri("temp") .build());
        return new InMemoryClientRegistrationRepository(registrations);
    }

    private ClientRegistration getRegistration(OAuth2ClientProperties clientProperties, String client)
    {
        if("google".equals(client)) {
            OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("google");
            return CommonOAuth2Provider.GOOGLE.getBuilder(client)
                    .clientId(registration.getClientId())
                    .clientSecret(registration.getClientSecret())
                    .scope("email", "profile")
                    .build();
        }
        /*
        if("facebook".equals(client)) {
            OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("facebook");
            return CommonOAuth2Provider.FACEBOOK.getBuilder(client)
                    .clientId(registration.getClientId())
                    .clientSecret(registration.getClientSecret())
                    .userInfoUri("https://graph.facebook.com/me?fields=id,name,email,link")
                    .scope("email") .build();
        }
        */
        return null;
    }
}
