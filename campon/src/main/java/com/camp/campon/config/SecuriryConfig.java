package com.camp.campon.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.camp.campon.security.CustomAccessDeniedHandler;
import com.camp.campon.security.CustomUserDetailsService;
import com.camp.campon.security.LoginSuccessHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration                  // 설정 클래스
@EnableWebSecurity              // 해당 클래스를 스프링 시큐리티 설정 빈으로 등록
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
// @EnableGlobalMethodSecurity
// prePostEnabled = true -> @PreAuthorize, @PostAuthorize 어노테이션 사용 활성화} 
// securedEnabled = true -> @Secured 어노테이션 사용 활성화
// 🔐 @PreAuthorize    : 메소드 실행 전에 인가 설정
// 🔐 @PostAuthorize   : 메소드 실행 후에 대한 인가 설정
// 🔐 @Secured         : 메소드 실행에 대한 인가(권한) 설정
public class SecuriryConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;                        // 비밀번호 암호화 객체

    @Autowired
    private DataSource dataSource;                                  // appllication.properties 에 정의한 데이터 소스를 가져오는 객체

    // 스프링 시큐리티 설정 메소드
    /**
     * 🔰 인증/인가처리
     * 🔰 로그인 설정
     * 🔰 로그아웃 설정
     * 🔰 자동 로그인 설정
     * 🔰 예외 처리
     * 🔰 CSRF 방지 기능 설정
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 스프링 시큐리티 설정
        log.info("스프링 시큐리티 설정...");

        // 인증 & 인가
        // ✅ 인증 (Authentication)
        // : 등록 된 사용자인지 확인하여 입증

        // ✅ 인가 (Authorization)
        // : 사용자의 권한을 확인하여 권한에 따라 자원의 사용범위를 구분하여 허락하는 것

        // 인가 처리

        // 람다식
        http.authorizeRequests((authorize) -> authorize                                    // 인가 설정
                                // anMatchers("자원 경로")                   - 인가에 대한 URL 경로를 설정
                                // permitAll()                              - 모든 사용자 허용
                                // hasAnyRole()                             - 여러 권한에 대한 허용
                                // hasRole()                                - 단일 권한에 대한 허용
                                // static 밑에 경로
                                .antMatchers("/css/**","/js/**","/img/**").permitAll()          // static/~ 정적자원 인가 처리                      
                                //url 경로
                             //   .antMatchers("/admin/**").hasAnyRole("ADMIN", "SELL")
                               // .antMatchers("/**").permitAll()                        
                                // .antMatchers("/user/order").permitAll()
                                // .antMatchers("/admin/**").hasRole("ADMIN")
                                // anyRequest()         : 모든 요청을 지정
                                // authenticated()      : 인증된 사용자만 허용
                               // .anyRequest().authenticated()
                                );

        // 빌더 패턴
        // http.authorizeRequests()                                    // 인가 설정
        //                 // anyMatchers("자원 경로")                   - 인가에 대한 URL 경로를 설정
        //                 // permitAll()                              - 모든 사용자 허용
        //                 // hasAnyRole()                             - 여러 권한에 대한 허용
        //                 // hasRole()                                - 단일 권한에 대한 허용
        //                 .antMatchers("/").permitAll()                        
        //                 // .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
        //                 // .antMatchers("/admin/**").hasRole("ADMIN")
        // ;

        // 로그인 설정
        // http.formLogin().loginPage("/user/login");       // 지정된 경로의 로그인 페이지로 이동
        http.formLogin((login) -> login                   
            .defaultSuccessUrl("/")             // 로그인 성공 시, URL : "/"(기본값)
            //(수정) 로그인페이지 경로 수정했슴
            .loginPage("/user/login")                        // 커스텀 로그인 페이지 지정 (default:/login)
            .loginProcessingUrl("/loginPro")   // 커스텀 로그인 요청 처리 경로 지정 (default:/login)
            //(수정) 파라미터이름을 수정했슴다.
            .usernameParameter("userId")            // 아이디 요청 파라미터 이름 설정 (default:username)
            .passwordParameter("userPw")            // 비밀번호 요청 파라미터 이름 설정 (default:password)
            .successHandler( authenticationSuccessHandler() )     // 로그인 성공 처리 빈을 지정
            .permitAll()                                          // 로그인 폼은 모든 사용자에게 허용
        );
        
        // 로그아웃 설정
        http.logout((logout) -> logout
            // .logoutSuccessUrl("/login")    // 로그아웃 성공 시, URL : "/login?logout"(기본값)
            // .logoutUrl("/logout")                 // 로그아웃 요청 처리 경로 지정 (default:logout)
            // 쿠키 삭제
            //.deleteCookies("remember-id", "remember-me", "JSESSIONID")
            .invalidateHttpSession(true)    // 세션 무효화
            .permitAll()
        );

        // 자동 로그인 설정
        http.rememberMe((rememberme) -> rememberme
            . key("dldydtjd9")
            // DataSourece 가 등록된 PersistentRepository 토큰정보 객체
            .tokenRepository( tokenRepository() )
            // 토큰 유효기간 : (초/분/시/일) 7일 (초 단위)
            .tokenValiditySeconds( 60 * 60 * 24 * 7 )
        );

        //주석처리함. 
        // 인증 예외 처리
        // http.exceptionHandling((exception) -> exception
        //     // .accessDeniedPage("/exception")
        //     .accessDeniedHandler( accessDeniedHandler() )
        // );


        // CSRF 방지 기능 비활성화 설정
        // http.csrf().disable(); // 기본값 enable();
    }

    
    // 사용자 인증 관리 메소드
    /**
     * 🔰 인메모리 방식
     * 🔰 JDBC 방식
     * 🔰 사용자 정의 방식 (UserDetailService)
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 🔰인증 방식 : 사용자 정의 인증 (UserDetails)
        auth.userDetailsService( customUserDetailsService() )
        // 비밀번호 암호화 방식 지정 - BCryptPasswordEncoder 또는 NoOpPasswordEncoder
        .passwordEncoder( passwordEncoder );
        

    }

        // PersistentRepository 토큰 정보 객체 - 빈 등록
        @Bean
        public PersistentTokenRepository tokenRepository() {
            // JdbcTokenRepositoryImpl : 토큰 저장 데이터 베이스를 생성
            JdbcTokenRepositoryImpl repositoryImpl = new JdbcTokenRepositoryImpl();
            repositoryImpl.setDataSource(dataSource);   // 토큰 저장소를 사용하는 데이터 소스 지정
            
            return repositoryImpl;
        }

        // 인증 성공 처리 클래스 - 빈 등록
        @Bean
        public AuthenticationSuccessHandler authenticationSuccessHandler() {
            return new LoginSuccessHandler();
        }
    

        // 사용자 정의 인증 구현 클래스 - 빈 등록
        @Bean
        public UserDetailsService customUserDetailsService() {
            return new CustomUserDetailsService();
        }

        // 인증 관리자 클래스 - 빈 등록
        @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }
        
        // 접근 거부 처리자 - 빈 등록
        @Bean
        public AccessDeniedHandler accessDeniedHandler() {
            return new CustomAccessDeniedHandler();
        }
    }


