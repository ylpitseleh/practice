package com.ylpitseleh.practice.domain.posts;

import com.ylpitseleh.practice.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 절대로 Entity 클래스를 request / response 클래스로 사용해서는 안 된다.
 * Entity 클래스는 데이터베이스와 맞닿은 핵심 클래스이다. Entity 클래스를 기준으로 테이블이 생성되고, 스키마가 변경된다.
 * 화면 변경은 아주 사소한 기능 변경인데, 이를 위헤 테이블과 연결된 Entity 클래스를 변경하는 것은 너무 큰 변경이다.
 *
 * View Layer와 DB Layer의 역할 분리를 철저하게 하는 게 좋다.
 * 실제로 Controller에서 결괏값으로 여러 테이블을 조인해서 줘야 할 경우가 빈번하므로 Entity 클래스만으로 표현하기가 어려운 경우가 많다.
 */

//@NoArgsConstructor : 기본 생성자 자동 추가, public Posts(){} 와 같은 효과
@Getter
@NoArgsConstructor // JPA에서는 프록시를 생성을 위해서 기본 생성자 하나를 반드시 생성해야 한다. (구글링으로 찾은 내용)
@Entity // 테이블과 링크될 클래스임을 나타냄
public class Posts extends BaseTimeEntity {

    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 생성 규칙. IDENTITY 옵션을 추가해야 auto_increment가 된다.
    private Long id;

    /*
    @Column : 테이블의 칼럼을 나나태며 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 칼럼이 된다.
    기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용한다.
    문자열의 경우 VARCHAR(255)가 기본값인데, 사이즈를 500으로 늘리고 싶거나(ex. title), 타입을 TEXT로 변경하고 싶거나(ex. content) 등의 경우에 사용된다.
     */
    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    /*
    @Builder : 해당 클래스의 빌더 패턴 클래스를 생성, 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
    생성자 대신 사용.
    어느 필드에 어떤 값을 채워야 할지 명확하게 인지할 수 있다.
        ex) public Example(String a, String b)를 new Example(b, a) 방지.
     */
    @Builder 
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}