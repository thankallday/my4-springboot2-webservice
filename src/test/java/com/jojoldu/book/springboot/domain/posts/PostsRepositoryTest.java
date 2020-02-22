package com.jojoldu.book.springboot.domain.posts;


// p95


import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest               // 별다른 설정 없이 @SpringBoot 를 사용할 경우 H2 데이타베이스를 자동으로 실행해 준다. See application.properties to log actual DMLs
public class PostsRepositoryTest {

  @Autowired
  PostsRepository postsRepository;

  @After    // 1 JUnit 에서 단위 테스트가 끝날 때마다 수행되는 메소드를 지정
            //   보통은 배포전 전체 테스트를 수행할 때 테스트간 테이터 침범을 막기위해 사용한다
            //   여러 데스트가 동시에 수행되면 테스트용 DB인 H2에 데이터가드대로 남아 있어 다음 테스트 실행시 실패할 수 가 있음
  public void cleanup() {
    postsRepository.deleteAll();
  }

  @Test
  public void 게시글저장_블러오기() {

    // given
    String title = "테스트 게시글";
    String content = "데스트 본문";

    // p99 application.properties to log actual DMLs
    postsRepository.save(Posts.builder()      // 2 table posts 에 insert/update query 를 실행한다. id가 있다면 update, 없다면 insert 가 실행된다
      .title(title)
      .content(content)
      .author("jojoldu@gmail.com")
      .build());

    //when
    List<Posts> postsList = postsRepository.findAll(); // 3 데이블 posts 에 있는 모든 데이타를 조회한다

    //then
    Posts posts = postsList.get(0);
    assertThat(posts.getTitle()).isEqualTo(title);
    assertThat(posts.getContent()).isEqualTo(content);
  }

}
