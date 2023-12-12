package com.camp.campon.controller;

import java.io.FileInputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    public String tempIndex() {

        return "camp/index";
    }
    

    @GetMapping(value="/index2")
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

        return "camp/index2";
    }



    //이미지 불러오기
    @GetMapping(value="/img", params="file")
    public void img(@RequestParam("file") String file, HttpServletResponse response) throws Exception  {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); 
        headers.add("Content-Disposition", "inline;"); 
        String filePath = file;
        FileInputStream fis = new FileInputStream(filePath);
        ServletOutputStream sos =  response.getOutputStream();
        FileCopyUtils.copy(fis, sos);
    }
    
}
