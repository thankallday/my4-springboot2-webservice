package com.jojoldu.book.springboot.config;

// p198 생성된 LoginUserArgumentResolver 를 스프링에서 인식될 수 있도록 WebMvcConfigurer 에 추가하는 클래스

import com.jojoldu.book.springboot.config.auth.LoginUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

  private final LoginUserArgumentResolver loginUserArgumentResolver;

  // p199 HandlerMethodArgumentResolver 는 항상 WebMvcConfigurer 의 addArgumentResolvers() 를 통해 추가해야 한다.
  // 다른 HandlerMethodArgumentResolver 기 필요하면, 같은 방식으로 추가한다
  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolver) {
    argumentResolver.add(loginUserArgumentResolver);
  }

}
