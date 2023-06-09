package com.webtest.webtest.aspect;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author weizhenqiang
 * @date 2023/5/29 14:41
 */
@Aspect
@Component
@Slf4j
@AllArgsConstructor
public class LoginLogAspect {

    @AfterReturning(pointcut = "@annotation(com.webtest.webtest.aspect.LoginLog)")
    public void secureClientLoginLog(JoinPoint joinPoint) {

        System.out.println("AfterReturning");

    }

    @AfterThrowing(throwing="ex",pointcut = "@annotation(com.webtest.webtest.aspect.LoginLog)")
    public void AfterThrowing(Throwable ex,JoinPoint joinPoint) {

        System.out.println(ex.getMessage());

    }

}
