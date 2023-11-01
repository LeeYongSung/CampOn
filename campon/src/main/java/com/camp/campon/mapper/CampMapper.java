package com.camp.campon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.camp.campon.dto.Camp;

@Mapper
public interface CampMapper {
    //상품 최신목록
    public List<Camp> newList() throws Exception;
    //상품 추천목록
    public List<Camp> hotList() throws Exception;

    //찜 목록
    public List<Camp> favoritesList() throws Exception;
}
