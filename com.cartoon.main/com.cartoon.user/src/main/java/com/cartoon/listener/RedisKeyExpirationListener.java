package com.cartoon.listener;

import com.cartoon.entity.AuthToken;
import com.cartoon.exceptions.LoginFailException;
import com.cartoon.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * redis失效key监听
 */
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${oauth.clientId}")
    private String clientId;
    @Value("${oauth.clientSecret}")
    private String clientSet;

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * redis失效key事件处理
     *
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        // message.toString()可以获取失效的key
        String expiredKey = message.toString();
        refreshToken(expiredKey);
        System.out.println("-------------------");
        System.out.println("失效key：" + expiredKey);

    }


    private void refreshToken(String expiredKey) {
        if (expiredKey.startsWith("expire:")) {
            //将用户名，和刷新token存redis
            String[] split = expiredKey.split(":");
        //判断refresh_token是否过期
            if (!redisUtil.hasKey("refresh_token:" + split[1])) {
                throw new LoginFailException("请重新登录！");
            }

            //获取此用户的refresh_token
            String refresh_token = redisUtil.get("refresh_token:" + split[1]).toString();

            //使用微服名调用
            List<ServiceInstance> list = discoveryClient.getInstances("com.cartoon.user.oauth");
            ServiceInstance instance = list.get(0);
            String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/oauth/token";
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

            params.add("grant_type", "refresh_token");
            params.add("refresh_token", refresh_token);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Authorization", this.httpBasic(clientId, clientSet));
            ResponseEntity<Map> responseEntity = restTemplate.exchange(url,
                    HttpMethod.POST, new HttpEntity<>(params, headers), Map.class);
            Map body = responseEntity.getBody();
            //创建一个实体类去接收
            AuthToken authToken = new AuthToken();
            authToken.setAccessToken(body.get("access_token").toString());
            authToken.setJti(body.get("jti").toString());
            authToken.setRefreshToken(body.get("refresh_token").toString());


            redisUtil.set("expire:" + split[1], authToken.getAccessToken(), 60*30);
            redisUtil.set("refresh_token:" + split[1], authToken.getRefreshToken(),60*60*24);
        }

    }
    //还有一个监听类，是配合上面的配置文件注释的部分的，可以灵活配置，配置监听不同的库，redis有16个库。

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

