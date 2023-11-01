package com.camp.campon.service;

import java.util.List;

import com.camp.campon.dto.Camp;

public interface CampService {
    public List<Camp> newList() throws Exception;

    public List<Camp> favoritesList() throws Exception;

    public List<Camp> campSelect(int campTypeNo) throws Exception;
}
