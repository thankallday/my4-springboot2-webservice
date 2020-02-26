package com.jojoldu.book.springboot.web;

// p132 mustache 로 화면 구성하기 - 기본 페이지 만들기

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

  @GetMapping("/")
  public String index() {

    return "index";      // http://localhost:8080/ 에 대한 응답으로 index.mustache 보내 스프링 부트가 open 하도록 한다.
  }

  // 138
  @GetMapping("/posts/save")
  public String postsSave() {
    return "posts-save"; // call posts-save.mustache
  }
}
