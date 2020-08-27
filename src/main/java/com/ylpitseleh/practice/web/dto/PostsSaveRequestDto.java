package com.ylpitseleh.practice.web.dto;

import com.ylpitseleh.practice.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
// 게시글 저장(save)용 DTO
@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity() { // PostsSaveRequestDto를 Posts화 해서 DB에 넣어줘야 하니까 toEntity 인듯?
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }

}