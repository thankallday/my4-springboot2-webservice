package com.jojoldu.book.springboot.web;

// p105


import com.jojoldu.book.springboot.service.PostsService;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

  private final PostsService postsService;

  // p105 등록용(insert)
  @PostMapping("/api/v1/posts")
  public Long save(@RequestBody PostsSaveRequestDto requestDto) {

    return postsService.save(requestDto);

  }


}
