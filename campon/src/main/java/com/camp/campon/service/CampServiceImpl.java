package com.camp.campon.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.camp.campon.dto.Camp;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CampServiceImpl implements CampService {

    private CampService campservice;

    @Override
    public List<Camp> campList(Camp camp) throws Exception {
        

        return camp;
    }
    
}
