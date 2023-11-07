package com.camp.campon.service;

import java.util.List;

import com.camp.campon.dto.Board;

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
}
