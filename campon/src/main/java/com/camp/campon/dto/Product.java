package com.camp.campon.dto;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Product {

    // 상품
    private Integer productNo;
    private String productName;
    private String productThumnail; //이름에 product 넣음
    private String productCon; //상세내용(이미지경로)
    private String productIntro; //기본내용
    private String productCategory;
    private Long productPrice;
    private Date regDate;
    private Date updDate;

    // 유저
    private Integer userNo;

    // 상품 이미지
    private Integer productimgNo; //I를 i로 수정
    private String productimgUrl;
    private List<String> productImgsUrlList;

    //상품 이미지들
    private List<MultipartFile> productThmFile;
    private List<MultipartFile> productConFile;
    private List<MultipartFile> productImgs;

    // 장바구니
    private Integer cartNo;
    private Integer cartCnt;

    // 찜 하기
    private int productsaveNo; //찜번호
    private int wishlistNo; //없어도 됨

    //주문별 대여상품 갯수
    private int orderCnt;

    //대여된 상품갯수
    private String sum;

    private int orderNo;



    
}
