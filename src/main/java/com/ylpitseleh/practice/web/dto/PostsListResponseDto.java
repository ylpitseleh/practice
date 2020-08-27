package com.ylpitseleh.practice.web.dto;
import com.ylpitseleh.practice.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

// 모든 게시글 불러오기(findAll)용 DTO
@Getter
public class PostsListResponseDto {
    private Long id;
    private String title;
    private String author;
    private LocalDateTime modifiedDate;

    public PostsListResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.modifiedDate = entity.getModifiedDate();
    }
}