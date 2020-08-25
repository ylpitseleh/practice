package com.ylpitseleh.practice.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * SessionUser user = (SessionUser) httpSession.getAttribute("user"); 세션값이 필요할 때마다 직접 세션에서 가져와야 함. 같은 코드 반복됨.
 * 이 부분을 메소드 인자로 세션값을 바로 받을 수 있게 변경.
 */
/*
@Target(ElementType.PARAMETER) : 이 어노테이션이 생성될 수 있는 위치를 지정. PARAMETER로 지정했으니 메소드의 파라미터로 선언된 객체에서만 사용할 수 있음.
                                 이 외에도 클래스 선언문에 쓸 수 있는 TYPE등이 있음.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser { //@interface : 이 파일을 어노테이션 클래스로 지정. LoginUser라는 이름을 가진 어노테이션이 생성되었다고 보면 됨.
}