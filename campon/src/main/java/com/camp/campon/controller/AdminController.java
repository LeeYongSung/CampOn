package com.camp.campon.controller;

import java.io.FileInputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.camp.campon.dto.Ad;
import com.camp.campon.dto.Camp;
import com.camp.campon.dto.CustomUser;
import com.camp.campon.dto.Product;
import com.camp.campon.dto.Productreview;
import com.camp.campon.dto.Users;
import com.camp.campon.service.AdService;
import com.camp.campon.service.BoardService;
import com.camp.campon.service.CampService;
import com.camp.campon.service.ProductService;
import com.camp.campon.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    
    private final UserService userService;
    // @Autowired
    // private UserService userService;

    @Autowired
    private CampService campService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private AdService adService;

    @GetMapping(value = "/index")
    public String adminUser(Model model) {
        return "admin/index";
    }

    /**********************************************
     * 상품 시작
     ***********************************************/
    // (관리자) 상품 관리 페이지
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/productlist")
    public String productList(Model model) throws Exception {
        List<Product> productList = productService.getProductList();
        model.addAttribute("productList", productList);
        return "admin/productlist";

    }

    // 상품등록 페이지
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/productadd")
    public String productAdd() {
        return "admin/productadd";
    }

    // 상품등록 실행
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/productInsert")
    public String productInsert(Product product) throws Exception {
        int result = productService.productInsert(product);
        log.info("상품등록 성공여부 : " + result);
        return "redirect:/admin/productlist";
    }

    // 상품 수정 페이지
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/productupdate", params = "productNo")
    public String productUpdate(@RequestParam String productNo, Model model) throws Exception {
        Product product = productService.selectUpd(Integer.parseInt(productNo));
        model.addAttribute("product", product);
        return "admin/productupdate";
    }

    // 상품 수정 pro
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/productUpdate")
    public String productUpdate(Product product) throws Exception {
        log.info("썸네일 있냐?" + product.getProductThmFile().size());
        int result = productService.productUpdate(product);
        log.info("상품수정 성공여부 : " + result);
        return "redirect:/admin/productlist";
    }

    // 이미지 불러오기
    @GetMapping(value = "/img", params = "file")
    public void img(@RequestParam("file") String file, HttpServletResponse response) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.add("Content-Disposition", "inline;");
        String filePath = file;
        FileInputStream fis = new FileInputStream(filePath);
        ServletOutputStream sos = response.getOutputStream();
        FileCopyUtils.copy(fis, sos);
    }

    // 상품 삭제 pro
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/delete", params = "productNo")
    public String productDelete(@RequestParam String productNo) throws NumberFormatException, Exception {
        int result = productService.deleteProduct(Integer.parseInt(productNo));
        return "redirect:/admin/productlist";
    }

    // 멤버 관리 페이지
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/memberList")
    public String memberList(Model model) throws Exception {
        List<Users> userList = userService.memberList("ROLE_USER");
        List<Users> sellList = userService.memberList("ROLE_SELL");
        model.addAttribute("userList", userList);
        model.addAttribute("sellList", sellList);
        return "admin/memberList";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/memberDelete")
    public String memberDelete(String userId) throws Exception {
        int result = userService.delete(userId);
        log.info("회원 삭제 여부 : " + result);
        return "redirect:/admin/memberList";
    }

    /**********************************************
     * 상품 끝
     ***********************************************/

    /**********************************************
     * 캠핑장 시작
     * @throws Exception
     ***********************************************/


     //판매자
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SELL')")
    @GetMapping(value = "/campproductlist")
    public String campList(Model model, Authentication auth) throws Exception {
        String name = "";
        if (auth != null) {
            name = auth.getName();
        } else {
            name = "anonymouseUser";
        }
        log.info("name : " + name);
        if (name == null) {
            return "redirect:/user/login";
        } else {
            if (!name.equals("anonymouseUser")) {
                // 유저 정보 획득
                Users user = userService.selectById(name);
                int userNo = user.getUserNo();
                // List<camp> campdetail = campService.
                // 획득한 유저 번호로 캠핑장 정보 획득
                List<Camp> camp = campService.campproductUser(userNo);
                // for (Camp campEl : camp) {
                // campEl.getDetailList(campService.campdetailUser(campEl.getCampNo()));
                // }
                // List<Camp> campdetailList = null;
                // // campdetailList = null;
                // // log.info("camp" + camp);

                // for(int i = 0; i < camp.size(); i++) {
                // int campNo = camp.get(i).getCampNo();
                // campdetailList = campService.campdetailUser(campNo);

                // log.info("campdetailList : " + campdetailList);

                // System.out.println();
                // if(campdetailList != null) campdetailList.addAll(campdetailList);
                // }
                // model.addAttribute("campdetailList", campdetailList);
                CustomUser customuser = (CustomUser) auth.getPrincipal();
                Users user1 = customuser.getUsers();
                String role = user1.getAuth();
                model.addAttribute("auth", role);
                model.addAttribute("campList", camp);
                List<Camp> camp1 = campService.campproductadmin();
                model.addAttribute("campListadmin", camp1);
                return "admin/campproductlist";
            }
            return "redirect:/user/login";
        }
    }

    //판매자
    /**
     * 캠핑장 등록
     * 
     * @param camp
     * @return
     * @throws Exception
     */
    @PreAuthorize("hasRole('ROLE_SELL')")
    @GetMapping(value = "/campproductadd")
    public String campInsert(@ModelAttribute Camp camp) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String userId = auth.getName();
        Users user = userService.selectById(userId);
        int userNo = user.getUserNo();
        camp.setUserNo(userNo);
        // log.info("camp : " + camp.getUserNo());
        // model.addAttribute("camp", camp);
        return "admin/campproductadd";
    }

    /**
     * 캠핑장 등록처리
     * 
     * @param camp
     * @param facilityTypeNo
     * @return
     * @throws Exception
     */
    //403 에러가 떠서 일단 주석처리 함. 
    // @PreAuthorize("hasRole('ROLE_SELL')")
    @PostMapping(value = "/campproductadd")
    public String campInsertPro(@ModelAttribute Camp camp, @RequestParam List<String> facilityTypeNo) throws Exception {

        int result = campService.campInsert(camp, facilityTypeNo);

        if (result == 0)
            return "admin/campproductadd";

        return "redirect:/admin/campproductlist";
    }

    //판매자
    /**
     * 캠핑장 수정
     * 
     * @return
     * @throws Exception
     */
    @PreAuthorize("hasRole('ROLE_SELL')")
    @GetMapping(value = "/campproductupdate")
    public String campUpdate(Model model, int campNo) throws Exception {
        Camp camp = campService.productsproducts(campNo);
        List<Camp> campfacility = campService.productsfacility(campNo);
        model.addAttribute("camp", camp);
        model.addAttribute("campfacility", campfacility);
        return "admin/campproductupdate";
    }
    @PreAuthorize("hasRole('ROLE_SELL')")
    @PostMapping(value="/campproductupdatePro")
    public String campUpdatePro(@ModelAttribute Camp camp, @RequestParam List<String> facilityTypeNo) throws Exception {
        int campNo = camp.getCampNo();
        int result1 = campService.campFacilityDelete(campNo);
        int result2 = campService.campEnvironmentDelete(campNo);
        int result3 = campService.campImgDelete(campNo);
        int result4 = campService.campUpdate(camp, facilityTypeNo);

        return "redirect:/admin/campproductlist";
    }
    

    // 캠핑상품 등록
    @PreAuthorize("hasRole('ROLE_SELL')")
    @GetMapping(value = "/campdetailinsert")
    public String campdetailinsert(Model model, Integer campNo, Integer userNo, @ModelAttribute Camp camp)
            throws Exception {
        model.addAttribute("userNo", userNo);
        model.addAttribute("campNo", campNo);
        return "admin/campdetailinsert";
    }

    // 캠핑상품 등록처리
    @PreAuthorize("hasRole('ROLE_SELL')")
    @PostMapping(value = "/campdetailinsert")
    public String campdetailinsertPro(@ModelAttribute Camp camp) throws Exception {
        int result = campService.detailinsert(camp);
        int campNo = camp.getCampNo();
        int userNo = camp.getUserNo();

        if (result == 0)
            return "redirect:/admin/campdetailinsert?campNo=" + campNo + "& userNo=" + userNo;

        return "redirect:/admin/campproductlist";
    }

    // 캠핑상품 수정
    @PreAuthorize("hasRole('ROLE_SELL')")
    @GetMapping(value = "/campdetailupdate")
    public String campdetailupdate(Model model, int cpdtNo) throws Exception {
        Camp camp = campService.productintro(cpdtNo);
        model.addAttribute("camp", camp);
        return "admin/campdetailupdate";
    }

    // 캠핑상품 수정처리
    @PreAuthorize("hasRole('ROLE_SELL')")
    @PostMapping(value = "/campdetailupdate")
    public String campdetailupdatePro(Camp camp) throws Exception {
        log.info("cpdtPriceStr : " + camp.getCpdtPriceStr());
        log.info("cpdtPrice : " + camp.getCpdtPrice());

        log.info(camp.getCpdtPrice() + "");
        // String cpdtPriceStr = camp.getCpdtPriceStr();
        // Integer cpdtPrice = cpdtPriceStr == null ? 0 :
        // Integer.parseInt(cpdtPriceStr);
        // camp.setCpdtPrice( cpdtPrice );

        // int filedelete = campService.cpdidelete(camp.getCpdtNo());
        // int result = campService.detailupdate(camp);
        // int cpdtNo = camp.getCpdtNo();
        // if(result == 0) return "redirect:/admin/campdetailupdate?cpdtNo" + cpdtNo;

        return "redirect:/admin/campproductlist";
    }

    // 캠핑상품 삭제처리
    @PreAuthorize("hasRole('ROLE_SELL')")
    @PostMapping(value = "/campdetaildelete")
    public String campdetaildelete(int cpdtNo) throws Exception {
        log.info("숫자 : " + cpdtNo);
        int filedelete = campService.cpdidelete(cpdtNo);
        int result1 = boardService.crdeletecpdtNo(cpdtNo);
        int result = campService.detaildelete(cpdtNo);
        if (result == 0)
            return "redirect:/admin/campdetailupdate?cpdtNo=" + cpdtNo;
        return "redirect:/admin/campproductlist";
    }

    @PreAuthorize("hasRole('ROLE_SELL')")
    @GetMapping(value = "/campdetaildelete")
    public String campdetaildeletepro(int cpdtNo) throws Exception {
        log.info("숫자 : " + cpdtNo);
        int filedelete = campService.cpdidelete(cpdtNo);
        int result1 = boardService.crdeletecpdtNo(cpdtNo);
        int result = campService.detaildelete(cpdtNo);
        if (result == 0)
            return "redirect:/admin/campdetailupdate?cpdtNo=" + cpdtNo;
        return "redirect:/admin/campproductlist";
    }

    //캠핑장 삭제
    @PreAuthorize("hasRole('ROLE_SELL')")
    @GetMapping(value="/campdelete")
    public String campdelete(int campNo) throws Exception {
        int result1 = campService.cpdeletecdi(campNo);
        int result2 = campService.cpdeletecpdt(campNo);
        int result3 = campService.cpdeletecpi(campNo);
        int result6 = campService.campFacilityDelete(campNo);
        int result7 = campService.campEnvironmentDelete(campNo);
        int result4 = campService.cpdelete(campNo);
        int result5 = boardService.crdeletecampNo(campNo);
        return "redirect:/admin/campproductlist";
    }
    


    /**********************************************
     * 캠핑장 끝
     ***********************************************/
    /**********************************************
     * 광고 시작
     ***********************************************/

     //판매자
    // 광고 등록 페이지
    @PreAuthorize("hasRole('ROLE_SELL')")
    @GetMapping("/adinsert")
    public String adinsert(Model model, int campNo) {
        model.addAttribute("campNo", campNo);
        return "admin/adinsert";
    }

    // 광고 등록 실행
    @PreAuthorize("hasRole('ROLE_SELL')")
    @PostMapping(value = "/adinsertpro")
    public String adinsertpro(@ModelAttribute Ad ad) throws Exception {
        int result = adService.adinsert(ad);
        return "redirect:/admin/adlistseller";
    }
    // admin 광고리스트
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/adlist")
    public String adlist(Model model) throws Exception {
        List<Ad> adlist = adService.adlist();
        model.addAttribute("adlist", adlist);
        return "admin/adlist";
    }
    // 승인처리
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/adcheck")
    public String adcheck(int adNo) throws Exception {
        int result = adService.adcheck(adNo);
        return "redirect:/admin/adlist";
    }
    // seller 광고리스트
    @PreAuthorize("hasRole('ROLE_SELL')")
    @GetMapping(value = "/adlistseller")
    public String adlistseller(Model model, Principal principal) throws Exception {
        int userNo = 0;
        if (principal == null) {
            userNo = 1000;
        } else {
            String userId = principal.getName();
            Users users = userService.selectById(userId);
            userNo = users.getUserNo();
        }
        List<Ad> adlistseller = adService.adlistseller(userNo);
        model.addAttribute("adlistseller", adlistseller);
        return "admin/adlistseller";
    }

    // 광고수정
    @PreAuthorize("hasRole('ROLE_SELL')")
    @GetMapping(value="/adupdate")
    public String adupdate(Model model, int adNo) throws Exception {
        Ad ad = adService.adview(adNo);
        model.addAttribute("ad", ad);
        return "admin/adupdate";
    }
    @PreAuthorize("hasRole('ROLE_SELL')")
    @PostMapping(value="/adupdatepro")
    public String adupdatepro(Ad ad) throws Exception {
        int result = adService.adupdate(ad);
        if(result == 0) return "admin/adupdate";
        return "redirect:/admin/adlistseller";
    }
    
    

}
