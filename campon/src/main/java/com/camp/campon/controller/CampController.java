package com.camp.campon.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.camp.campon.dto.Board;
import com.camp.campon.dto.Camp;
import com.camp.campon.dto.Product;
import com.camp.campon.dto.Users;
import com.camp.campon.service.BoardService;
import com.camp.campon.service.CampService;
import com.camp.campon.service.ProductService;
import com.camp.campon.service.SMSService;
import com.camp.campon.service.UserService;

import lombok.extern.slf4j.Slf4j;




@Slf4j
@Controller
@RequestMapping("/camp")
public class CampController {

    @Autowired
    private CampService campService;
    @Autowired
    private BoardService boardService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private SMSService smsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping(value="/index")
    public String campMain(Model model) throws Exception {
        List<Camp> camptypeList = campService.camptype();
        List<Camp> campnewList = campService.newList();
        List<Camp> campHotList = campService.hotList();
        List<Board> newReviewList = boardService.newReviewList();
        // log.info("camptypeList : " + camptypeList);
        model.addAttribute("camptypeList", camptypeList);
        model.addAttribute("campnewList", campnewList);
        model.addAttribute("campHotList", campHotList);
        model.addAttribute("newReviewList", newReviewList);
        return "camp/index";
    }

    /**
     * 소개 페이지
     * @return
     */
    @GetMapping(value="/information")
    public String information() {
        return "camp/information";
    }
     
    /**
     * 상품 등록 페이지
     * @return
     */
    // @GetMapping(value="/insertProduct")
    // public String insertProduct() {
    //     return "camp/insertProduct";
    // }

    /**
     * 즐겨찾기 페이지
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value="/favorites")
    public String favoritesList(Model model) throws Exception {
        List<Camp> favoritesList = campService.favoritesList();
        model.addAttribute("favoritesList", favoritesList);
        return "camp/favorites";
    }
    
    /**
     * 상품 목록 페이지
     * @param model
     * @param campTypeNo
     * @return
     * @throws Exception
     */
    @GetMapping(value="/campproducts")
    public String campProduct(Model model
                            , Integer campTypeNo
                            , Integer[] campTypeNos, Camp camp) throws Exception {
        List<Camp> campselect = null;
        campTypeNo = campTypeNo == null ? 0 : campTypeNo;
        log.info("campTypeNo : " + campTypeNo);

        if(campTypeNo == 0) {
            
            if( campTypeNos != null && campTypeNos.length != 0 )
            for (Integer no : campTypeNos) {
                log.info("no  :  " + no);
            }
            
            List<String> checkBoxList = new ArrayList<>();
            for (int i = 0; i < campTypeNos.length; i++) {
                checkBoxList.add(campTypeNos[i] + "");
            }

            if(campTypeNos != null && campTypeNos.length == 0) campTypeNo = -1;

            camp.setCheckBoxList(checkBoxList);
            camp.setSearchDate(new Date());
            camp.setCampTypeNo(campTypeNo);

            campselect = campService.campSearch(camp);
            log.info("searchDate : " + camp.getSearchDate() );
        } else {
            campselect = campService.campSelect(campTypeNo);
            log.info("campselect" + campselect);
        }

        model.addAttribute("campselect", campselect);
        return "camp/campproducts";
    }
    
    /**
     * 즐겨찾기 항목 삭제
     * @param favoritesNo
     * @return
     * @throws Exception
     */
    @GetMapping(value="/favoriteDelete")
    public String favoriteDelete(int favoritesNo) throws Exception {
        int result = campService.favoriteDelete(favoritesNo);
        if(result==0) return "redirect:/camp/favorites";
        return "redirect:/camp/favorites";
    }
    
    //캠핑장페이지
    @GetMapping(value="/campproduct")
    public String campProducts(Model model, int campNo) throws Exception {
        List<Camp> productsimg = campService.productsimg(campNo);
        Camp productsproducts = campService.productsproducts(campNo);
        int productsreserve = campService.productsreserve(campNo);
        Users productsseller = userService.productsseller(campNo);
        List<Camp> productsenvironment = campService.productsenvironment(campNo);
        Board productsreview = boardService.productsreview(campNo);
        List<Camp> productsfacility = campService.productsfacility(campNo);
        List<Camp> productsproductlist = campService.productsproductlist(campNo);
        
        model.addAttribute("productsimg", productsimg);
        log.info("productsproducts : " + productsproducts);
        model.addAttribute("productsproducts", productsproducts);
        model.addAttribute("productsreserve", productsreserve);
        model.addAttribute("productsseller", productsseller);
        model.addAttribute("productsenvironment", productsenvironment);
        model.addAttribute("productsreview", productsreview);
        model.addAttribute("productsfacility", productsfacility);
        model.addAttribute("productsproductlist", productsproductlist);
        
        
        return "camp/campproduct";
    }
    
