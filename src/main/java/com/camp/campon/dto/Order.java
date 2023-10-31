package com.camp.campon.dto;

import java.util.Date;

import lombok.Data;

@Data
public class Order {
    
    // 주문
    private int orderNo;
    private int orderCnt;
    private Date startDate;
    private Date endDate;
    private String orderNumber;

    // 상품
    private int productNo;

    // 유저
    private int userNo;

    // 배송
    private int deliveryNo;

    // 결제정보
    private int pmNo;
    private String pmType;
    private String pmPrice;
    private Date pmDate;
}
