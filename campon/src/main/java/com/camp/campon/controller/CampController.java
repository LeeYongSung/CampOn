package com.camp.campon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.camp.campon.dto.Camp;

import lombok.extern.slf4j.Slf4j;

import com.camp.campon.dto.Camp;
import com.camp.campon.service.CampService;
import org.springframework.web.bind.annotation.RequestParam;



@Slf4j
@Controller
@RequestMapping("/camp")
public class CampController {

    @Autowired
    private CampService campService;

    @GetMapping(value="/index")
    public String campMain(Model model) throws Exception {
        List<Camp> campnewList = campService.newList();
        model.addAttribute("campnewList", campnewList);
        return "camp/index";
    }

    @GetMapping(value="/information")
    public String information() {
        return "camp/information";
    }
     
    @GetMapping(value="/insertProduct")
    public String insertProduct() {
        return "camp/insertProduct";
    }

    @GetMapping(value="/favorites")
    public String favoritesList(Model model) throws Exception {
        List<Camp> favoritesList = campService.favoritesList();
        model.addAttribute("favoritesList", favoritesList);
        return "camp/favorites";
    }
    
    
    @GetMapping(value="/campproduct")
    public String campProduct(Model model, int campTypeNo) throws Exception {
        List<Camp> campselect = campService.campSelect(campTypeNo);
        log.info("campselect" + campselect);
        model.addAttribute("campselect", campselect);
        return "camp/campproduct";
    }
    
    
}
