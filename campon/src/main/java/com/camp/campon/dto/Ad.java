package com.camp.campon.dto;

import java.util.Date;

import lombok.Data;

@Data
public class Ad {
    int adNo;
    int campNo;
    String adImg;
    Date adStart;
    Date adEnd;
    int adCheck;
    Date regDate;
    Date updDate;
    
}
