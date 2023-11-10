package com.camp.campon.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.camp.campon.dto.Ad;



@Mapper
public interface AdMapper {

   public int adinsert(Ad ad) throws Exception;
}
