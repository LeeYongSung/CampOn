package com.camp.campon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.camp.campon.dto.Board;


@Mapper
public interface BoardMapper {

    // 리뷰 최신글
    public List<Board> newReviewList() throws Exception;
    public Board productsreview(int campNo) throws Exception;
}
