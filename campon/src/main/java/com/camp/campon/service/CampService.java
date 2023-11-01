package com.camp.campon.service;

import java.util.List;

import com.camp.campon.dto.Camp;

public interface CampService {
    //상품 최신목록
    public List<Camp> newList() throws Exception;
    //상품 추천목록
    public List<Camp> hotList() throws Exception;    

    public List<Camp> favoritesList() throws Exception;
}
