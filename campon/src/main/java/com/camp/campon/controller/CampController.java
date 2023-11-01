package com.camp.campon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.camp.campon.dto.Camp;

import lombok.extern.slf4j.Slf4j;

import com.camp.campon.dto.Camp;
import com.camp.campon.service.CampService;


@Slf4j
@Controller
@RequestMapping("/camp")
public class CampController {

    @Autowired
    private CampService campService;

    @GetMapping(value="/index")
    public String campMain(Model model) throws Exception {
        System.out.println("test");
        List<Camp> campnewList = campService.newList();
        model.addAttribute("campnewList", campnewList);
        return "camp/index";
    }

    @GetMapping(value="/information")
    public String information() {
        return "camp/information";
    }
    
    @GetMapping(value="/insertProduct")
    public String insertProduct() {
        return "camp/insertProduct";
    }
    
    

    @GetMapping(value="/campproduct")
    public String campProduct(Model model, Camp camp ) {
        return "camp/campproduct";
    }

    
}
