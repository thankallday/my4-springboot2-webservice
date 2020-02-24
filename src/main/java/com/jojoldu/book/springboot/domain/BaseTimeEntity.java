package com.jojoldu.book.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.time.LocalDateTime;

// p121
@Getter
@MappedSuperclass   // 1 JPA Entity 클래스들이 BaseTimeEntity 를 상속할 경우 필드들도 칼럼으로 인식하도록 한다.
@EntityListeners(AuditingEntityListener.class)  // 2 BaseTimeEntity 클래스에 Auditing 기능도 포함시킨다.
public class BaseTimeEntity {

  @CreatedDate  // 3 Entity 가 생성되어 저장될 때 시간이 자동 저장된다.
  private LocalDateTime createDate;

  @LastModifiedDate // 4 조회한 Entity 의 값이 변경될 때 시간이 자동 자동 저장 된다.
  private LocalDateTime modifiedDate;

}
