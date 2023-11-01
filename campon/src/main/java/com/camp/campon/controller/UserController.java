package com.camp.campon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;



@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
    
    @GetMapping(value="/index")
    public String userMyPage(Model model) {
        return "user/index";
    }

    @GetMapping(value="/login")
    public String userLogin(Model model) {
        return "user/login";
    }

    @GetMapping(value="/join")
    public String userJoin(Model model) {
        return "user/join";
    }

    @GetMapping(value="/seller")
    public String userSeller(Model model) {
        return "user/seller";
    }

    @GetMapping(value="/admin")
    public String userAdmin(Model model) {
        return "user/admin";
    }
    

}
