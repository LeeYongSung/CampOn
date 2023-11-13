package com.camp.campon.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.camp.campon.dto.Users;
import com.camp.campon.dto.UserAuth;

@Mapper
public interface UserMapper {

    // 회원 등록
    public int insert(Users user) throws Exception;
    
    // 회원 조회
    public Users select(int userNo) throws Exception;

    // 회원 조회
    public Users selectById(String userId) throws Exception;

    //회원 조회(일반회원, 기업회원)
    public List<Users> memberList(String auth) throws Exception;
    
    // 사용자 인증(로그인) - id
    public Users login(String username);
    
    //아이디중복검사
    public List<String> userIds() throws Exception;
 
    // 회원 권한 등록
    public int insertAuth(UserAuth userAuth) throws Exception;
    
    // 회원 수정
    public int update(Users user) throws Exception;

    // 회원 삭제
    public int delete(String userId) throws Exception;

    // 캠핑업체셀러정보조회
    public Users productsseller(int campNo) throws Exception;

    //캠핑업체 전화번호
    public String sellerTel(int cpdtNo) throws Exception;
    
}
