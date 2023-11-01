package com.camp.campon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.camp.campon.dto.Camp;

@Mapper
public interface CampMapper {
    
    // 캠핑장 목록    
    public List<Camp> campList(Camp camp) throws Exception; 

}
