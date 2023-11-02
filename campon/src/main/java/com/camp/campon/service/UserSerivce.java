package com.camp.campon.service;

import javax.servlet.http.HttpServletRequest;

import com.camp.campon.dto.Users;
import com.camp.campon.dto.UserAuth;

public interface UserSerivce {

    // 회원 등록
    public int insert (Users user) throws Exception;

    // 회원 조회
    public Users select(int userNo) throws Exception;

    // 로그인
    public void login(Users user, HttpServletRequest request) throws Exception;


}