    @GetMapping(value="/reservation")
    public String campReservation(Model model, Principal principal) throws Exception {
        int userNo = 0;
        if (principal == null){ userNo = 1000;}
        else {
            String userId = principal.getName();
            Users users = userService.selectById(userId);
            userNo = users.getUserNo();
            log.info(userNo + "");
        }
        List<Product> productList = productService.reservedProduct(userNo);
        List<Camp> reservationList = campService.reservation(userNo);
        log.info("reservationList : " + reservationList);
        model.addAttribute("reservationList", reservationList);
        model.addAttribute("productList", productList);
        return "camp/reservation";
    }

    @GetMapping(value="/schedule")
    public String campSchedule(Model model, Camp camp) throws Exception {

        // 현재 날짜 가져오기
        LocalDate currentDate = LocalDate.now();
        
        // 30일 뒤 날짜 계산
        LocalDate plus30Days = currentDate.plusDays(30);
        
        // 날짜 포맷 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        // 포맷에 맞게 날짜 문자열로 변환
        String startDate = currentDate.format(formatter);
        String endDate = plus30Days.format(formatter);
        
        log.info("현재 날짜 : " + startDate);
        log.info("30일 뒤 날짜 날짜 : " + endDate);

        camp.setCampOpen(startDate);
        camp.setCampClose(endDate);
        
        List<Camp> campschedule = campService.schedule(camp);

        
        log.info("campschedule : " + campschedule);
        model.addAttribute("campschedule", campschedule);
        model.addAttribute("startDate" ,  startDate);

        return "camp/schedule";
    }

    @GetMapping(value="/campprogress")
    public String campProgress(Model model) {

        return "camp/campprogress";
    }
    
    
    
    /**
     * 상품상세페이지
     * @param model
     * @param campNo
     * @param cpdtNo
     * @return
     * @throws Exception
     */
    @GetMapping(value="/campdetail")
    public String campdetail(Model model, int cpdtNo) throws Exception {
        List<Camp> productimg = campService.productimg(cpdtNo);
        Camp productintro = campService.productintro(cpdtNo);

        model.addAttribute("productimg", productimg);
        model.addAttribute("productintro", productintro);
        
        return "camp/campdetail";
    }

    @GetMapping(value="/reservate")
    public String campReservate(Model model, int cpdtNo) throws Exception {

        Camp camp = campService.reservate(cpdtNo);

        log.info("camp : " + camp);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();
        if(userId.equals("anonymousUser")) return "redirect:/user/login";
        // log.info(userId);
        // 임시값
        // String userId = "user";
        Users user = userService.selectById(userId);
        log.info("user : " + user);
        // log.info("camp" + camp);
        // log.info("user" + user);
        model.addAttribute("camp", camp);
        model.addAttribute("user", user);
        return "camp/reservate";
    }
    
