package com.ylpitseleh.practice.web;

import com.ylpitseleh.practice.config.auth.LoginUser;
import com.ylpitseleh.practice.config.auth.dto.SessionUser;
import com.ylpitseleh.practice.service.PostsService;
import com.ylpitseleh.practice.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*
페이지에 관련된 컨트롤러는 모두 IndexController를 사용한다.
 */
@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    // 첫 화면 (세션값이 없으면 로그인 창, 있으면 내 이름 Display. / 게시글 목록 Display)
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) { // // @LoginUser를 파라미터에서 사용하기 위한 환경 구성 (LoginUser.java를 위한 클래스 1.LoginUserArgumentResolver.java 2. WebConfig.java)
        /*
        Model : 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있다.
        여기서는 postsService.findAllDesc()로 가져온 결과를 posts로 index.mustache에 전달한다.
         */
        /*
        @LoginUser SessionUser user : 기존에 httpSession.getAttribute("user")로 가져오던 세션 정보 값이 개선되었다.
        이제는 어느 컨트롤러든지 @LoginUser만 사용하면 세션 정보를 가져올 수 있게 되었다.
         */
        model.addAttribute("posts", postsService.findAllDesc()); // index.mustache에 {{#posts}} posts라는 List를 순회하면서 게시글 목록을 보여준다.
        if (user != null) { // 세션에 저장된 값이 있을 때만 model에 userName으로 등록한다. 세션에 저장된 값이 없으면 로그인 버튼이 보인다.
            model.addAttribute("userMyName", user.getName());
        }
        return "index"; // index.mustache로
    }
    // 게시글 등록 페이지
    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }
    // 게시글 수정 페이지
    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}