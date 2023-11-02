package com.camp.campon.dto;

import java.util.Date;

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

    // 공통 날짜
    private Date regDate;
    private Date updDate;

    // 캠핑장 상품
    private int cpdtNo;
    private String cpdtName;
    private String cpdtIntroduction;
    private int cpdtNop;
    private String cpdtSize;
    private int cpdtPrice;

    // 유저
    private int userNo;

    // 캠핑종류
    private int campTypeNo;
    private String campTypeName;

    // 환경
    private int environmentNo;

    // 환경 종류
    private int environmentTypeNo;
    private String environmentTypeName;

    // 캠핑장 이미지
    private int cpiNo;
    private String cpiUrl;

    // 오픈 시작일 / 종료일 체크
    private String startDate;
    private String endDate;
}
