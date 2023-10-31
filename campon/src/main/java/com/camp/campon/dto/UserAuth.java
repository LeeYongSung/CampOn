package com.camp.campon.dto;

import lombok.Data;

// 회원 권한
@Data
public class UserAuth {
    
    private int authNo;
    private String userId;
    private String auth;

}
