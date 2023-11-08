package com.camp.campon.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Board {
    private int reviewNo;
    private int userNo;
    private int campNo;
    private int reservationNo;
    private int orderNo;
    private String reviewTitle;
    private MultipartFile reviewImgfile;
    private String reviewImg;
    private String reviewCon;
    private Date regDate;
    private Date updDate;
    private int cpdtNo;

    private String campName;
    private String cpdtName;
    private String userName;
    private String userId;

    private int prNo;
    private String prTitle;
    private String prCon;
    private String prImg;
    private MultipartFile prImgfile;

    private int productNo;
    private String productName;
    private String productCategory;

    private Date reservationStart;
    private Date reservationEnd;
    private Date startDate;
    private Date endDate;
}
