package com.camp.campon.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.camp.campon.dto.Board;
import com.camp.campon.dto.Camp;
import com.camp.campon.dto.Users;

import lombok.extern.slf4j.Slf4j;

import com.camp.campon.dto.Camp;
import com.camp.campon.service.BoardService;
import com.camp.campon.service.CampService;
import com.camp.campon.service.UserService;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




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
    private AuthenticationManager authenticationManager;

    @GetMapping(value="/index")
    public String campMain(Model model) throws Exception {
        List<Camp> camptypeList = campService.camptype();
        List<Camp> campnewList = campService.newList();
        List<Camp> campHotList = campService.hotList();
        List<Board> newReviewList = boardService.newReviewList();
        log.info("camptypeList : " + camptypeList);
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
    @GetMapping(value="/insertProduct")
    public String insertProduct() {
        return "camp/insertProduct";
    }

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
    public String campProduct(Model model, int campTypeNo) throws Exception {
        List<Camp> campselect = campService.campSelect(campTypeNo);
        log.info("campselect" + campselect);
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
    public String campReservation(Model model, Integer userNo) throws Exception {

        log.info("예약 조회페이지 진입...");
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // List<Users> user = (List<Users>) auth.getPrincipal();

        // log.info("user : " + user);

        // int userNo = user.get(0).getUserNo();
        // log.info("name : " + userId);

        // 임시값
        userNo = 2;

        List<Camp> reservationList = campService.reservation(userNo);
        log.info("reservationList : " + reservationList);
        model.addAttribute("reservationList", reservationList);

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
        // 임시값
        String userId = "user";
        Users user = userService.selectById(userId);
        log.info("camp" + camp);
        log.info("user" + user);
        model.addAttribute("camp", camp);
        model.addAttribute("user", user);
        return "camp/reservate";
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

    //캠핑상품 등록
    @GetMapping(value="/campdetailinsert")
    public String campdetailinsert(Model model, Integer campNo, Integer userNo, @ModelAttribute Camp camp) throws Exception{
        log.info("캠핑장번호 : " + campNo);
        model.addAttribute("userNo", userNo);
        model.addAttribute("campNo", campNo);
        return "camp/campdetailinsert";
    }
    
    @PostMapping(value="/campdetailinsert")
    public String campdetailinsertPro(@ModelAttribute Camp camp) throws Exception {
        log.info("타입번호 : " + camp.getCampTypeNo());
        int result = campService.detailinsert(camp);
        
        if(result == 0) return "camp/campdetailinsert";

        return "redirect:/camp/campdetailinsert";
    }
    
    //캠핑상품 수정
    @GetMapping(value="/campdetailupdate")
    public String campdetailupdate(Model model, int cpdtNo) throws Exception{
        Camp camp = campService.productintro(cpdtNo);
        model.addAttribute("camp", camp);
        return "camp/campdetailupdate";
    }

}
