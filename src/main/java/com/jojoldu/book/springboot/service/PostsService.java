package com.jojoldu.book.springboot.service;

// p105

import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.posts.PostsRepository;
import com.jojoldu.book.springboot.web.dto.PostsListResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/*
  p106 final 이 선언된 모든 필드를 인자 값으로 하는 생성자를 lombok 의  RequiredArgsConstructor 가 대신해 준다.
  해당 클래스의 의존성 관계가 변경될 때마다 생성자 코드를 걔속해서 수정해야 하는 번거로움을 해결아기 위해서다.
  lombok 어노테이션을 쓰면 해당 컨트롤러에 새로운 소비스를 추가하거나, 기존 컴포넌트를 제거하는 등의 상황이 발생해도 생성자 코드는 전혀 손대지 않아도 된다.
 */
@RequiredArgsConstructor
@Service
public class PostsService {

  private final PostsRepository postsRepository;

  @Transactional // p105 DB insert
  public Long save(PostsSaveRequestDto requestDto) {
    return postsRepository.save(requestDto.toEntity()).getId();
  }

  @Transactional // p113
  public Long update(Long id, PostsUpdateRequestDto requestDto) {
    Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
    posts.update(requestDto.getTitle(), requestDto.getContent());
    return id;
  }

  @Transactional // p113
  public PostsResponseDto findById(Long id) {
    Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
    return new PostsResponseDto(posts);
  }

  @Transactional(readOnly = true)
  public List<PostsListResponseDto> findAllDesc() {
    return postsRepository.findAllDesc()
          .stream()
          .map(PostsListResponseDto::new)
      /*
        p149 .map(posts --> new PostsListResponseDto(posts)) 와 같다
        postsRepository 결과로 넘어온 Posts 의 Stream 을
        Map 을 통해 PostsListResponseDto 변환 -> List 로 반환하는 메소드
       */
          .collect(Collectors.toList());
  }

  // p159
  @Transactional
  public void delete(Long id) {
    Posts posts = postsRepository.findById(id)
      .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
    postsRepository.delete(posts); // p159 또는 postsRepository.deleteById(id); 가능하다
  }

}
