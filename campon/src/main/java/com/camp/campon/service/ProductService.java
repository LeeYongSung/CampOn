package com.camp.campon.service;

import java.util.List;

import com.camp.campon.dto.Product;

public interface ProductService {
    
    //카테고리별 상품목록
    public List<Product> getCategoryList(String category) throws Exception;
   //상품 하나 선택
   public Product select(int productNo) throws Exception;
    // 상품 등록
    public int productInsert(Product product) throws Exception;
    //상품 수정
    public int productUpdate(Product product) throws Exception;

    // 찜 목록
    public List<Product> wishList();

    // 찜 삭제
    public int wishListDelete(int wishlistNo);

    // 장바구니 목록
    public List<Product> cartList();

    // 장바구니 삭제
    public int cartListDelete(int cartlistNo);

    // 상품 상세페이지
    public List<Product> productimg(Integer productNo);
}
