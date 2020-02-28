package com.jojoldu.book.springboot.config.auth.dto;

// p187

import com.jojoldu.book.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;


@Getter
public class SessionUser implements Serializable { //p187 인증된 사용자 정보만 담는 클래스

  private String name;
  private String email;
  private String picture;

  public SessionUser(User user) {
    this.name = user.getName();
    this.email = user.getEmail();
    this.picture = user.getPicture();
  }
}
