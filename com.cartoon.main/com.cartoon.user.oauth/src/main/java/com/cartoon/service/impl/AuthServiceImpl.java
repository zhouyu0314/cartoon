package com.cartoon.service.impl;

import com.cartoon.entity.AuthToken;
import com.cartoon.exceptions.UnauthorizedException;
import com.cartoon.service.AuthService;
import com.cartoon.util.CommonCheckUtil;
import com.cartoon.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RedisUtil redisUtil;

    private static final String AUTHORIZE_TOKEN = "Authorization";

    /**
     * 用户登录，如果获取不到返回null
     *
     * @param username  用户名
     * @param password  密码
     * @param clientId  认证系统用户名
     * @param clientSet 认证系统密码
     * @return
     */
    @Override
    public AuthToken userLogin(String username, String password, String clientId, String clientSet, String ttl) throws UnauthorizedException{
        CommonCheckUtil.checkPhone(username);
        CommonCheckUtil.checkPassword(password);
        AuthToken token = this.createToken(username, password, clientId, clientSet);
        if (token != null) {
            return token;
        }
        throw new UnauthorizedException("授权异常！");
    }

//    /**
//     * 用户的退出操作
//     * @param username
//     * @return
//     */
//    @Override
//    public void LoginOut(String username) {
//        //获取请求对象
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        String header = request.getHeader(AUTHORIZE_TOKEN);
//        System.out.println("header:" + header);
//        request.getParameter(AUTHORIZE_TOKEN);
//        redisUtil.del(header);
//    }

    private AuthToken createToken(String username, String password, String clientId, String clientSet) {

        //使用微服名调用
        List<ServiceInstance> list = discoveryClient.getInstances("com.cartoon.user.oauth");
        ServiceInstance instance = list.get(0);
        String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/oauth/token";
        //将密码认证的必须参数加入
        /*
         * MultiValueMap是一个接口，它继承了Map主要实现类是LinkedMultiValueMap，
         * 它允许一个key存多个value他是用LinkedHashMap来实现的
         * 　　LinkedHashMap是比HashMap多了一个链表的结构。
         * 与HashMap相比LinkedHashMap维护的是一个具有双重链表的HashMap，
         * LinkedHashMap支持2中排序一种是插入排序，一种是使用排序，
         * 最近使用的会移至尾部例如 M1 M2 M3 M4，使用M3后为 M1 M2 M4 M3了，
         * LinkedHashMap输出时其元素是有顺序的，而HashMap输出时是随机的，
         * 如果Map映射比较复杂而又要求高效率的话，最好使用LinkedHashMap，
         * 但是多线程访问的话可能会造成不同步，所以要用Collections.synchronizedMap来包装一下，
         * 从而实现同步。另外，LinkedHashMap可以实现快速的查询第一个元素（First）跟结尾（Last）。
         *
         */
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("grant_type", "password");
        param.add("username", username);
        param.add("password", password);
        //此为修改认证模式
        /*
         *MultiValueMap 多种值map 一个 key 可以对应多个 value
         * 创建LinkedMultiValueMap 实际上就是创建private final Map<K, List<V>> targetMap;
         * 多个value封装成一个List<>
         */

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", this.httpBasic(clientId, clientSet));
        //HttpEntity表示由头和正文组成的HTTP请求或响应实体。

        //此body是泛型的，是不是可以添加我们想要的数据？？？

        //通过http请求数据此返回的是一个响应实体，里面包含status、headers、body就是常见的响应信息
        /*
         *body里封装了access_token -> eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJhcHAiXSwibmFtZSI6bnVsbCwiaWQiOm51bGwsImV4cCI6MTU5MzI3MDQxNSwiYXV0aG9yaXRpZXMiOlsic2Vja2lsbF9saXN0IiwiZ29vZHNfbGlzdCJdLCJqdGkiOiIzMDYzOTVjYy1mMThkLTRlZjAtYmQ0Ny1lMWFkZGU1ZWVjZjAiLCJjbGllbnRfaWQiOiJjaGFuZ2dvdSIsInVzZXJuYW1lIjoiemhhbmdzYW4ifQ.Xo-6YOCZq8enlczlQDj2QOhxlrO75HmQ9wSyOuT2cMwGHrALobr9GVEg741gjZ2ePFV9ao5U1-Feh723DZWuzLBpiRxO1wHxsUXuGAATLE6WuwNhitYoVWnD_n80A4oeh3k0G9uLA82ciGHlbvSmVnxo7VBS2RzEx2p7aj3Iu8j32_v9dDJs5OMAngjCRwU8rTigQqMwUKQXNCzL0CILIkLLxDIQr_tRmHgUQ3pKbgdQu0x54xAaOS7AEk8hNpUmW-S3wKMawbNUuSpeOF8gcaHi5pRl3Oyd28gtN7W0BhMnKlJtHQDsfR2J3NgmV3FjMS4NDLOlEeEX7re-yuVhdA
         * token_type -> bearer
         * refresh_token -> eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJhcHAiXSwiYXRpIjoiMzA2Mzk1Y2MtZjE4ZC00ZWYwLWJkNDctZTFhZGRlNWVlY2YwIiwibmFtZSI6bnVsbCwiaWQiOm51bGwsImV4cCI6MTU5MzI3MDQxNSwiYXV0aG9yaXRpZXMiOlsic2Vja2lsbF9saXN0IiwiZ29vZHNfbGlzdCJdLCJqdGkiOiI5MTllY2RmMS1lZDNiLTQ0NjctOWJkYS1hZDk3M2IwZGRkOTAiLCJjbGllbnRfaWQiOiJjaGFuZ2dvdSIsInVzZXJuYW1lIjoiemhhbmdzYW4ifQ.fg_uM7GeSeezfsUXGagKHJFznEQhwptwjaHTWC1rVYnODXHIzkuj2CyQyJC2XBFS_cLKjPVnep_YazZ5mT6MFtnmzhmNGpFuaHeJ6Cd106FSX7LoigCeoQ_x5_FLHuW_3-flWAUCyo-YA5Yx6dDbbbEWkBnr_kzRVfIhDtpr2L2zKk5YwNwNKyjArkUi1v8bL90c15ReYyBnGQR6zf-OXfw4jtEPpl25IO2Mi4hV1-_XfalduFJYp4hfAGvZdMZEDUL9QfXeak2hCTrnkRk7JU8UcAeOGWAkM82ROa6zaYv5Hk3oobfQQpi4ubJFFyC6Zx1EqMfc1Wa4Fx3r0LjINA
         * "expires_in" -> {Integer@10810} 3599
         * "scope" -> "app"
         * "jti" -> "306395cc-f18d-4ef0-bd47-e1adde5eecf0"
         */
        ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(param, headers), Map.class);
        //测试一下
        responseEntity.getHeaders();
        responseEntity.getStatusCode();
        Map body = responseEntity.getBody();
        //创建一个实体类去接收
        AuthToken authToken = new AuthToken();
        authToken.setAccessToken(body.get("access_token").toString());
        authToken.setJti(body.get("jti").toString());
        authToken.setRefreshToken(body.get("refresh_token").toString());


        //将用户名，和刷新token存redis
        redisUtil.set("expire:"+username,authToken.getAccessToken(),60*30);
        redisUtil.set("refresh_token:"+username,authToken.getRefreshToken(),60*60*24);

        return authToken;
    }

    /**
     * 此为将用户名 密码加密拼接
     *
     * @param clientId
     * @param clientSet
     * @return
     */
    private String httpBasic(String clientId, String clientSet) {
        String key = clientId + ":" + clientSet;
        byte[] encode = Base64Utils.encode(key.getBytes());
        String value = "Basic " + new String(encode);
        return value;

    }




}
