//package com.cartoon.aop;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//
//@Aspect
//@Component
//public class UserLogAspects {
//
//
//    @Autowired
//    private KafkaTemplate kafkaTemplate;
//
//    @Pointcut("execution(* com.cartoon.controller.UserController.*(..))")
//    public void pointCut() {
//    }
//
//    @Before("pointCut()")
//    public void before(JoinPoint joinPoint) {
//
//        kafkaTemplate.send("cartoon", "cartoon", "调用" + joinPoint.getTarget() + "的" +
//                joinPoint.getSignature().getName() + "方法，参数：" + Arrays.toString(joinPoint.getArgs()));
//
//
//
//    }
//
//
//    @AfterReturning(value = "pointCut()",returning = "result")
//    public void afterReturning(JoinPoint joinPoint, Object result){
//        kafkaTemplate.send("cartoon", "cartoon", "调用" + joinPoint.getTarget() + "的" +
//                joinPoint.getSignature().getName() + "方法，返回值：" + result);
//    }
//
//
//    @AfterThrowing(value = "pointCut()",throwing = "e")
//    public void afterReturning(JoinPoint joinPoint,RuntimeException e){
//        kafkaTemplate.send("cartoon", "cartoon", "调用" + joinPoint.getTarget() + "的" +
//                joinPoint.getSignature().getName() + "方法，异常："+e);
//    }
//
//}
