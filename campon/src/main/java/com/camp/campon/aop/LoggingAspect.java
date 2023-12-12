package com.camp.campon.aop;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@EnableAspectJAutoProxy
@Component
@Aspect
public class LoggingAspect {
	
	/**
	 * 어드 바이스 유형
	 * - Around
	 * - Before
	 * - After
	 * - AfterReturning
	 * - AfterThrowing
	 */
	
	/**
	 * 포인트컷 표현요소
	 * - 지시자 : execution
	 * - 반환값  
	 * - 패키지
	 * - 클래스
	 * - 메소드
	 * - 인수
	 */
	@Before("execution(* com.camp.campon.service.*Service*.*(..))")
	public void before(JoinPoint jp) {
		// jp.getSignature() 	: 타겟 메소드의 시그처 정보(반환타입 패키지.클래스.메소드) 반환
		// jp.getArgs() 		: 타겟 메소드의 매개변수를 반환
		log.info("===================================================================");
		log.info("[@Before] #########################################################");
		log.info("target : " + jp.getTarget().toString());
		log.info("signature : " + jp.getSignature());
		log.info("args : " + Arrays.toString(jp.getArgs()) );
		
		// 파라미터 출력
		printParam(jp);
		log.info("===================================================================\n");
	}
	
	
	@After("execution(* com.camp.campon.service.*Service*.*(..))")
	public void after(JoinPoint jp) {
		log.info("===================================================================");
		log.info("[@After] ##########################################################");
		log.info("target : " + jp.getTarget().toString());
		log.info("signature : " + jp.getSignature());
		log.info("args : " + Arrays.toString(jp.getArgs()) );
		
		// 파라미터 출력
		printParam(jp);
	    log.info("===================================================================\n");
	}
	
	/*
	 * @Around 유형을 적용하면, @After 어드바이스는 실행되지 않는다.
	 * 
	 * ProceedingJoinPoint : 어드바이스에서 대상 메서드의 실행을 제어하고 호출하는 객체
	 * - proceed() 			: 대상 메소드 호출
	 */
	@Around("execution(* com.camp.campon.service.*Service*.*(..))")
	public Object around(ProceedingJoinPoint jp) {
		log.info("===================================================================");
		log.info("[@Around] #########################################################");
		log.info("target : " + jp.getTarget().toString());
		log.info("signature : " + jp.getSignature());
		log.info("args : " + Arrays.toString(jp.getArgs()) );
		LocalDateTime time = LocalDateTime.now();
		log.info("현재 시간 : " + time);
		
		Object result = null;
		try {
			result = jp.proceed();
			if( result != null )
				log.info("반환값 : " + result.toString());
		} catch (Throwable e) {
			log.error("예외가 발생했습니다.");
			e.printStackTrace();
		}
		
		// After, Around 를 함께 사용하려면, Around 어드바이스  에서 After 어드바이스를 호출해준다.
		after(jp);
		log.info("===================================================================\n");
		return result;
	}
	
	
	
	// pointcut 	: 포인트컷 표현식
	// returning 	: 타겟 메소드의 반환값을 저장하는 매개변수명 지정
	@AfterReturning(pointcut = "execution(* com.camp.campon.service.*ServiceImpl.*(..))", returning = "result")
	public Object afterReturning(JoinPoint jp, Object result) {
		log.info("===================================================================");
		log.info("[@AfterReturning] #################################################");
		log.info("target : " + jp.getTarget().toString());
		log.info("signature : " + jp.getSignature());
		log.info("args : " + Arrays.toString(jp.getArgs()) );
		// 파라미터 출력
		printParam(jp);
		
		// 반환값 출력
		if( result != null )
			log.info("반환값 : " + result.toString());
		
		log.info("===================================================================\n");
		return result;
	}
	
	
	@AfterThrowing(pointcut = "execution(* com.camp.campon.service.*Service*.*(..))", throwing ="exception")
	public void afterThrowing(JoinPoint jp, Exception exception) {
		log.info("===================================================================");
		log.info("[@AfterThrowing] ##################################################");
		log.info("target : " + jp.getTarget().toString());
		log.info("signature : " + jp.getSignature());
		log.info( exception.toString() ); 
		log.info("===================================================================\n");
	}
	

	/**
	 * 파라미터 출력
	 * @param jp
	 */
	public void printParam(JoinPoint jp) {
		Signature signature = jp.getSignature();
	    // 타겟 메소드의 파라미터 이름 가져오기
	    String[] parameterNames = ((MethodSignature) signature).getParameterNames();
	    // 타겟 메소드의 인수 가져오기
	    Object[] args = jp.getArgs();
	    // 파라미터 이름과 값을 출력
	    if( parameterNames != null )
	    for (int i = 0; i < parameterNames.length; i++) {
	        String paramName = parameterNames[i];
	        Object paramValue = args[i];
	        log.info("파라미터명: " + paramName + ", 값: " + paramValue);
	    }
	}

}
