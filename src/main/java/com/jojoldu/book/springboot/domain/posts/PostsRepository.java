package com.jojoldu.book.springboot.domain.posts;

/*
  p94
  Posts 클래스로 DB에 접근하게 해 줄 클래스
  보통 Dao 라고 불리는 DB layer 접근자.
  JPA 에선 Repository 라고 부르며 인터페이스로 생성한다. JpaRepository <Entity 클래스, PK 타입>을 상속하면 기본적인 CRUD 메소드가 자동으로 생성된다
  @Repository 를 추가할 필요가 없다

  주의할 점은 Entity 클래스와 기본 Entity Repository 는 함께 위치해야 한다 --> 도메인 패키지에서 함께 관리한다.
 */

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository <Posts, Long> {
}
