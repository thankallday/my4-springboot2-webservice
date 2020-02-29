package com.jojoldu.book.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// p222
@Configuration
@EnableJpaAuditing  // JPA Auditing 활성화 p222 @WebMvcTest 는 일반적인 @Configuration 은 스캔하지 않는다.
public class JpaConfig {}
