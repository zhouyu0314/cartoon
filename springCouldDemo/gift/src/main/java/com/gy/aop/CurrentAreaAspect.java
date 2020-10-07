package com.gy.aop;

import com.gy.utils.Cons;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Map;

@Aspect
@Component
public class CurrentAreaAspect {
    @Pointcut("execution(* com.gy.controller.GiftController.*(..))")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Map arg = (Map) args[0];
        Cons.setCurrentArea(Integer.valueOf(arg.get("area").toString()));
    }


    @AfterReturning(value = "pointCut()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {


    }


    @AfterThrowing(value = "pointCut()", throwing = "e")
    public void afterReturning(JoinPoint joinPoint, RuntimeException e) {

    }
}
