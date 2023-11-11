package com.camp.campon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camp.campon.dto.Ad;
import com.camp.campon.mapper.AdMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdServiceImple implements AdService {
    
    @Autowired
        AdMapper adMapper;

    
    // 광고리스트
    @Override
    public List<Ad> adlist() throws Exception {
        List<Ad> adlist = adMapper.adlist();
        return adlist;
    }

    // 승인
    @Override
    public int adcheck(int adNo) throws Exception {
        int result = adMapper.adcheck(adNo);
        return result;
    }

    @Override
    public List<Ad> adlistseller(int userNo) throws Exception {
        List<Ad> adlistseller = adMapper.adlistseller(userNo);
        return adlistseller;
    }

    // 광고 등록
    @Override
    public int adinsert(Ad ad) throws Exception {
        
        return adinsert;
    }


    
}
