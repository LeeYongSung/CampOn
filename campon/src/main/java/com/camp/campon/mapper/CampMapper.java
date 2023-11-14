package com.camp.campon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.camp.campon.dto.Camp;

@Mapper
public interface CampMapper {

    // 상품 최신목록
    public List<Camp> newList() throws Exception;

    //상품 추천목록
    public List<Camp> hotList() throws Exception;

    // 찜 목록
    public List<Camp> favoritesList() throws Exception;
    // 찜 목록 삭제
    public int favoriteDelete(int favoritesNo) throws Exception;
    
    // 메뉴 선택시 해당 목록 출력
    public List<Camp> campSelect(int campTypeNo) throws Exception;

    // 캠핑 종류
    public List<Camp> camptype() throws Exception;

    // 캠핑장 페이지
    public List<Camp> productsimg(int campNo) throws Exception;
    public Camp productsproducts(int campNo) throws Exception;
    public int productsreserve(int campNo) throws Exception;
    public List<Camp> productsenvironment(int campNo) throws Exception;
    public List<Camp> productsfacility(int campNo) throws Exception;
    public List<Camp> productsproductlist(int campNo) throws Exception;

    // 예약 현황 조회
    public List<Camp> reservation(Integer userNo) throws Exception;
    public List<Camp> reservationNow(Integer userNo) throws Exception;
    public Camp reservate(int cpdtNo) throws Exception;

    // 오픈 일정
    public List<Camp> schedule(Camp camp) throws Exception;

    // 캠핑 상세
    public List<Camp> cpdtList(int cpdtNo) throws Exception;

    // 캠핑상품 페이지
    public List<Camp> productimg(int cpdtNo) throws Exception;
    public Camp productintro(int cpdtNo) throws Exception;

    // 예약완료 페이지
    public Camp reservecomplete(String userId) throws Exception;

    // 캠핑상품 등록
    public int detailinsert(Camp camp) throws Exception;
    public int cpdiinsert(Camp camp) throws Exception;

    // 캠핑상품 번호최댓값
    public int maxdetailNo() throws Exception;
    
    // 상품 등록 유저 확인
    public List<Camp> campproductUser(Integer userNo) throws Exception;
    public List<Camp> campproductadmin() throws Exception;
    public List<Camp> campdetailUser(Integer campNo) throws Exception;

    //캠핑상품 수정
    public int detailupdate(Camp camp) throws Exception;

    //캠핑상품 삭제
    public int detaildelete(int cpdtNo) throws Exception;
    public int cpdidelete(int cpdtNo) throws Exception;
    // 캠핑장 등록
    public int campInsert(Camp camp) throws Exception;

    // 캠핑장 번호 최댓값
    public int maxcampNo() throws Exception;

    // 캠핑장 이미지
    public int campImgInsert(Camp camp) throws Exception;

    // 캠핑장 시설물 등록
    public int campFacilityInsert(Camp camp) throws Exception;

    // 캠핑장 환경 등록
    public int campEnvironmentInsert(Camp camp) throws Exception;

    // 예약 진행
    public int reservateInsert(Camp camp) throws Exception;

    // 상품 검색
    public List<Camp> campSearch(Camp camp) throws Exception;

    //예약 목록 삭제
    public int reservationdelete(int reservationNo) throws Exception;

    //캠핑장 삭제
    public int cpdelete(int campNo) throws Exception;
    public int cpdeletecpi(int campNo) throws Exception;
    public int cpdeletecpdt(int campNo) throws Exception;
    public int cpdeletecdi(int campNo) throws Exception;

    //캠핑장 수정
    public int campUpdate(Camp camp) throws Exception;
    public int campFacilityDelete(int campNo) throws Exception;
    public int campImgDelete(int campNo) throws Exception;
    public int campEnvironmentDelete(int campNo) throws Exception;

}
