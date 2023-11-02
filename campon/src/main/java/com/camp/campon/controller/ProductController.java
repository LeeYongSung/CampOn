package com.camp.campon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.camp.campon.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    
    // 메인페이지
    @GetMapping("/index")
    public String productMain(Model model) {
        return "product/index";
    }

    // 상품등록
     @PostMapping("/productadd")
    public String productAdd(Model model) {
        return "product/productadd";
    }
    
    
}
