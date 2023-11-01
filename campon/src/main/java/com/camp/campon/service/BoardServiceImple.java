package com.camp.campon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camp.campon.dto.Board;
import com.camp.campon.mapper.BoardMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardServiceImple implements BoardService {

    @Autowired
    private BoardMapper boardMapper;

    @Override
    public List<Board> newReviewList() throws Exception {
        List<Board> newReviewList = boardMapper.newReviewList();
        return newReviewList;
    }
    
}
