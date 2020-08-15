package com.cartoon.oauth.config;

import com.alibaba.fastjson.JSON;
import com.cartoon.exceptions.DataNotFoundException;
import com.cartoon.feign.UserFeignClient;
import com.cartoon.oauth.util.UserJwt;
import com.cartoon.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/*****
 * 自定义授权认证类
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    ClientDetailsService clientDetailsService;

    @Autowired
    UserFeignClient userFeignClient;

    @Autowired
    private RedisUtil redisUtil;



    /****
     * 自定义授权认证
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    /**
     * 此方法默认是执行两遍，第一遍是验证security的用户名密码
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException,DataNotFoundException {
        //取出身份，如果身份为空说明没有认证
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //没有认证统一采用httpbasic认证，httpbasic中存储了client_id和client_secret，开始认证client_id和client_secret
        //第一遍先进入这里，个人估计是启用security
        if (authentication == null) {
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
            if (clientDetails != null) {
                //秘钥(从数据库中读取security的密码)
                String clientSecret = clientDetails.getClientSecret();
                //加密方式
                //return new User(username,new BCryptPasswordEncoder().encode(clientSecret), AuthorityUtils.commaSeparatedStringToAuthorityList(""));
                //不加密使用数据库原文方式
                return new User(username, clientSecret, AuthorityUtils.commaSeparatedStringToAuthorityList(""));
            }
        }
        /*
         * 如果application.yml里面的  clientSecret: 1231231 不正确，不会走到这一步，
         * 证明以上的操作，是系统先根据 clientId: taobao读取数据库中的密码登录security
         * 然后用户或者说系统再使用用户提供的clientSecret: 1231231 再登录或者说验证是否和数据库中密码一致，如果一致则会第二次调用此方法，
         * 第二次调用的时候已经有认证信息了，或者说已经登录认证了，所以authentication!=null
         * 则进行一下操作
         */
        if (StringUtils.isEmpty(username)) {
            return null;
        }

        //此处是将查询到的用户信息封装（转换成map）
        com.cartoon.entity.User user = this.seachPwd(username);
        String password = user.getPassword();
        checkVip(user);


        //添加自定义信息
        //{"scope":["app"],"exp":1596796122,"authorities":["{\"username\":\"18626000309\"}"],"jti":"8e0cdf91-a42f-43d2-9055-fb477661f8b7","client_id":"doctor"}
        Map<String,Object> params = new HashMap<>();
        params.put("username",username);
        String data = JSON.toJSONString(params);
        UserJwt userDetails = new UserJwt(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList(data));



        /**
         * 此处封装自定义信息
         */
        userDetails.setId(user.getId());
        userDetails.setVip(user.getVip());

        //登录成功之后将用户的信息存到redis
        redisUtil.set("userinfo:"+user.getPhone(), user,60*60*24);
        return userDetails;
    }

    /**
     * 根据手机号登陆 获取当前手机号用户的信息
     */
    private com.cartoon.entity.User seachPwd(String username) {
        String phone = username;
        return userFeignClient.serchUserByPhoneFeign(phone);
    }

    /**
     * 验证vip状态，不合法修改
     * @param user
     */
    private void checkVip(com.cartoon.entity.User user){
        userFeignClient.checkVip(user);
    }


}