    @PostMapping(value="/reservate")
    public String campReservatePay(Model model, Camp camp) throws Exception {
        int result = campService.reservateInsert(camp);
        //캠핑장 판매자한테 문자보내기
        int cpdtNo = camp.getCpdtNo();
        String cpdtName = camp.getCpdtName();
        String sellerTel = userService.sellerTel(cpdtNo);
        SimpleDateFormat sdf = new SimpleDateFormat("yy년 MM월 dd일"); 
        Date reservationStart = camp.getReservationStart();
        String rsStart = sdf.format(reservationStart);
        Date reservationEnd = camp.getReservationEnd();
        String rsEnd = sdf.format(reservationEnd);
        String msg = "안녕하세요 캠핑장 판매자님 캠프온입니다.\n"+cpdtName +"에 "+rsStart +"~"+rsEnd+" 예약되었습니다. 홈페이지에서 확인하세요.";
        MultiValueMap<String, String> param =  new LinkedMultiValueMap<String, String>(); 
        param.add("msg", msg);
        param.add("receiver", sellerTel);
        param.add("rdate", "");
        param.add("rtime", "");
        param.add("testmode_yn", "N");
        // 문자 전송 요청
        Map<String, Object> resultMap = smsService.send(param);
        Object resultCode = resultMap.get("result_code");
        Integer result_code = Integer.valueOf( resultCode != null ? resultCode.toString() : "-1" );
        String message = (String) resultMap.get("message");
        
        //캠핑장 예약한 사람한테 문자보내기
        String userTel = camp.getUserTel();
        String msgg = "안녕하세요 캠프온입니다.\n"+cpdtName +"에 "+rsStart +"~"+rsEnd+" 예약되었습니다. \n홈페이지에서 캠핑장에 필요한 상품도 대여해보세요. \n 즐거운 여행 되세요!"; 
        MultiValueMap<String, String> param2 =  new LinkedMultiValueMap<String, String>(); 
        param.add("msg", msgg);
        param.add("receiver", userTel);
        param.add("rdate", "");
        param.add("rtime", "");
        param.add("testmode_yn", "N");
        // 문자 전송 요청
        Map<String, Object> resultMap2 = smsService.send(param2);
        Object resultCode2 = resultMap.get("result_code");
        Integer result_code2 = Integer.valueOf( resultCode2 != null ? resultCode2.toString() : "-1" );
        String message2 = (String) resultMap2.get("message");


        if(result == 0) return "camp/reservate?cpdtNo=" + cpdtNo;
        return "redirect:/camp/complete";
    }
    
    
    @GetMapping(value="/complete")
    public String complete(Model model ) throws Exception {
        Camp reservecomplete = null;
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String userId = "user";
        // String userId = auth.getName();

        log.info("name : " + userId);

        Users user = userService.selectById(userId);

        String name = user.getUserId();

        if(userId.equals(name)){
            reservecomplete = campService.reservecomplete(userId);
            model.addAttribute("reservecomplete", reservecomplete);
        }

        return "camp/complete";
    }

    @ResponseBody
    @GetMapping(value="/campSearch")
    public List<Camp> campSearch(Model model, Camp camp) throws Exception {

        log.info("keywordValue : " + camp.getKeyword());
        log.info("dateValue : " + camp.getSearchDate());
        log.info("regionNoValue : " + camp.getRegionNo());
        log.info("checkBoxList : " + camp.getCheckBoxList());
        List<String> checkBoxList = camp.getCheckBoxList();

        if( checkBoxList != null )
        for(int i = 0; i < checkBoxList.size(); i++) {
            // Integer[] campTypeNo = checkBoxList.get(i).split(',');
            log.info("campTypeNo : " + checkBoxList.get(i));
        }

        if( checkBoxList == null )  {
            camp.setCampTypeNo(-1);
            camp.setCheckBoxList(new ArrayList<>());
        }


        camp.setSearchDate(new Date());
        List<Camp> campList = campService.campSearch(camp);

        model.addAttribute("campselect", campList);
        // return "camp/campproducts";
        return campList;
    }
    

    @GetMapping(value="/campSearch2")
    public String campSearch2(Model model, Camp camp) throws Exception {

        log.info("keywordValue : " + camp.getKeyword());
        log.info("dateValue : " + camp.getSearchDate());
        log.info("regionNoValue : " + camp.getRegionNo());
        log.info("checkBoxList : " + camp.getCheckBoxList());
        List<String> checkBoxList = camp.getCheckBoxList();

        if( checkBoxList != null )
        for(int i = 0; i < checkBoxList.size(); i++) {
            // Integer[] campTypeNo = checkBoxList.get(i).split(',');
            log.info("campTypeNo : " + checkBoxList.get(i));
        }

        if( checkBoxList == null )  {
            camp.setCampTypeNo(-1);
            camp.setCheckBoxList(new ArrayList<>());
        }


        camp.setSearchDate(new Date());
        List<Camp> campList = campService.campSearch(camp);

        model.addAttribute("campselect", campList);
        // return "camp/campproducts";
        return "UI/component/camp/list";
    }

    @GetMapping(value="/reservationdelete")
    public String reservationdelete(int reservationNo) throws Exception {
        int result = campService.reservationdelete(reservationNo);
        int result1 = boardService.crdeletelist(reservationNo);
        return "redirect:/camp/reservation";
    }
    

}
