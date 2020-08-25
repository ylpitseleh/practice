package com.ylpitseleh.practice.config.auth.dto;

import com.ylpitseleh.practice.domain.user.User;
import lombok.Getter;

import java.io.Serializable;
// SessionUser에는 인증된 사용자 정보만 필요하다. 그 외에 필요한 정보들은 없으니 name, email, picture만 필드로 선언한다.
/*
User 클래스와 별개로 SessionUser 클래스를 만들어야 하는 이유
User 클래스가 엔티티이기 때문에 언제 다른 엔티티와의 관계가 형성될지 모른다.
예를 들어 @OneToMany, @ManyToOne 등 자식 엔티티를 갖고 있다면 직렬화 대상에 자식들까지 포함되니 성능 이슈, 부수 효과가 발생할 확률이 높다.
따라서 직렬화 기능을 가진 세션 Dto를 하나 추가로 만드는 것이 이후 운영 및 유지보수 때 많은 도움이 된다.
 */
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}