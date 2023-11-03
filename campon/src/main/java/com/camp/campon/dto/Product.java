package com.camp.campon.dto;

import java.util.Date;

import lombok.Data;

@Data
public class Product {

    // 상품
    private Integer productNo;
    private String productName;
    private String productThumnail; //이름에 product 넣음
    private String productCon; //상세내용
    private String productIntro; //기본내용
    private String productCategory;
    private Long productPrice;
    private Date regDate;
    private Date updDate;

    // 유저
    private Integer userNo;

    // 상품 이미지
    private Integer productImgNo;
    private String productImgUrl;

    // 장바구니
    private Integer cartNo;
    private Integer cartCnt;

    
}
