package com.ylpitseleh.practice.web;

import com.ylpitseleh.practice.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/*
WebMvcTest : 여러 스프링 어노테이션 중, Web에 집중할 수 있는 어노테이션이다.
선언할 경우 @Controller, @ControllerAdvice등을 사용할 수 있다. 단, @Service, @Component, @Repository 등은 사용할 수 없다.
여기서는 Controller만 사용하기 때문에 선언한다.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
public class HelloControllerTest {

    @Autowired // 스프링이 관리하는 Bean을 주입받는다.
    private MockMvc mvc; // 웹 API를 테스트할 때 사용. 스프링 MVC 테스트의 시작점. 이 클래스를 통해 HTTP GET, POST 등에 대한 API 테스트를 할 수 있다.

    @WithMockUser(roles="USER") // 가짜로 인증된 사용자 생성
    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello")) // MockMvc를 통해 /hello 주소로 HTTP GET 요청을 한다.
                .andExpect(status().isOk()) // 200, 404, 500 등의 상태를 검증한다. (여기선 200인지 아닌지 검증)
                .andExpect(content().string(hello));
    }

    @WithMockUser(roles="USER")
    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                        .param("name", name)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
        /*
         * jsonPath : JSON 응답값을 필드별로 검증할 수 있는 메소드. $을 기준으로 필드명을 명시.
         */
    }


}