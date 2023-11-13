package com.camp.campon.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

//만약 문제생기면 implements Serializable 빼기 
@Data
public class Users implements Serializable {
    // 사용자
    private int userNo;
    private String userId; //아이디
    private String userPw; //패스워드
    private String userPwCheck;  // 패스워드 확인(수정)
    private String userName; //이름
    private String userTel;
    private String userEmail;
    private String userAddress;
    //사업자일  경우 업체명, 사업자 번호 필요(수정)
    private String companyName;
    private Integer companyNumber;

    private Date regDate;
    private Date updDate;

    //권한(수정)
    private String auth;

    // 권한 목록
   // List<UserAuth> authList;

}
