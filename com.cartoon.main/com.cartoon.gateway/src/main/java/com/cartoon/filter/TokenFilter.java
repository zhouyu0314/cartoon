package com.cartoon.filter;

import com.cartoon.exceptions.UnauthorizedException;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class TokenFilter implements GlobalFilter, Ordered {


    private static final String AUTHORIZE_TOKEN = "Authorization";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)throws RuntimeException {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String url = request.getURI().getPath();
        //不进行身份认证的url
        if (url.startsWith("/api/user/serchUserByPhone/") || url.startsWith("/api/user/userReg") ||
                url.startsWith("/oath/user/login")) {
            return chain.filter(exchange);
        }
        //从headers获取token
        String token = request.getHeaders().getFirst(AUTHORIZE_TOKEN);
        if (StringUtils.isEmpty(token)) {
            //从请求参数获取token
            token = request.getQueryParams().getFirst(AUTHORIZE_TOKEN);
        }
        if (StringUtils.isEmpty(token)) {
            throw new UnauthorizedException("请先进行身份验证！");
        }

//        //对比用户发过来的token和redis里面的token是否一致
//        Object obj = redisUtil.get(token);
//        if (obj == null) {
//            //查不到redis则说明令牌失效
//            System.out.println("查询不到信息");
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            return response.setComplete();  //直接响应状态
//        }

        //这里不再验证令牌的有效性，而是验证是否有令牌，如果有令牌
        try {

            //将令牌装到头文件中
            request.mutate().header(AUTHORIZE_TOKEN, "bearer " + token);
        } catch (Exception e) {
            //此应该不需要if判断.isEmpty(token) 在上面已经判断过了
            if (StringUtils.isEmpty(token)) {
                //throw new UnauthorizedException("用户未授权，请重新登录");
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }


}
