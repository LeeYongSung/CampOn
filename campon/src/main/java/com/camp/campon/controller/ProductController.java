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
import org.springframework.web.bind.annotation.ResponseBody;
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
        //List<Productreview> proReviewList =  productService.getReviewList();
        List<Productreview> proReviewList =  productService.getReviewListLimit();
        // 추천상품 리스트
        List<Product> productHotList = productService.hotList();
        
        model.addAttribute("proReviewList", proReviewList);
        model.addAttribute("productHotList", productHotList);
        
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
    // 상품 상세페이지
    @GetMapping(value="/productdetail") 
    public String productDetail(Model model, Integer productNo) throws Exception {
        Product product = productService.select(productNo);
        List<Productreview> proReviewList = productService.getReviewListByNoLim(productNo);
        for (Productreview productreview : proReviewList) {
            log.info(productreview.getPrNo() +"리뷰넘버"); 
        }
        int reviewCount = productService.reviewCount(productNo);
        model.addAttribute("product", product);
        model.addAttribute("proReviewList", proReviewList);
        model.addAttribute("reviewCount", reviewCount);
        return "product/productdetail";
    }

    //-------------------- 찜 목록 --------------------
    // 상품 찜 목록
    @GetMapping("/wishlist")
    public String wishlist(Model model, Principal principal) throws Exception {
        String userId = principal.getName();
        Users users = userService.selectById(userId);
        int userNo = users.getUserNo();
        List<Product> wishlist = productService.wishList(userNo);
        model.addAttribute("wishlist", wishlist);
        return "product/wishlist";
    }
    // 상품 찜 등록
    @ResponseBody
    @GetMapping(value="/addProductsave")
    public String addProductsave(int productNo, Principal principal) throws Exception {
        String userId = principal.getName();
        Users users = userService.selectById(userId);
        int userNo = users.getUserNo();
        Product product = new Product();
        product.setProductNo(productNo);
        product.setUserNo(userNo);
        int result = productService.addProductsave(product);
        log.info("찜등록여부 : " + result);
        if (result > 0) return "SUCCESS";
        else return "FAIL";
    }
    // 상품 찜 삭제
    @PostMapping(value="/wishlistDelete")
    public String wishlistDelete(int productsaveNo) throws Exception  {
        int result = productService.wishListDelete(productsaveNo);
        if( result == 0 ) return "redirect:/product/wishlist";
        return "redirect:/product/wishlist";
    }
   
    //-------------------- 장바구니 --------------------
    // 장바구니 목록
    @GetMapping("/cart")
    public String cartlist(Model model, Principal principal) throws Exception {
        String userId = principal.getName();
        Users users = userService.selectById(userId);
        int userNo = users.getUserNo();
        List<Product> cartList = productService.cartList(userNo);
        model.addAttribute("cartList", cartList);
        return "product/cart";
    }
    // 장바구니 담기
    @GetMapping(value="/addcart")
    public String addCart(Product product) throws Exception {
        int result = productService.addCart(product);
        log.info("장바구니에 넣기 성공여부 : "+ result);
        return "redirect:/product/cart";
    }

    //장바구니에 담기(ajax)
    @ResponseBody
    @GetMapping(value="/addProductsaveAjax")
    public String addCartAjax(Product product) throws Exception {
        int result = productService.addCartAjax(product);
        log.info("장바구니에 넣기 성공여부 : "+ result);
        if (result > 0) return "SUCCESS";
        else return "FAIL";
    }

    //찜목록 전부다 장바구니에 담기
    @GetMapping(value="/addcartAll")
    public String addcartAll(Principal principal) throws Exception {
        String userId = principal.getName();
        Users users = userService.selectById(userId);
        int userNo = users.getUserNo();
        int result = productService.addcartAll(userNo);
        log.info("장바구니에 담긴 상품 갯수 : " + result);
        return "redirect:/product/cart";
    }
    // 장바구니 삭제
    @PostMapping(value="/cartDelete")
    public String cartListDelete(Product product)  throws Exception {
       int cartNo = product.getCartNo();
       int result = productService.cartListDelete(cartNo);
        return "redirect:/product/cart";
    }
    
}
