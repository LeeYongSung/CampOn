package com.camp.campon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import groovy.util.logging.Slf4j; 


@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @GetMapping(value="/index")
    public String adminUser(Model model) {
        return "admin/index";
    }
    
}
