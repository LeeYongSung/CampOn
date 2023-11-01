package com.camp.campon.dto;

import java.util.Date;

import lombok.Data;

@Data
public class Board {
    private int reviewNo;
    private int userNo;
    private int campNo;
    private int reservationNo;
    private String reviewImg;
    private String reviewCon;
    private Date regDate;
    private Date updDate;
    private int camptypeNo;  //캠핑종류번호가 왜들어가있지??

    private String campName;
}
