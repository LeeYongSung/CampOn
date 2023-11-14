package com.camp.campon.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.camp.campon.dto.Order;

@Mapper
public interface OrderMapper {
    //주문번호에 따른 주문정보와 상품목록, 캠핑장 가져오기
    public Order selectOrder(String orderNumber) throws Exception;
    //order테이블에 넣기
    public int addOrder(Order order) throws Exception;
    //주문번호에 따른 결제정보
    public Order paymentsByOrNo(String orderNumber) throws Exception;
    //결제 합계금액
    public Long payAmount(Order order) throws Exception;
    //payments 테이블에 넣기
    public int addPayments(Order order) throws Exception;
    //Delivery 테이블에 넣기
    public int addDelivery(String orderNumber) throws Exception;
    //이용자에게 대여내역 문자보내기
    public List<Order> toUserMsg(String orderNumber) throws Exception;
    //찜, 카트에 있는거 삭제
    public int saveCartDel(int userNo) throws Exception;
}
