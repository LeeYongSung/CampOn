package com.camp.campon.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


import com.camp.campon.dto.Users;
import com.camp.campon.dto.UserAuth;

public interface UserService {

    // 회원 등록
    public int insert(Users user) throws Exception;

    // 회원 조회
    public Users select(int userNo) throws Exception;

    //회원 조회(일반회원, 기업회원)
    public List<Users> memberList(String auth) throws Exception;
    //아이디중복검사
    public List<String> userIds() throws Exception;

    // 회원 조회 - id
    public Users selectById(String userId) throws Exception;

    // 로그인
    public void login(Users user, HttpServletRequest requset) throws Exception;

    // 회원 수정
    public int update(Users user) throws Exception;

    // 회원 삭제
    public int delete(String userId) throws Exception;

    // 캠핑업체셀러정보조회
    public Users productsseller(int campNo) throws Exception;    
    //캠핑업체 전화번호
    public String sellerTel(int cpdtNo) throws Exception;
    
}
