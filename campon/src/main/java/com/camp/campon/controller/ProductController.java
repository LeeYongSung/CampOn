package com.camp.campon.controller;

import java.io.FileInputStream;
import java.security.Principal;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.camp.campon.dto.Camp;
import com.camp.campon.dto.Product;
import com.camp.campon.dto.Productreview;
import com.camp.campon.dto.Users;
import com.camp.campon.service.CampService;
import com.camp.campon.service.ProductService;
import com.camp.campon.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;



@Slf4j
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private CampService campService;
    
    @Value("${upload.path}")
    private String uploadPath;

    //-------------------- 상품 메인 --------------------
    // 메인페이지
    @GetMapping("/index")
    public String productMain(Model model) throws Exception {
        //상품 후기 불러오기
        List<Productreview> proReviewList =  productService.getReviewList();
        log.info("후기목록의 사이즈 : "+proReviewList.size());
        model.addAttribute("proReviewList", proReviewList);
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





    //-------------------- 찜 목록 --------------------
    // 상품 찜 목록
    @GetMapping("/wishlist")
    public String wishlist(Model model) {
        List<Product> wishlist = productService.wishList();
        model.addAttribute("wishlist", wishlist);
        return "product/wishlist";
    }

    // 상품 찜 삭제
    @GetMapping(value="/wishlistDelete")
    public String wishlistDelete(int wishlistNo) {
        int result = productService.wishListDelete(wishlistNo);
        if( result == 0 ) return "redirect:/product/wishlist";
        return "redirect:/product/wishlist";
    }

    // 장바구니 담기
    @GetMapping(value="/addcart")
    public String addCart(Product product) throws Exception {
        int result = productService.addCart(product);
        log.info("장바구니에 넣기 성공여부 : "+ result);
        return "product/wishlist";
    }
    




    //-------------------- 장바구니 --------------------
    // 장바구니 목록
    @GetMapping("/cart")
    public String cartlist(Model model) {
        List<Product> cartList = productService.cartList();
        model.addAttribute("cartList", cartList);
        return "product/cart";
    }

    // 장바구니 삭제
    @GetMapping(value="/cart/Delete")
    public String cartListDelete(int cartlistNo) {
        int result = productService.cartListDelete(cartlistNo);
        if( result == 0 ) return "redirect:/product/cart";
        return "product/cart";
    }
    
    // 상품 상세페이지
    @GetMapping(value="/productdetail") 
    public String productDetail(Model model, Integer productNo) throws Exception {
        List<Product> productimg = productService.productimg(productNo);
        log.info(productNo + "");
        Product select = productService.select(productNo);
        List<Productreview> proReviewList = productService.getReviewListByNo(productNo);
        int reviewCount = productService.reviewCount(productNo);
        model.addAttribute("productimg", productimg);
        model.addAttribute( "select", select);
        model.addAttribute("proReviewList", proReviewList);
        model.addAttribute("reviewCount", reviewCount);
        return "product/productdetail";
    }





    //-------------------- 결제하기 --------------------
    @GetMapping(value="/payment")
    public String payMent(Model model, Integer userNo) throws Exception {
        // 임시값
        userNo = 2;
        List<Product> cartList = productService.cartList();
        List<Camp> reservationList = campService.reservation(userNo);

        model.addAttribute("cartList", cartList);
        model.addAttribute("reservationList", reservationList);

        return "product/payment";
    }
    
}
