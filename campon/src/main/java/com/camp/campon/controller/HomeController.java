package com.camp.campon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;

import com.camp.campon.dto.Board;
import com.camp.campon.dto.Camp;
import com.camp.campon.service.BoardService;
import com.camp.campon.service.CampService;
import com.camp.campon.service.UserService;


@Slf4j
@Controller
@RequestMapping(value = {"", "/"})
public class HomeController {

    @Autowired
    private CampService campService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping(value="/")
    public String mainIndex(Model model) throws Exception {

        List<Camp> camptypeList = campService.camptype();
        List<Camp> campnewList = campService.newList();
        List<Camp> campHotList = campService.hotList();
        List<Board> newReviewList = boardService.newReviewList();

        log.info("camptypeList : " + camptypeList);
        model.addAttribute("camptypeList", camptypeList);
        model.addAttribute("campnewList", campnewList);
        model.addAttribute("campHotList", campHotList);
        model.addAttribute("newReviewList", newReviewList);

        return "camp/index";
    }
    
}
