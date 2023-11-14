package com.camp.campon.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Ad {
    int adNo;
    int campNo;
    String adImg;
    MultipartFile adImgFile;
    String adStart;
    String adEnd;
    int adCheck;
    Date regDate;
    Date updDate;

    String campName;

}
