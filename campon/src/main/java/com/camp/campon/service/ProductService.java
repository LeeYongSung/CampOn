package com.camp.campon.service;

import java.util.List;

import com.camp.campon.dto.Product;

public interface ProductService {
    
    //카테고리별 상품목록
    public List<Product> getCategoryList(String category) throws Exception;

    // 상품 등록
    public int productInsert(Product product) throws Exception;

    // 찜 목록
    public List<Product> wishlist();

    // 찜 삭제
    public int wishlistDelete(int wishlistNo);
}
