package com.jojoldu.book.springboot.web.dto;

// p112 수정 요청 용 dto

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
