package com.jojoldu.book.springboot.config.auth;

/*
    p195
    IndexController.index(Model model) 메소드에서
      SessionUser user = (SessionUser) httpSession.getAttribute("user");
    같은 코드가 계속 반복되므로, 이 부분을 메소드 인자로 세션 값을 바로 받을 수 있도록 변경
    @LoginUser 사용하도록 개선
*/


import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)  // 1 이 어노테이션이 생성될 수 있는 위치를 지정. PARAMETER 로 지정했으므로 메소드의 파라미터로 선언된 객체에서만 사용할 수 있다
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {  // 2 이 파일을 어노테이션 클래스로 지정한다. 즉, LoginUser 라는 어노테이션이 생성된다.

}
