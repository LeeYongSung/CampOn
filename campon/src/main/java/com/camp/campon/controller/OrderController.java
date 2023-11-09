package com.camp.campon.controller;

import java.security.Principal;
import java.util.List;
import java.util.Random;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



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
    
    //결제하기 버튼 누르면 폼 제출
    @PostMapping(value="/paymentpro")
    public String paymentpro(Order order, Principal principal) throws Exception {
        int userNo = 0;
        if (principal == null){ userNo = 1000;}
        else {
            String userId = principal.getName();
            Users users = userService.selectById(userId);
            userNo = users.getUserNo();
        }
        order.setUserNo(userNo);
        log.info("order 객체에 어떻게 담겨있는지 확인 : "+order);   
        //(reservationNo=2, pmType=카드,cartCnts=[2, 3, 4, 5], productNos=[1, 2, 11, 1])
        //카트 업뎃
        int[] cartCnts = order.getCartCnts();
        int[] productNos = order.getProductNos();
        for (int i=0; i< cartCnts.length ; i++) {
            Product product = new Product();
            product.setCartCnt(cartCnts[i]);
            product.setProductNo(productNos[i]);
            product.setUserNo(userNo);
            int result = productService.cartUpdate(product);
            log.info("카트업뎃여부 : "+result);
        }
        //orderNumber 생성
        int createNum = 0;
        String ranNum = "";
        String orderNumber = "";
        Random random = new Random();
        for (int j=0; j<6; j++){
            createNum = random.nextInt(9);
            ranNum =  Integer.toString(createNum);
            orderNumber += ranNum;
        } 
        System.out.println(orderNumber); //order_number에 넣어줄 거임
        order.setOrderNumber(orderNumber);
        int result2 = orderService.addOrder(order);
        log.info("order 테이블에 주문정보 등록여부 : " +result2);
        Long pmPrice = orderService.payAmount(order);
        log.info("결제합계금액 : "+pmPrice);
        order.setPmPrice(pmPrice+"");
        int result3 = orderService.addPayments(order);
        log.info("payments테이블에 등록 : "+ result3);
        int result4 = orderService.addDelivery(orderNumber);
        log.info("delivery 테이블에 등록 : "+result4);
        return "redirect:/order/depositcomp?orderNumber=" + orderNumber;
    }
    
    // 상품 결제완료 페이지
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
    

}
