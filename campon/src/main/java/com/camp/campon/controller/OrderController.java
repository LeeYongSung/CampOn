package com.camp.campon.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.camp.campon.dto.Camp;
import com.camp.campon.dto.Order;
import com.camp.campon.dto.Product;
import com.camp.campon.dto.Users;
import com.camp.campon.service.CampService;
import com.camp.campon.service.OrderService;
import com.camp.campon.service.ProductService;
import com.camp.campon.service.UserService;


@Slf4j
@Controller
@RequestMapping("/order")
public class OrderController {
        @Autowired
        private OrderService orderService;
        @Autowired
        private UserService userService;
        @Autowired
        private ProductService productService;
        @Autowired
        private CampService campService;

    //상품 결제완료 페이지
    // 테스트 : http://localhost:8081/order/depositcomp?orderNumber=...
    @GetMapping(value="/depositcomp")
    public String depositcomp(String orderNumber, Model model) throws Exception {
        Order order = orderService.selectOrder(orderNumber);
        Order orderpay = orderService.paymentsByOrNo(orderNumber);
        String pmType = orderpay.getPmType();
        String paytotal = orderpay.getPmPrice();
        String userName = userService.select(order.getUserNo()).getUserName();
        log.info(userName + "userName");
        log.info(order.toString());
        model.addAttribute(order);
        model.addAttribute("paytotal",paytotal);
        model.addAttribute("userName", userName);
        model.addAttribute("pmType", pmType);
        return "product/depositcomp";
    }
    

    //-------------------- 결제하기 --------------------
    @GetMapping(value="/payment")
    public String payMent(Model model, Principal principal) throws Exception {
        int userNo = 0;
        if (principal == null){ userNo = 1000;}
        else {
            String userId = principal.getName();
            Users users = userService.selectById(userId);
            userNo = users.getUserNo();
        }
        List<Product> cartList = productService.cartList(userNo);
        List<Camp> reservationList = campService.reservation(userNo);
        model.addAttribute("cartList", cartList);
        model.addAttribute("reservationList", reservationList);
        return "product/payment";
    }
    
}
