package com.camp.campon.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camp.campon.dto.Order;
import com.camp.campon.mapper.OrderMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Order selectOrder(String orderNumber) throws Exception {
        Order order = orderMapper.selectOrder(orderNumber);
        Date orderDate = order.getOrderDate();
        Calendar cal = Calendar.getInstance(); 
		cal.setTime(orderDate);
        cal.add(Calendar.DATE, 3);
        Date depositDeadLine = new Date(cal.getTimeInMillis());
        log.info("입금기한 : "+depositDeadLine);
        order.setDepositDeadLine(depositDeadLine);
        return order;
    }

    @Override
    public Order paymentsByOrNo(String orderNumber) throws Exception {
        Order order = orderMapper.paymentsByOrNo(orderNumber);
        return order;
    }

    @Override
    public Long payAmount(Order order) throws Exception {
        Long payAmount = orderMapper.payAmount(order);
        return payAmount;
    }

    @Override
    public int addOrder(Order order) throws Exception {
        int result = orderMapper.addOrder(order);
        return result;
    }

    @Override
    public int addPayments(Order order) throws Exception {
        int result = orderMapper.addPayments(order);
        return result;
    }

    @Override
    public int addDelivery(String orderNumber) throws Exception {
        int result = orderMapper.addDelivery(orderNumber);
        return result;
    }

    @Override
    public List<Order> toUserMsg(String orderNumber) throws Exception {
        List<Order> orderList = orderMapper.toUserMsg(orderNumber);
        return orderList;
    }

    @Override
    public int saveCartDel(int userNo) throws Exception {
        int result = orderMapper.saveCartDel(userNo);
        return result;
        }

    
}
