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
    @Pointcut("execution(* com.gy.mapper.OrdersMapper.*(..))")
    public void pointCut() {

    }

    //定义切入点
    @Pointcut("execution(* com.gy.mapper.OrderItemMapper.*(..))")
    public void pointCut1() {

    }

    @Before("pointCut() || pointCut1()")
    public void before(JoinPoint joinPoint) {
        DynamicDataSourceContextHolder.setDataSourceKey(DataSourceKey.getArea(Cons.getCurrentArea()));

    }



}
