package com.camp.campon.dto;
import java.util.Date;
import lombok.Data;

@Data
public class Productreview {
    private Integer prNo;
    private Integer orderNo;
    private Integer userNo;
    private Integer productNo;
    private String prImg;
    private String prTitle;
    private String prCon;
    private Date regDate;
    private Date updDate;

    //기타
    private String productName;
    private String userId;
}
