package com.jojoldu.book.springboot.web;

/*
    p108
    Api controller 를 테스트 하는데 HelloController 와 달리 @WebMvcTest 를 사용하지 않았다.
    @WebMvcTest 의 경우 JPA 기능이 작동하지 않기 때문인데,
    Controller 와 ControllerAdvice 등 외부 연동과 관련된 부분만 활성화 되니,
    지금 같이 JPA 기능까지 한번에 테스트할 때는 @springBootTest 와 TestRestTemplate 을 사용하면 된다
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.posts.PostsRepository;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

  @Autowired
  private WebApplicationContext context; // p217 @SpringBootTest 에서 MockMvc 를 사용하는 방법

  private MockMvc mvc; // p217 @SpringBootTest 에서 MockMvc 를 사용하는 방법


  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private PostsRepository postsRepository;

  @Before // p217 @SpringBootTest 에서 MockMvc 를 사용하는 방법 추가. 매번 테스트가 시작되기 전에 MockMvc 인스턴스를 생성한다.
  public void setup() {

    mvc = MockMvcBuilders.webAppContextSetup(context)
      .apply(springSecurity())
      .build();
  }

  @After
  public void tearDown() throws Exception {
    postsRepository.deleteAll();
  }

  /*
      p108 등록 테스트
      p216 1 인증된 모의(가짜) 사용자를 만들어서 사용한다. roles 에 권한을 추가할 수 있다. 즉, 이 어노테이션으로 ROLE_USER 권한을 가진 사용자가 API 를 요청하는 것과 동일한 효과를 가지게 된다.
             @WithMockUser 는 MockMvc 에서만 작동하기 때문에 실제 작동하지 않는다.
             현재 @SpringBootTest 로만 되어있고, MockMvc 는 전혀 사용하지 않고 있다.
   */
  @Test
  @WithMockUser(roles = "USER") // p216 기존 테스트에 시큐리티 적용하기 - 임의 사용자 인증을 추가
  public void Posts_등록된다() throws Exception {
    // given
    String title = "title";
    String content = "content";
    PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
      .title(title)
      .content(content)
      .author("author")
      .build();
    String url = "http://localhost:" + port + "api/v1/posts";

    // when
    //ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class); p217 기존 테스트에 시큐리티 적용하려면 불필요
    mvc.perform(post(url) // p218 3 생성된 MockMvc 를 통해 API 테스트를 한다. 본문(Body) 영역은 문자열로 표현하기 위해 ObjectMapper 를 통해 문자열 JSON 으로 변환한다.
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .content(new ObjectMapper().writeValueAsString(requestDto)))
      .andExpect(status().isOk());

    // then
    //assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    //assertThat(responseEntity.getBody()).isGreaterThan(0L);
    // p218 기존 테스트에 시큐리티 적용하려면 위의 문장들은 필요없다.
    List<Posts> all = postsRepository.findAll();
    assertThat(all.get(0).getTitle()).isEqualTo(title);
    assertThat(all.get(0).getContent()).isEqualTo(content);
  }

  // p114 수정 테스트
  @Test
  @WithMockUser(roles = "USER") // p216 기존 테스트에 시큐리티 적용하기 - 임의 사용자 인증을 추가
  public void Posts_수정된다() throws Exception {
    // given
    Posts savedPosts = postsRepository.save(Posts.builder()
      .title("title")
      .content("content")
      .author("author")
      .build());
    Long updateId = savedPosts.getId();
    String expectedTitle = "title2";
    String expectedContent = "content2";

    PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
      .title(expectedTitle)
      .content(expectedContent)
      .build();
    String url = "http://localhost:" + port + "/api/v1/posts/"  + updateId;

    //HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);  // p218 기존 테스트에 시큐리티 적용하려면 불필요

    // when
    // ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class); p218 기존 테스트에 시큐리티 적용하려면 불필요
    mvc.perform(put(url) // p218 3
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .content(new ObjectMapper().writeValueAsString(requestDto)))
      .andExpect(status().isOk());

    // then
    // assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    // assertThat(responseEntity.getBody()).isGreaterThan(0L);
    // p218 기존 테스트에 시큐리티 적용하려면 위의 문장들은 필요없다.
    List<Posts> all = postsRepository.findAll();
    assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
    assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
  }
}
