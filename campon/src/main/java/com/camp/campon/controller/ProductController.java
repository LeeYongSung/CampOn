package com.camp.campon.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;


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
    //카페고리별 상품 목록
    @GetMapping(value={"", "/"}, params="category")
    public String getCategoryList(@RequestParam("category") String category, Model model) throws Exception {
        log.info("카테고리" + category);
        List<Product> productList = productService.getCategoryList(category);
        model.addAttribute("productList", productList);
        return "product/productList";
    }

    //카테고리별 상품 목록 ajax (사용x)
    @GetMapping(value="/getList", params="category")
    public List<Product> getCategoryListAjax(@RequestParam("category") String category) throws Exception {
        log.info("카테고리AJAX" + category);
        List<Product> productList = productService.getCategoryList(category);
        return productList;
    }
    

    // 상품등록 페이지
    @GetMapping("/productadd")
    public String productAdd() {
        return "product/productadd";
    }

    // 상품등록 실행
    @PostMapping("/productInsert")
    public String productInsert(Product product) throws Exception {
        int result = productService.productInsert(product);
        log.info("상품등록 성공여부 : " +result);
            return "user/mypage";
    }
}
