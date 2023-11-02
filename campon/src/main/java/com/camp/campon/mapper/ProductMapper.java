package com.camp.campon.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.camp.campon.dto.Product;

@Mapper
public interface ProductMapper {
        // 상품 등록
    public int productInsert(Product product) throws Exception;
    
}
