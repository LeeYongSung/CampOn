package com.camp.campon.service;

import java.util.List;

import com.camp.campon.dto.Camp;

public interface CampService {

    // 상품 최신목록
    public List<Camp> newList() throws Exception;

    // 상품 추천목록
    public List<Camp> hotList() throws Exception;    

    // 즐겨찾기 목록
    public List<Camp> favoritesList() throws Exception;
    public int favoriteDelete(int favoritesNo) throws Exception;

    // 캠핑장 목록
    public List<Camp> campSelect(int campTypeNo) throws Exception;

    // 캠핑 종류
    public List<Camp> camptype() throws Exception;

    // 캠핑장 페이지
    public List<Camp> productsimg(int campNo) throws Exception;
    public Camp productsproducts(int campNo) throws Exception;
    public int productsreserve(int campNo) throws Exception;   
    // 예약 일정
    public List<Camp> reservation(Camp camp) throws Exception;
    
    // 오픈 일정
    public List<Camp> schedule(Camp camp) throws Exception;
    
    // 캠핑 상세
    public List<Camp> cpdtList(int cpdtNo) throws Exception;
}
