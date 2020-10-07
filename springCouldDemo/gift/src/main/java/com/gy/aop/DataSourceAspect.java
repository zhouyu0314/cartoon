//package com.gy.aop;
//
//import com.gy.annotation.DataSource;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//import java.lang.reflect.Proxy;
//import java.util.Map;
//
//@Component
//@Aspect
//public class DataSourceAspect {
//    //定义切入点
////    @Pointcut("@annotation(com.gy.annotation.DataSource)")
//    @Pointcut("execution(* com.gy.mapper.PersonMapper.*(..))")
//    public void pointCut() {
//
//    }
//
//    @Before("pointCut()")
//    public void before(JoinPoint joinPoint) {
//        //获取方法签名
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        //获取切入方法的对象
//        Method method = signature.getMethod();
//        //获取方法上的Aop注解
//        DataSource annotation = method.getAnnotation(DataSource.class);
//        if (annotation != null) {
//            // 获取代理处理器
//            InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
//            // 获取私有 memberValues 属性
//            try {
//                Field f = invocationHandler.getClass().getDeclaredField("memberValues");
//                // 打开访问权限
//                f.setAccessible(true);
//                // 获取 memberValues (目标注解的信息都在 memberValues 中)
//                // 获取实例的属性map
//                Map<String, Object> memberValues = (Map<String, Object>) f.get(invocationHandler);
//                // 1)、动态获取 TargetAnnotation.targetAnnotationClassField 标注的注解属性值
//                Object targetAnnotationClassField = memberValues.get(annotation.value());
//                memberValues.clear();
//                memberValues.put("value", "slave2");
//                System.out.println(memberValues);
//            } catch (NoSuchFieldException | IllegalAccessException e) {
//
//            }
//        }
//    }
//
//
////    @Around("pointCut()")
////    public Object dosome(ProceedingJoinPoint joinPoint) throws Throwable {
////        //获取方法签名
////        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
////        //获取切入方法的对象
////        Method method = signature.getMethod();
////        //获取方法上的Aop注解
////        DataSource annotation = method.getAnnotation(DataSource.class);
////        //获取注解上的值如 : @MyAnnotation(key = "'param id is ' + #id")
////        String keyEl = annotation.value();
////        //将注解的值中的El表达式部分进行替换
////        //String currentArea = Cons.getCurrentArea();
////        keyEl = keyEl.replace("#area", "slave2");
////
////        //创建解析器
////        SpelExpressionParser parser = new SpelExpressionParser();
////        //获取表达式
////        Expression expression = parser.parseExpression(keyEl);
////        //设置解析上下文(有哪些占位符，以及每种占位符的值)
////        EvaluationContext context = new StandardEvaluationContext();
////        //获取参数值
////        Object[] args = joinPoint.getArgs();
////        //获取运行时参数的名称
////        DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
////        String[] parameterNames = discoverer.getParameterNames(method);
////        for (int i = 0; i < parameterNames.length; i++) {
////            context.setVariable(parameterNames[i],args[i].toString());
////        }
////        //解析,获取替换后的结果
////        String result = expression.getValue(context,String.class);
////
////        System.out.println("***************************"+result);
////
////
////        return null;
////    }
//
////    @Around("pointCut()")
////    public void dosome(ProceedingJoinPoint joinPoint) throws Throwable {
////        //获取方法签名
////        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
////        //获取切入方法的对象
////        Method method = signature.getMethod();
////        //获取方法上的Aop注解
////        DataSource annotation = method.getAnnotation(DataSource.class);
////        if (annotation != null) {
////            // 获取代理处理器
////            InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
////            // 获取私有 memberValues 属性
////            try{
////                Field f = invocationHandler.getClass().getDeclaredField("memberValues");
////                // 打开访问权限
////                f.setAccessible(true);
////                // 获取 memberValues (目标注解的信息都在 memberValues 中)
////                // 获取实例的属性map
////                Map<String, Object> memberValues = (Map<String, Object>) f.get(invocationHandler);
////                // 1)、动态获取 TargetAnnotation.targetAnnotationClassField 标注的注解属性值
////                Object targetAnnotationClassField = memberValues.get(annotation.value());
////                memberValues.clear();
////                memberValues.put("value","slave2");
////                System.out.println(memberValues);
////            }catch (NoSuchFieldException e){
////
////            }
////        }
////
////    }
//
//
//}
