package com.camp.campon.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import lombok.extern.slf4j.Slf4j;

// 로그인 성공 처리 클래스
@Slf4j
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String Cookie = null;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request
                                      , HttpServletResponse response
                                      , Authentication authentication) throws ServletException, IOException {
        log.info("로그인 인증 성공...");

        // 아이디 저장
        String rememberId = request.getParameter("remember-id");        // 아이디 저장 여부
        String username = request.getParameter("userId");                  // 아이디 (수정)
        log.info("remeberId : " + rememberId);
        log.info("id : " + username);

        // 아이디 저장 체크 ✅
        if( rememberId != null && rememberId.equals("on") ) {
            Cookie cookie = new Cookie("remember-id", username);     // 쿠키에 아이디 등록
            cookie.setMaxAge(60 * 60 * 24 * 7);                          // 유효기간  : 7일
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        
        // 아이디 저장 체크❌
        else {
            Cookie cookie = new Cookie("remember-id", username);     // 쿠키에 아이디 등록
            cookie.setMaxAge(0);                                  // 유효기간  : X
            cookie.setPath("/");        
            response.addCookie(cookie);
        }


        // 인증된 사용자 정보 - (아이디/패스워드/권한)
        User user = (User) authentication.getPrincipal();
        log.info("아이디 : " + user.getUsername());
        log.info("패스워드 : " + user.getPassword());           // 노출❌ (보안)
        log.info("권한 : " + user.getAuthorities());
        super.onAuthenticationSuccess(request, response, authentication);
    }


    
}
