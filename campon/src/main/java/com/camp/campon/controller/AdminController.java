package com.camp.campon.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.camp.campon.dto.Camp;
import com.camp.campon.dto.Users;
import com.camp.campon.service.BoardService;
import com.camp.campon.service.CampService;
import com.camp.campon.service.ProductService;
import com.camp.campon.service.UserService;

import lombok.extern.slf4j.Slf4j;


 


@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private CampService campService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BoardService boardService;

    @GetMapping(value="/index")
    public String adminUser(Model model) {
        return "admin/index";
    }

    @GetMapping(value="/campproductlist")
    public String campList(Model model, Authentication auth) throws Exception {
        String name = "";
        if(auth != null) {
            name = auth.getName();
        } else {
            name = "anonymouseUser";
        }
        log.info("name : " + name);
        if(name == null) {
            return "redirect:/user/login";
        } else {
            if(!name.equals("anonymouseUser")) {

                // 유저 정보 획득
                Users user = userService.selectById(name);
                int userNo = user.getUserNo();

                // 획득한 유저 번호로 캠핑장 정보 획득
                List<Camp> camp = campService.campproductUser(userNo);
                log.info("camp" + camp);
                // for(int i = 0; i < camp.size(); i++) {
                //     Camp campList = new Camp();
                //     int campNo = camp.get(i).getCampNo();
                //     String campName = camp.get(i).getCampName();
                //     String campAddress = camp.get(i).getCampAddress();
                //     List<Camp> campDetail = campService.productsproductlist(campNo);
                //     for(int j = 0; j < campDetail.size(); j++) {
                //         Date regDate = camp.get(i).getRegDate();
                //         Date updDate = camp.get(i).getUpdDate();
                //         log.info("campNo(" + i + ") : " + campNo);
                //         List<Camp> campimg = campService.productsimg(campNo);
                //         String cpiUrl = campimg.get(i).getCpiUrl();
                //         String cpdtName = campDetail.get(i).getCpdtName();
                //         campList.setCampName(campName);
                //         campList.setCampAddress(campAddress);
                //         campList.setCpdtName(cpdtName);
                //         campList.setRegDate(regDate);
                //         campList.setUpdDate(updDate);
                //         campList.setCpiUrl(cpiUrl);
                //         String campDetatilImg = campDetail.get(i).getCpdiUrl();
                //         // log.info("campDetail : " + campDetail);
                //         log.info("campDetatilImg : " + campDetatilImg);
                //         log.info("campList : " + campList);
                        model.addAttribute("campList", camp);
                //     }
                // }
                return "admin/campproductlist";
            } 
            return "redirect:/user/login";
        }
    }
    

    @GetMapping(value="/campproductadd")
    public String campProductAdd() {
        return "admin/campproductadd";
    }

    @GetMapping(value="/campproductupdate")
    public String campProductUpdate() {
        return "admin/campproductupdate";
    }
    
    
}
