package com.camp.campon.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.camp.campon.dto.Camp;
import com.camp.campon.dto.Order;
import com.camp.campon.dto.Product;
import com.camp.campon.dto.Users;
import com.camp.campon.service.CampService;
import com.camp.campon.service.OrderService;
import com.camp.campon.service.ProductService;
import com.camp.campon.service.SMSService;
import com.camp.campon.service.UserService;

import lombok.extern.slf4j.Slf4j;



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
        @Autowired
        private SMSService smsService;


        
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
        List<Camp> reservationList = campService.reservationNow(userNo);
        log.info("reservationList : " + reservationList);
        model.addAttribute("cartList", cartList);
        model.addAttribute("reservationList", reservationList);
        return "product/payment";
    }
    
    //결제하기 버튼 누르면 폼 제출
    @PostMapping(value="/paymentpro")
    public String paymentpro(Order order, Principal principal) throws Exception {

        int userNo = 0;
        String userTel = "01000000000";
        String userName = "이용자";
        if (principal == null){ userNo = 1000;}
        else {
            String userId = principal.getName();
            Users users = userService.selectById(userId);
            userTel = users.getUserTel();
            userNo = users.getUserNo();
            userName = users.getUserName();
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

        
        //상품 결제 문자 보내기
        SimpleDateFormat sdt = new SimpleDateFormat("yy년 MM월 dd일");
        SimpleDateFormat sdtt = new SimpleDateFormat("MM월 dd일");
        List<Order> orderList = orderService.toUserMsg(orderNumber);
        String cpDtName = orderList.get(0).getCpDtName();
        String productmsg = "";
        for (int i = 0; i < orderList.size(); i++) {
            Order order2 = orderList.get(i);
            int orderCnt = order2.getOrderCnt();
            String productName = order2.getProductName();
            String mmm ="";
            if (i == orderList.size()-1){ mmm =  productName + " : "+orderCnt + "개";}
            else { mmm = productName + " : "+orderCnt + "개, "; }
            productmsg += mmm;
        }
        String stDate = sdt.format(orderList.get(0).getStartDate());
        String sttDate = sdtt.format(orderList.get(0).getStartDate());
        String edDate = sdt.format(orderList.get(0).getEndDate());
        String msg = "안녕하세요 "+ userName+"님 캠프온입니다. \n"+ stDate +"~"+ edDate+ " 예약된 " +cpDtName+" 캠핑장에 대여상품 "+productmsg+"를 대여하셨습니다. \n"+sttDate +"에 캠핑장으로 배송될 예정입니다. \n이용해주셔서 감사합니다. ";
        MultiValueMap<String, String> param =  new LinkedMultiValueMap<String, String>(); 
        param.add("msg", msg);
        param.add("receiver", userTel);
        param.add("rdate", "");
        param.add("rtime", "");
        param.add("testmode_yn", "N");
        // 문자 전송 요청
        Map<String, Object> resultMap = smsService.send(param);
        Object resultCode = resultMap.get("result_code");
        Integer result_code = Integer.valueOf( resultCode != null ? resultCode.toString() : "-1" );
        String message = (String) resultMap.get("message");


        //장바구니와 찜에 있는 상품들 모두 삭제
        int result5 = orderService.saveCartDel(userNo);
        log.info("장바구니와 찜에 있는 목록들 삭제여부 : "+result5);
        return "redirect:/order/depositcomp?orderNumber=" + orderNumber;
    }
    
    // 상품 결제완료 페이지
    // 테스트 : http://localhost:8081/order/depositcomp?orderNumber=...
    @GetMapping(value="/depositcomp")
    public String depositcomp(String orderNumber, Model model) throws Exception {
        
        Order order = orderService.selectOrder(orderNumber);
        log.info("order : " + order);
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
