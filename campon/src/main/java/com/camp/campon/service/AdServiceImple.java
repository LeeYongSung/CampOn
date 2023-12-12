package com.camp.campon.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.camp.campon.dto.Ad;
import com.camp.campon.mapper.AdMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdServiceImple implements AdService {
    
    @Autowired
    AdMapper adMapper;

    @Value("${upload.path}")
    private String uploadPath;
    
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
        MultipartFile adimg = ad.getAdImgFile();

        String newPath = "/img/camp";
        String OriginName = adimg.getOriginalFilename();
        String FileName = UUID.randomUUID().toString() + "_" + OriginName;
        String FilePath = newPath + "/" + FileName;
        byte[] FileData = adimg.getBytes();
        
        File UploadFile = new File(uploadPath, FileName);
        FileCopyUtils.copy(FileData, UploadFile);           // 파일 업로드 기능 구현

        ad.setAdImg(FilePath);

        int result = adMapper.adinsert(ad);
        return result;
    }

    @Override
    public Ad adview(int adNo) throws Exception {
        Ad adview = adMapper.adview(adNo);
        return adview;
    }

    @Override
    public int adupdate(Ad ad) throws Exception {
        MultipartFile adimg = ad.getAdImgFile();

        String newPath = "/img/camp";
        String OriginName = adimg.getOriginalFilename();
        String FileName = UUID.randomUUID().toString() + "_" + OriginName;
        String FilePath = newPath + "/" + FileName;
        byte[] FileData = adimg.getBytes();
        
        File UploadFile = new File(uploadPath, FileName);
        FileCopyUtils.copy(FileData, UploadFile);           // 파일 업로드 기능 구현

        ad.setAdImg(FilePath);
        
        int result = adMapper.adupdate(ad);
        return result;
    }


    
}
