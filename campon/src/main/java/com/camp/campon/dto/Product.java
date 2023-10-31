package com.camp.campon.dto;

import java.util.Date;

import lombok.Data;

@Data
public class Product {

    // 상품
    private int productNo;
    private String productName;
    private String thumnail;
    private String productCon;
    private String productIntro;
    private String productCategory;
    private Long productPrice;
    private Date regDate;
    private Date updDate;

    // 유저
    private int userNo;

    // 상품 이미지
    private int productImgNo;
    private String productImgUrl;

    // 장바구니
    private int cartNo;
    private int cartCnt;

    
}
