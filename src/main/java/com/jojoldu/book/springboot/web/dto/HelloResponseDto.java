package com.jojoldu.book.springboot.web.dto;

// p72

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter                     // 1 선언된 모든 필드의 get 메소드를 생성해 준다
@RequiredArgsConstructor    // 2 선언된 모든 final 필드가 포함된 생성자를 생성해 준다. final 이 없는 필드는 생성자에 포함되니 않는다
public class HelloResponseDto {
  private final String name;
  private final int amount;
}

