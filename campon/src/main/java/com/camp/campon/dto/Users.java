package com.camp.campon.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Users {


    // 사용자
    private int userNo;
    private String userName;
    private String userId;
    private String userPw;
    private String userTel;
    private String email;
    private String userAddress;
    private Date regDate;
    private Date updDate;

    // 권한 목록
    List<UserAuth> authList;

}
