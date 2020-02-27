package com.jojoldu.book.springboot.web;

// p132 mustache 로 화면 구성하기 - 기본 페이지 만들기

import com.jojoldu.book.springboot.service.PostsService;
import com.jojoldu.book.springboot.web.dto.PostsListResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

  private final PostsService postsService; //p 150

  @GetMapping("/")
  public String index(Model model) {  // p150 1 Model: 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있다.
    model.addAttribute("posts", postsService.findAllDesc()); // findAllDesc() 결과를 "posts" attribute 로 index.mustache 에 전달한다.
    return "index";      // http://localhost:8080/ 에 대한 응답으로 index.mustache 보내 스프링 부트가 open 하도록 한다.
  }

  // 138
  @GetMapping("/posts/save")
  public String postsSave() {
    return "posts-save"; // call posts-save.mustache
  }

  // p155
  @GetMapping("/posts/update/{id}")
  public String postsUpdate(@PathVariable Long id, Model model)
  {
    PostsResponseDto dto = postsService.findById(id);
    model.addAttribute("post", dto);
    return "posts-update";
  }
}
