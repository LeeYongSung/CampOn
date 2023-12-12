package com.camp.campon.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Order {
    
    // 주문
    private int orderNo;
    private int orderCnt;
    private Date startDate;
    private Date endDate;
    private String orderNumber;
    private int reservationNo;
    private Date orderDate;

    private int cpDtNo;
    //캠핑상품 이름
    private String cpDtName;

    // 상품
    private int productNo;
    private String productName;
    //상품 갯수
    private int cartCnt;
    
    // 유저
    private int userNo;

    // 배송
    private int deliveryNo;

    // 결제정보
    private int pmNo;
    private String pmType;
    private String pmPrice;
    private Date pmDate;

    private List<Product> productList;  
    private Camp camp; 
    private Date depositDeadLine; //무통장입금 시 입금기한


    //
    private int[] cartCnts;
    private int[] productNos;


}
