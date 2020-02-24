package com.jojoldu.book.springboot.web.dto;

// p107

import com.jojoldu.book.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {

  private String title;
  private String content;
  private String author;

  @Builder
  public PostsSaveRequestDto(String title, String content, String author) {
    this.title = title;
    this.content = content;
    this.author = author;
  }

  // p 108 Entity class 와 Controller 에서 쓸 Dto 는 분리해서 사용한다
  public Posts toEntity() { // p108 View layer 와 DB layer 이 역할을 철저히 분리. Controller 에서 결과값을 여러 테이블을 조인해서 줘야 할 경우가 빈번하므로 Entity class 만으로 표현하기가 어렵기 때문.
    return Posts.builder()
      .title(title)
      .content(content)
      .author(author)
      .build();
  }
}
