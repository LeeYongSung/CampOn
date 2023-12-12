package com.camp.campon.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import com.camp.campon.dto.UserAuth;
import com.camp.campon.dto.Users;
import com.camp.campon.mapper.UserMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public int insert(Users user) throws Exception {
        // 비밀번호 암호화
        String userPw = user.getUserPw();
        String encodedPw = passwordEncoder.encode(userPw);
        user.setUserPw(encodedPw);
        // 회원 등록
        int result = userMapper.insert(user);

        // 권한 등록
        // if( result > 0 ) {
        //     UserAuth userAuth = new UserAuth();
        //     userAuth.setUserId( user.getUserId() );
        //     userAuth.setAuth("ROLE_USER");          // 기본 권한 : 사용자 권한 (ROLE_USER)
        //     result = userMapper.insertAuth(userAuth);
        // }

        return result;
    }

    @Override
    public Users select(int userNo) throws Exception {
        return userMapper.select(userNo);
    }

    @Override
    public void login(Users user, HttpServletRequest requset) throws Exception {

        String username = user.getUserId();
        String password = user.getUserPwCheck();
        log.info("username : " + username);
        log.info("password : " + password);

        // 아이디, 패스워드 인증 토큰 생성
        UsernamePasswordAuthenticationToken token 
            = new UsernamePasswordAuthenticationToken(username, password);

        // 토큰에 요청정보를 등록
        token.setDetails( new WebAuthenticationDetails(requset) );
        log.info("토큰에 요청정보 등록");
        // 토큰을 이용하여 인증(로그인)
        Authentication authentication = authenticationManager.authenticate(token);
        log.info("토은을 이용하여 인증(로그인)");
        User authUser = (User) authentication.getPrincipal();
        log.info("인증된 사용자 아이디 : " + authUser.getUsername());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public Users selectById(String userId) throws Exception {
        Users user = userMapper.selectById(userId);
        return user;
    }

    @Override
    public int update(Users user) throws Exception {
        // 비밀번호 암호화
        String userPw = user.getUserPw();
        String encodedPw = passwordEncoder.encode(userPw);
        user.setUserPw(encodedPw);
        int result = userMapper.update(user);
        return result;
    }

    @Override
    public int delete(String userId) throws Exception {
        int result = userMapper.delete(userId);
        return result;
    }

    @Override
    public Users productsseller(int campNo) throws Exception {
        Users seller = userMapper.productsseller(campNo);
        return seller;
    }

    @Override
    public List<Users> memberList(String auth) throws Exception {
        List<Users> memberList = userMapper.memberList(auth);
        return memberList;
    }

    @Override
    public List<String> userIds() throws Exception {
        List<String> userIdList = userMapper.userIds();
        return userIdList;
    }

    @Override
    public String sellerTel(int cpdtNo) throws Exception {
        String userTel = userMapper.sellerTel(cpdtNo);
        return userTel;
    }
    
}
