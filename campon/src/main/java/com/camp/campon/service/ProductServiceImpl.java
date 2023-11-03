package com.camp.campon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camp.campon.dto.Product;
import com.camp.campon.mapper.ProductMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    
    @Override
    public int productInsert(Product product) throws Exception {
        int result = productMapper.productInsert(product);
        return result;
    }

    @Override
    public List<Product> getCategoryList(String category) throws Exception {
        List<Product> productList = productMapper.getCategoryList(category);
        return productList;
    }
    
}
