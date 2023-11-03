package com.camp.campon.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Data;


//@Slf4j
@Data
public class CustomUser extends User{
    
    private Users users;

    public CustomUser(Users users) {
        // this(), super() - 는 생성자 안에서 항상 첫번째 문장
        
        //(user_auth 테이블 없애고 auth열만 추가하려고 했을 때 이런식으로 해야 함. )

        /**
         * 수정 전
         */
        // super(users.getUserId(), users.getUserPw(), new ArrayList<SimpleGrantedAuthority>().stream()
        //                                                                                     .map( (auth) -> new SimpleGrantedAuthority(users.getAuth()))
        //                                                                                     .collect(Collectors.toList())
        //      );
        /**
         * 수정 후
         */
        super(users.getUserId(), users.getUserPw(), Arrays.asList(new SimpleGrantedAuthority(users.getAuth())));
//        log.info(users.getUserId() + users.getUserPw() + users.getAuth());
        // super(users.getUserId(), users.getUserPw(), users.getAuthList().stream()
        //                                                                .map( (auth) -> new SimpleGrantedAuthority(auth.getAuth()))
        //                                                                .collect(Collectors.toList()));
        // List<UserAuth> userAuthList = users.getAuthList();
        // List<SimpleGrantedAuthority> authList = new ArrayList<SimpleGrantedAuthority>();
        // for (int i = 0; i < userAuthList.size(); i++) {
        //     authList.add( new SimpleGrantedAuthority( userAuthList.get(i).getAuth() ));
        // }
        // super(users.getUserId(), users.getUserPw(), authList);
        
        this.users = users;
    }

    
}
