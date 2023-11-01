package com.camp.campon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camp.campon.dto.Camp;
import com.camp.campon.mapper.CampMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CampServiceImpl implements CampService{

    @Autowired
    private CampMapper campMapper;

    @Override
    public List<Camp> newList() throws Exception{
        List<Camp> campnewList = campMapper.newList();
        return campnewList;
    }
}
