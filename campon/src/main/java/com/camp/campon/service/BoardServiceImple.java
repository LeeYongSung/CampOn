package com.camp.campon.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.camp.campon.dto.Board;
import com.camp.campon.dto.Camp;
import com.camp.campon.mapper.BoardMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardServiceImple implements BoardService {

    @Autowired
    private BoardMapper boardMapper;

    @Value("${upload.path}")
    private String uploadPath;

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

    @Override
    public Camp reservation(int reservationNo) throws Exception {
        Camp reservation = boardMapper.reservation(reservationNo);
        return reservation;
    }

    @Override
    public int crinsert(Board board) throws Exception {
        MultipartFile reviewImgfile = board.getReviewImgfile();
        
        String originName = reviewImgfile.getOriginalFilename();
        byte[] fileData = reviewImgfile.getBytes();
        String fileName = UUID.randomUUID().toString() + "_" + originName;
        String filePath = uploadPath + "/" + fileName;
        
        File uploadFile = new File(uploadPath, fileName);
        FileCopyUtils.copy(fileData, uploadFile);
        
        board.setReviewImg(filePath);
        
        int result = boardMapper.crinsert(board);
        
        return result;
    }
    
    @Override
    public Board order(int orderNo) throws Exception {
        Board order = boardMapper.order(orderNo);
        return order;
    }
    @Override
    public int prinsert(Board board) throws Exception {
        MultipartFile prImgfile = board.getPrImgfile();
        
        String originName = prImgfile.getOriginalFilename();
        byte[] fileData = prImgfile.getBytes();
        String fileName = UUID.randomUUID().toString() + "_" + originName;
        String filePath = uploadPath + "/" + fileName;
        
        File uploadFile = new File(uploadPath, fileName);
        FileCopyUtils.copy(fileData, uploadFile);

        board.setPrImg(filePath);

        int result = boardMapper.prinsert(board);

        return result;
    }

    @Override
    public int crupdate(Board board) throws Exception {
        MultipartFile reviewImgfile = board.getReviewImgfile();
        
        String originName = reviewImgfile.getOriginalFilename();
        byte[] fileData = reviewImgfile.getBytes();
        String fileName = UUID.randomUUID().toString() + "_" + originName;
        String filePath = uploadPath + "/" + fileName;
        
        File uploadFile = new File(uploadPath, fileName);
        FileCopyUtils.copy(fileData, uploadFile);
        
        board.setReviewImg(filePath);
        
        int result = boardMapper.crupdate(board);

        return result;
    }

    @Override
    public int crdelete(int reviewNo) throws Exception {
        int result = boardMapper.crdelete(reviewNo);
        return result;
    }

    @Override
    public int prupdate(Board board) throws Exception {
        MultipartFile prImgfile = board.getPrImgfile();
        
        String originName = prImgfile.getOriginalFilename();
        byte[] fileData = prImgfile.getBytes();
        String fileName = UUID.randomUUID().toString() + "_" + originName;
        String filePath = uploadPath + "/" + fileName;
        
        File uploadFile = new File(uploadPath, fileName);
        FileCopyUtils.copy(fileData, uploadFile);
        
        board.setPrImg(filePath);
        
        int result = boardMapper.prupdate(board);

        return result;
    }

    @Override
    public int prdelete(int prNo) throws Exception {
        int result = boardMapper.prdelete(prNo);
        return result;
    }

    @Override
    public List<Board> crlistpage(Board board) throws Exception {
        List<Board> crlistpage = boardMapper.crlistpage(board);
        return crlistpage;
    }
    @Override
    public int crlistcount() throws Exception{
        int crlistcount = boardMapper.crlistcount();
        return crlistcount;
    }

    @Override
    public List<Board> prlistpage(Board board) throws Exception {
        List<Board> prlistpage = boardMapper.prlistpage(board);
        return prlistpage;
    }

    @Override
    public int prlistcount() throws Exception {
        int prlistcount = boardMapper.prlistcount();
        return prlistcount;
    }

    @Override
    public List<Board> usercrlist(int userNo) throws Exception {
        List<Board> usercrlist = boardMapper.usercrlist(userNo);
        return usercrlist;
    }

    @Override
    public List<Board> userprlist(int userNo) throws Exception {
        List<Board> userprlist = boardMapper.userprlist(userNo);
        return userprlist;
    }

    @Override
    public List<Board> campreviewlist(int userNo) throws Exception {
        List<Board> campreviewlist = boardMapper.campreviewlist(userNo);
        return campreviewlist;
    }

    @Override
    public int crdeletelist(int reservationNo) throws Exception {
        int result = boardMapper.crdeletelist(reservationNo);
        return result;
    }

    @Override
    public int crdeletecampNo(int campNo) throws Exception {
        int result = boardMapper.crdeletecampNo(campNo);
        return result;
    }

    @Override
    public int crdeletecpdtNo(int cpdtNo) throws Exception {
        int result = boardMapper.crdeletecpdtNo(cpdtNo);
        return result;
    }

    
}
