package com.camp.campon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.camp.campon.dto.Product;
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

    // 상품등록 페이지
    @GetMapping("/productadd")
    public String productAdd(Model model) {
        return "product/productadd";
    }

    // 상품등록 실행
    @PostMapping("/productaddpro")
    public String productAddPro(@ModelAttribute("product") Product product, Model model) {
        try {
            productService.productInsert(product);
            model.addAttribute("success", "상품등록 완료");
            return "product/productaddmessage";
            
        } catch (Exception e) {
            model.addAttribute("fail", "상품등록에 실패했습니다.");
            return "product/productaddmessage";
        }
    }

}
