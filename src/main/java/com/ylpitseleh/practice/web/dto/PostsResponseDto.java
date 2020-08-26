package com.ylpitseleh.practice.web.dto;

import com.ylpitseleh.practice.domain.posts.Posts;
import lombok.Getter;
// id로 게시글 찾기 (findById)용 DTO
@Getter
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;

    /*
    PostsResponseDto는 Entity의 필드 중 일부만 사용하므로 생성자로 Entity를 받아 필드에 값을 넣는다.
    굳이 모든 필드를 가진 생성자가 필요하진 않으므로 Dto는 Entity를 받아 처리한다.

    파라미터에 Long id, String title 이런식으로 해도 되긴 하지만 이게 더 번거로움.
     */
    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}