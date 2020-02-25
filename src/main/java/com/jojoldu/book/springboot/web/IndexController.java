package com.jojoldu.book.springboot.web;

// p132 mustache 로 화면 구성하기 - 기본 페이지 만들기

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

  @GetMapping("/")
  public String index() {

    return "index";
  }
}
