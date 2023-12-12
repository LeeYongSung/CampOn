package com.camp.campon.dto;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Camp {

    // 캠핑장
    private int campNo;
    private int regionNo;
    private String campName;
    private String campAddress;
    private String campTel;
    private String campLocation;
    private String campOpen;
    private String campClose;
    private String campPeriod;
    private String campCaution;
    private String campIntroduction;
    private String campLayout;

    // 공통 날짜
    private Date regDate;
    private Date updDate;

    // 캠핑장 상품
    private int cpdtNo;
    private String cpdtName;
    private String cpdtIntroduction;
    private int cpdtNop;
    private String cpdtSize;
    private Integer cpdtPrice;
    private String cpdtPriceStr;


    // 캠핑종류
    private int campTypeNo;
    private String campTypeName;
    private String campTypeImg;
    
    // 환경
    private int environmentNo;
    
    // 환경 종류
    private int environmentTypeNo;
    private String environmentTypeName;
    
    // 캠핑장 이미지
    private int cpiNo;
    private String cpiUrl;
    private String cpdiUrl;
    
    // 즐겨찾기번호
    private int favoritesNo;
    
    // 오픈 시작일 / 종료일 체크
    private String startDate;
    private String endDate;
    
    // 시설물
    private int facilitytypeNo;
    private String facilitytypeName;
    private String facilitytypeImg;
    
    // 예약
    // 유저
    private int reservationNo;
    private int reservationNop;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date reservationStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date reservationEnd;
    private int reservationDate;
    private int userNo;
    private String userName;
    private String campPaymentType;
    private String userTel; 

    private List<MultipartFile> cpdiFiles;
    
    private List<MultipartFile> file;
    private MultipartFile layoutFile;

    private List<Camp> detailsList;

    // 검색
    private String keyword;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date searchDate;

    // 캠핑 타입 리스트 받아오기
    private List<String> checkBoxList;

    public Camp() {
        this.searchDate = new Date();
    }


    // 위치값
    private String campLatitude;
    private String campLongitude;
}
