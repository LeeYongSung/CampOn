package com.camp.campon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.camp.campon.dto.Camp;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/camp")
public class CampController {

    @GetMapping(value="/index")
    public String campMain(Model model) {
        System.out.println("test");
        return "camp/index";
    }
    

    @GetMapping(value="/campproduct")
    public String campProduct(Model model, Camp camp ) {
        return ;
    }

    
}
