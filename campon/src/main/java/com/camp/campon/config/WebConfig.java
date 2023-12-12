package com.camp.campon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration          // 빈 등록 설정 클래스 지정
public class WebConfig {
    
    @Bean               // 빈 등록
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        // BCryptPasswordEncoder            : BCrypt 해시 알고리즘을 사용하여 비밀번호
        // NoOpPasswordEncoder              : 암호화 없이 비밀번호를 저장
        // ...
    }
}
