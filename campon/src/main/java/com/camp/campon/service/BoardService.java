package com.camp.campon.service;

import java.util.List;

import com.camp.campon.dto.Board;

public interface BoardService {
        // 리뷰 최신글
    public List<Board> newReviewList() throws Exception;
    public Board productsreview(int campNo) throws Exception;
}
