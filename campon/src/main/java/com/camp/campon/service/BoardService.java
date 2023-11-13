package com.camp.campon.service;

import java.util.List;

import com.camp.campon.dto.Board;
import com.camp.campon.dto.Camp;

public interface BoardService {
    
    // 리뷰 최신글
    public List<Board> newReviewList() throws Exception;
    // 해당 캠핑장 리뷰 1개
    public Board productsreview(int campNo) throws Exception;
    // 캠핑 리뷰 목록
    public List<Board> crlist() throws Exception;
    // 캠핑 리뷰 조회
    public Board crread(int reviewNo) throws Exception;

    // 상품리뷰 최신글
    public List<Board> newprlist() throws Exception;
    // 상품리뷰 목록
    public List<Board> prlist() throws Exception;
    // 상품 리뷰 조회
    public Board prread(int prNo) throws Exception;

    // 예약정보 가져오기
    public Camp reservation(int reservationNo) throws Exception;
    // 대여정보 가져오기
    public Board order(int orderNo) throws Exception;

    //캠핑 리뷰 등록
    public int crinsert(Board board) throws Exception;
    //상품 리뷰 등록
    public int prinsert(Board board) throws Exception;

    //캠핑 리뷰 수정 / 삭제
    public int crupdate(Board board) throws Exception;
    public int crdelete(int reviewNo) throws Exception;
    public int crdeletelist(int reservationNo) throws Exception;
    public int crdeletecampNo(int campNo) throws Exception;
    public int crdeletecpdtNo(int cpdtNo) throws Exception;
    //상품 리뷰 수정 / 삭제
    public int prupdate(Board board) throws Exception;
    public int prdelete(int prNo) throws Exception;

    // 캠핑 리뷰 목록(페이징)
    public List<Board> crlistpage(Board board) throws Exception;
    public int crlistcount() throws Exception;
    //상품리뷰목록(페이징)
    public List<Board> prlistpage(Board board) throws Exception;
    public int prlistcount() throws Exception;

    //유저 리뷰 리스트
    public List<Board> usercrlist(int userNo) throws Exception;
    public List<Board> userprlist(int userNo) throws Exception;

    //판매자 리뷰 리스트
    public List<Board> campreviewlist(int userNo) throws Exception;
}
