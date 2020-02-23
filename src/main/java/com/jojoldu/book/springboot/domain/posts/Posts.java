package com.jojoldu.book.springboot.domain.posts;

// p88

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter             // 6 lombok 어노테이션. lombok 은 코드를 단순화시켜 주지만 필수 어노테이션은 아니다. 클래스내 모든 필드의 Getter 메소드를 자동 생성
                    // p92 Entity 클래스에서는 절대 Setter 메소드를 만들지 않는다. 대신 해당 필드의 값 변경이 필요하면 명확히 그 목적과 의도를 나타낼 수 있는 메소드를 추가해야 한다
@NoArgsConstructor  // 5 lombok 어노테이션. lombok 이 기본 생성자 자동추가.
@Entity             // 1 JPA 어노테이션. 테이블과 링크될 클래스임을 나타낸다. 기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍(_)으로 테이블 이름을 매칭한다. SalesManager.java --> sales_manager table
public class Posts {// 실제 DB 테이블과 매칭될 클래스이며 보통 Entity 클래스 라고도 한다. DB 데이터에 작업할 경우 실제 퀴리를 날리기 보다, 이 Entity 클래스의 수정을 통해 작업한다

  @Id   // 2 해당 테이블의 PK 필드
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 3 PK 의 생성규칙을 나타낸다. GenerationType.IDENTITY 옵션을 추가해야 auto-increment 된다.
  private long id;

  @Column(length = 500, nullable = false) // 4 테이블의 칼럼을 나타내며 둗이 선언하지 않더라도 해당 클래스의 필드는 모두 칼럼이 된다. 사용하는 이유는 default 값 이외에 추가로 변경이 필요한 옵션이 있을때 사용. 문자열의 경우 255가 기본. 여기서는 500으로.
  private String title;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String content;

  private  String author;

  // 입력용
  @Builder  // 7 lombok 이 해당 클래스의 빌더 패턴 클래스를 생성. 생성자 상단에 선언시 생성자에 포함된 필드만 빌더에 포함
            //          p93 이 책에서는 생성자 대신에 @Builder 를 통해 제공되는 빌더 클래스를 사용한다. 어느 필드에 어떤 값을 채워야 할지 명확히 하기 위해.
  public Posts(String title, String content, String author) {
    this.title = title;
    this.content = content;
    this.author = author;
  }

  // p112 수정용
  public void update (String title, String content) {
    this.title = title;
    this.content = content;
  }
}
