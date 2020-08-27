package com.ylpitseleh.practice.config;

import com.ylpitseleh.practice.config.auth.LoginUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
/**
 * LoginUserArgumentResolver를 스프링에서 인식될 수 있도록 WebMvcConfigurerer에 추가한다.
 * HandlerMethodArgumentResolver는 항상 WebMvcConfigurer의 addArgumentResolvers()를 통해 추가해야 한다.
 * 다른 HandlerMethodArgumentResolver가 필요하다면 같은 방식으로 추가해주면 된다.
 */

// @LoginUser를 파라미터에서 사용하기 위한 환경 구성 (LoginUser.java를 위한 클래스 1.LoginUserArgumentResolver.java 2. WebConfig.java)
@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final LoginUserArgumentResolver loginUserArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserArgumentResolver);
    }
}