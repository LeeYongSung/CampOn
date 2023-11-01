package com.camp.campon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@Controller
@RequestMapping("/camp")
public class CampController {

    @GetMapping(value="/index")
    public String campMain(Model model) {
        System.out.println("test");
        return "camp/index";
    }
    
    
}
