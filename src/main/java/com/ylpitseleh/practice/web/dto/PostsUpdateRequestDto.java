package com.ylpitseleh.practice.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
// 게시글 수정(update)용 DTO
@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {
    private String title;
    private String content;

    @Builder
    public PostsUpdateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}