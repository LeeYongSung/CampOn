package com.camp.campon.service;

import com.camp.campon.dto.Product;

public interface ProductService {
    
    // 상품 등록
    public int productInsert(Product product) throws Exception;
}
