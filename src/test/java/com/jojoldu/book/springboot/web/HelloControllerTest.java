package com.jojoldu.book.springboot.web;

// p61

import com.jojoldu.book.springboot.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)            // 1 테스트를 진핼할때 JUnit 에 내장된 실행자 외애 다른 실행자를 실행시킨다.. 스프링 부트 테스트와 JUnit 사이에 연결자 역할을 한다.
@WebMvcTest(controllers = HelloController.class    // 2 여러 스프링 테스트 어노테이션 중 Web(Spring MVC)에 집중할 수 잇는 어노테이션이다. 선언할 경우 @Controller, @ControllerAdvice 를 사용할 수 있다.
        /* p219 hello가_리턴된다() 테스트 에러 --> No qualifying bean of type 'com.jojoldu.book.springboot.config.auth.CustomOAuth2UserService'
           p220 @WebMvcTest 는 CustomOAuth2UserService 를 스캔하지 않기 때문에 위의 에러가 테스트할 때 발생한다
                @WebMvcTest 는 WebSecurityConfigurerAdapter, WebMvcConfigurer 를 비롯한 @ControllerAdvice, @Controller 를 읽는다.
                즉, @Repository, @Service, @Component 는 스캔 대상이 아니다.
                해서, SecurityConfig 는 읽었지만, SecurityConfig 를 생성하기 위해 필요한 CustomOAuth2UserService 는 읽을 수가 없어서 에러가 밸생.
                이 문제를 해결하기 위햐 스캔대상에서 SecurityConfig 를 아래 처럼 제거한다
         */
        , excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
       }
)
public class HelloControllerTest {

  @Autowired              // 3 스프링이 관리하는 bean 을 주입 받는다
  private MockMvc mvc;    // 4 웹 API 를 테스트 할 때 사용. 스프링 MVC 테스트의 시작점. 이 클래스를 통해 HTTP GET, POST 등에 대한 API 테스트를 할 수 있다.

  @WithMockUser(roles = "USER") // p220
  @Test
  public void hello가_리턴된다() throws Exception {

    String hello = "hello";

    mvc.perform(get("/hello"))      // 5 MockMvc 를 통행 /hello 주소로 HTTP GET 요청을 한다.
      .andExpect(status().isOk())    // 6 mvc.perform 의 결과를 검증한다 OK 200 인지 검증 한다
      .andExpect(content().string(hello));  // 7 응답 본문의 내용을 검증 한다
  }

  // p76
  @WithMockUser(roles = "USER") // p220
  @Test
  public void helloDto가_리턴된다() throws Exception {
    String name = "hello";
    int amount = 1000;

    mvc.perform(
      get("/hello/dto") // calls @GetMapping("/hello/dto")
        .param("name", name) // 1 API 테스할 때 사용될 요청 파라미터를 설정한다, 단 값은 String 만 허용된다
        .param("amount", String.valueOf(amount))) // 문자로 변환해야 한다
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.name", is(name))) // 2 JSON 응답 값을 필드별로 검증할 수 있는 메소드 $를 기준으로 필드명을 명시한다
      .andExpect(jsonPath("$.amount", is(amount))); // 여기서는 name 과 amount 를 검증하니 $.name, $.amount 로 검증한다.


  }

}
