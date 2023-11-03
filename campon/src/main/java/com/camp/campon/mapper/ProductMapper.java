package com.camp.campon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.camp.campon.dto.Product;

@Mapper
public interface ProductMapper {
    //카테고리별 상품목록
    public List<Product> getCategoryList(String category) throws Exception;
    // 상품 등록
    public int productInsert(Product product) throws Exception;
    //상세이미지 등록
    public int insertImgs(Product product) throws Exception;
    //상품번호 최댓값
    public int maxPk() throws Exception;
    
}
