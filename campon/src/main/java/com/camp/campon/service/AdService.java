package com.camp.campon.service;

import java.util.List;

import com.camp.campon.dto.Ad;

public interface AdService {
    
   // 광고 리스트
   public List<Ad> adlist() throws Exception;

   // 승인
   public int adcheck(int adNo) throws Exception;

   // 광고리스트 (seller)
   public List<Ad> adlistseller(int userNo) throws Exception;

   // 광고등록
   public int adinsert(Ad ad) throws Exception;

   // 광고 조회
   public Ad adview(int adNo) throws Exception;

   // 광고 수정
   public int adupdate(Ad ad) throws Exception;   

}
