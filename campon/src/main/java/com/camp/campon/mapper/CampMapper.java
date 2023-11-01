package com.camp.campon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.camp.campon.dto.Camp;

@Mapper
public interface CampMapper {
    // 상품 최신목록
    public List<Camp> newList() throws Exception;

    // 찜 목록
    public List<Camp> favoritesList() throws Exception;

    // 메뉴 선택시 해당 목록 출력
    public List<Camp> campSelect(int campTypeNo) throws Exception;
}
