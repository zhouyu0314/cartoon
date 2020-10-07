package com.gy.aop;

import com.gy.config.DynamicDataSourceContextHolder;
import com.gy.cons.Cons;
import com.gy.exceptions.DataSourceNotFoundException;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 */
public class DynamicDataSourceAnnotationInterceptor implements MethodInterceptor {


    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAnnotationInterceptor.class);


    /**
     * 缓存方法注解值
     */
    private static final Map<Method, String> METHOD_CACHE = new HashMap<>();

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            String datasource = determineDatasource(invocation);
            if (! DynamicDataSourceContextHolder.containsDataSource(datasource)) {
                logger.error("数据源[{}]不存在",datasource);
                throw new DataSourceNotFoundException("归属地错误！");
            }
            DynamicDataSourceContextHolder.setDataSourceRouterKey(datasource);
            return invocation.proceed();
        } finally {
            DynamicDataSourceContextHolder.removeDataSourceRouterKey();
        }
    }

    private String determineDatasource(MethodInvocation invocation) {
//        Method method = invocation.getMethod();
//        Parameter[] parameters = method.getParameters();
        //注解有值时，使用注解的 没有进else
//        if (METHOD_CACHE.containsKey(method)) {
//            return METHOD_CACHE.get(method);
//        } else {
//            DataSource ds = method.isAnnotationPresent(DataSource.class) ? method.getAnnotation(DataSource.class)
//                    : AnnotationUtils.findAnnotation(method.getDeclaringClass(), DataSource.class);
//            METHOD_CACHE.put(method, ds.value());
            //return ds.value();
        String currentArea = Cons.getCurrentArea();

        return currentArea;
        }
//    }

}