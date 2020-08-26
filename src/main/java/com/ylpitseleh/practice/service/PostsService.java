package com.ylpitseleh.practice.service;

import com.ylpitseleh.practice.domain.posts.Posts;
import com.ylpitseleh.practice.domain.posts.PostsRepository;
import com.ylpitseleh.practice.web.dto.PostsListResponseDto;
import com.ylpitseleh.practice.web.dto.PostsResponseDto;
import com.ylpitseleh.practice.web.dto.PostsSaveRequestDto;
import com.ylpitseleh.practice.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service 메소드는 트랜잭션과 도메인 간의 순서만 보장해준다.
 * 비즈니스 로직은 Domain으로 처리한다.
 *
 * @RequiredArgsConstructor
 * 생성자를 직접 안 쓰고 롬복 어노테이션을 사용한 이유 : 해당 클래스의 의존성 관계가 변경될 때마다 생성자 코드를 계속해서 수정하는 번거로움을 해결하기 위함.
 */

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    // 게시글 저장
    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }
    // 게시글 수정
    /*
    update는 쿼리 날리는 부분이 없다.
    JPA의 엔티티 매니저가 활성화된 상태로 트랜잭션 안에서 데이터베이스를 가져오면 이 데이터는 영속성 컨텍스트가 유지된 상태이다.
    이 상태에서 해당 데이터의 값을 변경하면 트랜잭션이 끝나는 시점에 해당 테이블에 변경분을 반영한다.
    즉, Entity 객체의 값만 변경하면 별도로 Update 쿼리를 날릴 필요가 없다는 것이다 => 더티 체킹 (Dirty Checking)
    */
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent()); // 업데이트 쿼리가 아니라 값만 바꿔주는건데? 위에 주석 참조.
        return id;
    }
    // 게시글 삭제 
    @Transactional
    public void delete (Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        postsRepository.delete(posts);
    }
    // id로 게시글 찾기
    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }
    // 모든 게시글 불러오기
    @Transactional(readOnly = true) // 트랜잭션 범위는 유지하되, 조회 기능만 남겨두어서 조회 속도가 개선된다.
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new) // .map(posts -> new PostsListResponseDto(posts)) 와 같다.
                .collect(Collectors.toList());
        // postsRepository 결과로 넘어온 Posts의 Stream을 map을 통해 PostsListResponseDto 변환 -> List로 반환하는 메소드.
    }
}