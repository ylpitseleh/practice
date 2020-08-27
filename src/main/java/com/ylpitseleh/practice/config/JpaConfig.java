package com.ylpitseleh.practice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
/*
HelloControllerTest에서 @EnableJpaAuditing이 @SpringBootApplication과 함께 있다보니 @WebMvcTest에서도 스캔하게 되어서 에러가 발생한다.
따라서 @EnableJpaAuditing과 @SpringBootApplication 둘을 분리한다.
 */
@Configuration
@EnableJpaAuditing
public class JpaConfig {
}
