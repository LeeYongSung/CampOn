package com.camp.campon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
    
    @GetMapping(value="/index")
    public String userMyPage(Model model) {
        return "user/index";
    }
    

}
