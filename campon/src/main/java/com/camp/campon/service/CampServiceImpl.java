package com.camp.campon.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Override
    public List<Camp> favoritesList() throws Exception {
        List<Camp> favoritesList = campMapper.favoritesList();
        return favoritesList;
    }

    @Override
    public List<Camp> campSelect(int campTypeNo) throws Exception {
        List<Camp> campselect = campMapper.campSelect(campTypeNo);
        log.info("campTypeNo : " + campselect);
        return campselect;
    }
    
    @Override
    public List<Camp> hotList() throws Exception {
        List<Camp> campHotList = campMapper.hotList();
        return campHotList;    
    }
    
    @Override
    public List<Camp> camptype() throws Exception {
        List<Camp> camptypeList = campMapper.camptype();
        
        return camptypeList;
    }
    
    @Override
    public int favoriteDelete(int favoritesNo) throws Exception {
        int result = campMapper.favoriteDelete(favoritesNo);
        return result;
    }
    
    
    // 캠핑장 페이지
    @Override
    public List<Camp> productsimg(int campNo) throws Exception {
        List<Camp> productsimg = campMapper.productsimg(campNo);
        return productsimg;
    }
    
    @Override
    public Camp productsproducts(int campNo) throws Exception {
        Camp productsproducts = campMapper.productsproducts(campNo);
        return productsproducts;
    }
    
    @Override
    public int productsreserve(int campNo) throws Exception {
        int productsreserve = campMapper.productsreserve(campNo);
        return productsreserve;
    }
    @Override
    public List<Camp> productsenvironment(int campNo) throws Exception {
        List<Camp> productsenvironment = campMapper.productsenvironment(campNo);
        return productsenvironment;
    }
    @Override
    public List<Camp> productsfacility(int campNo) throws Exception {
        List<Camp> productsfacility = campMapper.productsfacility(campNo);
        return productsfacility;
    }
    @Override
    public List<Camp> productsproductlist(int campNo) throws Exception {
        List<Camp> productsproductlist = campMapper.productsproductlist(campNo);
        return productsproductlist;
    }
    
    
    // 예약
    public List<Camp> reservation(Integer userNo) throws Exception {
        List<Camp> reservationList = campMapper.reservation(userNo);
        
        return reservationList;
    }
    
    @Override
    public List<Camp> schedule(Camp camp) throws Exception {
        List<Camp> scheduleList = campMapper.schedule(camp);

        return scheduleList;

    }

    @Override
    public List<Camp> productimg(int cpdtNo) throws Exception {
        List<Camp> productimg = campMapper.productimg(cpdtNo);
        return productimg;
    }

    @Override
    public Camp productintro(int cpdtNo) throws Exception {
        Camp productintro = campMapper.productintro(cpdtNo);
        return productintro;
    }

    @Override
    public Camp reservate(int cpdtNo) throws Exception {
        Camp camp = campMapper.reservate(cpdtNo);

        return camp;
    }
    @Override
    public Camp reservecomplete(String userId) throws Exception{
        Camp reservecomplete = campMapper.reservecomplete(userId);
        return reservecomplete;
    }

    //캠핑상품 등록
    @Override
    public int detailinsert(Camp camp) throws Exception{
        int result = campMapper.detailinsert(camp);
        String parentTable = "campdetail";
        int cpdtNo = campMapper.maxdetailNo();

        List<MultipartFile> fileList = camp.getFile();

        // if(!fileList.isEmpty())
        // for(MultipartFile file : fileList){
        //     if(file.isEmpty()) continue;
        //     String originName = 
        // }


        return result;
        
    }

}
