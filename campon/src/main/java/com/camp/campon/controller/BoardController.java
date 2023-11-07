package com.camp.campon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.camp.campon.dto.Board;
import com.camp.campon.service.BoardService;


@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {
    
    @Autowired
    private BoardService boardService;

    @GetMapping(value="/index")
    public String index(Model model) throws Exception {
        List<Board> newReviewList = boardService.newReviewList();
        model.addAttribute("newReviewList", newReviewList);
        List<Board> crlist = boardService.crlist();
        model.addAttribute("crlist", crlist);
        List<Board> newprlist = boardService.newprlist();
        model.addAttribute("newprlist", newprlist);
        List<Board> prlist = boardService.prlist();
        model.addAttribute("prlist", prlist);
        return "board/index";
    }

    @GetMapping(value="/crread")
    public String crread(Model model, int reviewNo) throws Exception {
        Board crread = boardService.crread(reviewNo);
        model.addAttribute("crread", crread);
        return "board/crread";
    }
    @GetMapping(value="/prread")
    public String prread(Model model, int prNo) throws Exception {
        Board prread = boardService.prread(prNo);
        model.addAttribute("prread", prread);
        return "board/prread";
    }
    
    
}
