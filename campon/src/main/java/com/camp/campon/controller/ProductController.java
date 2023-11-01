package com.camp.campon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/product")
public class ProductController {
    
    @GetMapping("/index")
    public String productMain(Model model) {
        return "product/index";
    }

    // 상품등록
     @GetMapping("/product_add")
    public String productAdd(Model model) {
        return "product/product_add";
    }
    
}
