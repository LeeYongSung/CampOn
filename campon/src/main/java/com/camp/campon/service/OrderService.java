package com.camp.campon.service;

import com.camp.campon.dto.Order;

public interface OrderService {
    //주문번호에 따른 주문정보와 상품목록, 캠핑장 가져오기
    public Order selectOrder(String orderNumber) throws Exception;
        //주문번호에 따른 결제정보
        public Order paymentsByOrNo(String orderNumber) throws Exception;
}
