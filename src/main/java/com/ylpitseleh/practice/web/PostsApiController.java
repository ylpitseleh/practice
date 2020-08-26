package com.ylpitseleh.practice.web;
import com.ylpitseleh.practice.service.PostsService;
import com.ylpitseleh.practice.web.dto.PostsListResponseDto;
import com.ylpitseleh.practice.web.dto.PostsResponseDto;
import com.ylpitseleh.practice.web.dto.PostsSaveRequestDto;
import com.ylpitseleh.practice.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor // final이 선언된 모든 필드를 인자값으로 하는 생성자를 생성해준다.
@RestController
public class PostsApiController {

    private final PostsService postsService;

    // 게시글 등록 페이지의 등록 버튼
    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }
    // 게시글 수정 페이지의 수정 완료 버튼
    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        // 파라미터에 @RequestBody PostsUpdateRequestDto 쓸 수 있는건 AJAX로부터 title, content 데이터를 받았기 때문이다.
        return postsService.update(id, requestDto);
    }
    // 게시글 수정 페이지의 삭제 버튼
    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }
    // Update, Delete 할 때 id(게시글 번호 매김. PK)로 게시글 찾아서 수정 or 변경
    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }
    // 첫 화면에서 모든 게시글 불러오기
    @GetMapping("/api/v1/posts/list")
    public List<PostsListResponseDto> findAll() {
        return postsService.findAllDesc();
    }
}