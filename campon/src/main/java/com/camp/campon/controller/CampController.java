package com.camp.campon.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        
        model.addAttribute("productsimg", productsimg);
        model.addAttribute("productsproducts", productsproducts);
        model.addAttribute("productsreserve", productsreserve);
        model.addAttribute("productsseller", productsseller);
        model.addAttribute("productsenvironment", productsenvironment);
        model.addAttribute("productsreview", productsreview);
        model.addAttribute("productsfacility", productsfacility);
        
        
        return "camp/campproduct";
    }
    
    @GetMapping(value="/reservation")
    public String campReservation(Model model, Camp camp) throws Exception {
        log.info("오픈일정 안내 페이지 진입...");
        List<Camp> reservationList = campService.reservation(camp);

        for(int i = 0; i < reservationList.size(); i++) {
            int campNo = reservationList.get(i).getCampNo();
            int cpDtNo = reservationList.get(i).getCpdtNo();

            log.info("campNo : " + campNo);
            log.info("cpDtNo : " + cpDtNo);
        }

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
    
    
    
    
}
