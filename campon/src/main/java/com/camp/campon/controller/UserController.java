package com.camp.campon.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.camp.campon.dto.CustomUser;
import com.camp.campon.dto.Product;
import com.camp.campon.dto.Users;
import com.camp.campon.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PersistentTokenRepository persistentTokenRepository;

    // @GetMapping(value={"", "/"})
    // public String home(Model model, Principal principal) {
    // // Principal : 현재 로그인한 사용자의 정보를 확인하는 인터페이스
    // String loginId = principal != null ? principal.getName() : "guest";
    // // String loginId = principal.getName();
    // model.addAttribute("loginId", loginId);
    // return "index";
    // }

    // 로그인화면
    @GetMapping(value = "/login")
    public String login(@CookieValue(value = "remember-id", required = false) Cookie cookie, Model model) {
        // - required=false : 쿠키 필수 ❌ ➡ 쿠키가 없어도 에러❌ (null)
        String userId = "";
        boolean rememberId = false;
        if (cookie != null) {
            log.info("CookieName : " + cookie.getName());
            log.info("CookiValue : " + cookie.getValue());
            userId = cookie.getValue();
            rememberId = true;
        }
        model.addAttribute("userId", userId);
        model.addAttribute("rememberId", rememberId);
        return "user/login";
    }

    // 회원 가입 화면
    @GetMapping(value = "/join")
    public String join(Model model) throws Exception {
        List<String> userIdList = userService.userIds();
        model.addAttribute("userIdList", userIdList);
        return "user/join";
    }

    // 회원 가입 처리
    @PostMapping(value = "/join")
    public String joinPro(Users user, HttpServletRequest request) throws Exception {
        int result = userService.insert(user);
        log.info(result + "회원가입성공");
        // 회원 가입 성공 시, 바로 로그인
        if (result > 0) {
            userService.login(user, request);
            log.info("회원가입 성공 시 바로 로그인 되었나?");
            
        }
        return "redirect:/";
    }

    // 회원정보 수정
    @GetMapping(value = "/update")
    public String userUpdate(Model model, Principal principal) throws Exception {
        String loginId = principal != null ? principal.getName() : null;
        Users user = userService.selectById(loginId);
        model.addAttribute("user", user);
        return "user/update";
    }

    /**
     * 회원정보 수정 처리
     * 
     * @param entity
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/update")
    public String updateUpdatePro(Users user, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        log.info("user : " + user);
        int result = userService.update(user);
        log.info("회원정보 수정여부 : " + result);
        // 회원정보 수정 실패
        if (result == 0) {
            return "redirect:/user/update";
        }
        // 시큐리티 강제 로그아웃
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        new SecurityContextLogoutHandler().logout(request, response, authentication);
        // remember-me 쿠키 삭제
        Cookie cookie = new Cookie("remember-me", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        // 토큰 삭제
        persistentTokenRepository.removeUserTokens(user.getUserId());
        // 로그아웃 후 ➡ 로그인 페이지
        return "redirect:/user/login";
    }

    //회원정보 삭제
    @GetMapping(value="/delete")
    public String delete(String userId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("삭제할 아이디 : " + userId);
        int result = userService.delete(userId);
        log.info("유저 삭제 여부 : "+ result);
        if (result > 0){
            // 시큐리티 강제 로그아웃
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            // remember-me 쿠키 삭제
            Cookie cookie = new Cookie("remember-me", "");     
            cookie.setMaxAge(0);                                  
            cookie.setPath("/");        
            response.addCookie(cookie);
            // 토큰 삭제
            persistentTokenRepository.removeUserTokens(userId);
            return "redirect:/";
        } else {
            return "redirect:/user/update";
        }
    }

    @GetMapping(value = "/seller")
    public String userSeller(Model model) {
        return "user/seller";
    }

    // 마이페이지
    @GetMapping("/mypage")
    public String mypage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();
        if (userId == "anonymousUser" && userId.equals("anonymousUser")) {
            return "redirect:/user/login";
        } else {
            CustomUser customuser = (CustomUser) auth.getPrincipal();
            Users user = customuser.getUsers();
            String role = user.getAuth();
            model.addAttribute("auth", role);
            return "user/mypage";
        }
    }
}
