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

    @Override
    public Board productsreview(int campNo) throws Exception {
        Board productsreview = boardMapper.productsreview(campNo);
        return productsreview;
    }

    @Override
    public List<Board> crlist() throws Exception {
        List<Board> crlist = boardMapper.crlist();
        return crlist;
    }

    @Override
    public List<Board> newprlist() throws Exception {
        List<Board> newprlist = boardMapper.newprlist();
        return newprlist;
    }

    @Override
    public List<Board> prlist() throws Exception {
        List<Board> prlist = boardMapper.prlist();
        return prlist;
    }

    @Override
    public Board crread(int reviewNo) throws Exception {
        Board crread = boardMapper.crread(reviewNo);
        return crread;
    }

    @Override
    public Board prread(int prNo) throws Exception {
        Board prread = boardMapper.prread(prNo);
        return prread;
    }
    
}
