package com.gy.aop;

import com.gy.config.DataSourceKey;
import com.gy.config.DynamicDataSourceContextHolder;
import com.gy.utils.Cons;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class DataSourceAspect {
    //定义切入点
    @Pointcut("execution(* com.gy.mapper.GiftMapper.*(..))")
    public void pointCut() {

    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        DynamicDataSourceContextHolder.setDataSourceKey(DataSourceKey.getArea(Cons.getCurrentArea()));

    }



}
