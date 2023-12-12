package com.camp.campon.common;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice                       // 컨트롤러에서 발생하는 예외 처리 클래스 지정
public class CommonExceptionHandler {

    // 지정한 예외 타입에 대한 처리를 하는 메소드로 지정
    // @ExceptionHandler(Exception.class)
    // public String exception(Exception e, HttpServletResponse response) {

    //     log.info(e.toString());

    //     log.info("status : " + response.getStatus());
    //     return "error/exception";
    // }
    
}
