package com.cartoon.serivce.impl;

import com.cartoon.async.AccountRecordAsyn;
import com.cartoon.entity.User;
import com.cartoon.exceptions.*;
import com.cartoon.mapper.UserMapper;
import com.cartoon.serivce.IUserService;
import com.cartoon.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private AccountRecordAsyn accountRecordAsyn;

    //用户注册
    @Override
    public Integer userReg(String phone, String password, String recommender)
            throws UsernameConflictExcaption, InsertDataException, IllegalParamsException {
        //验证手机号
        CommonCheckUtil.checkPhone(phone);
        //验证密码
        CommonCheckUtil.checkPassword(password);
        List<User> data = this.findUserByPhone(phone);
        //如果用户存在，抛出UsernameConflictExcaption("用户已存在")）用户名冲突异常
        if (data.size() != 0) {
            throw new UsernameConflictExcaption("用户已存在");
        }
        //加密
        String pwd = new BCryptPasswordEncoder().encode(password);
        //创建用户对象
        User user = new User();
        user.setId(IdWorker.getId());
        user.setPassword(pwd);
        user.setCreatedTime(SimpleDate.getDate(new Date()));
        user.setModifiyTime(SimpleDate.getDate(new Date()));
        user.setUsername(RandomStr.getRandomString(16));
        user.setPhone(phone);
        user.setHeadImg("/static/headimages/defaultHeadImage.jpg");
        user.setVip(1);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        user.setVipExpire(SimpleDate.getDate(calendar.getTime()));
        //验证是否有推荐用户
        if (!StringUtils.isEmpty(recommender)) {
            List<User> userByPhone = this.findUserByPhone(recommender);
            User oldUser = userByPhone.get(0);
            //如果有老用户
            if (oldUser != null) {
                //修改新用户的推荐人
                user.setRecommender(recommender);
                //查看老用户是否是vip,并且vip没有过期
                if (oldUser.getVip() == 1 && SimpleDate.parseDate(oldUser.getVipExpire()).getTime() > new Date().getTime()) {
                    //将vip到期时间+7
                    String vipExpire = user.getVipExpire();
                    oldUser.setVipExpire(SimpleDate.getDate(CalendarUtil.add(vipExpire, 7)));
                } else if (oldUser.getVip() == 1 && SimpleDate.parseDate(oldUser.getVipExpire()).getTime() < new Date().getTime()) {
                    //如果用户是vip但是vip到期了,设置当前日期的七天之后为到期时间
                    oldUser.setVipExpire(SimpleDate.getDate(CalendarUtil.add(SimpleDate.getDate(new Date()), 7)));
                } else {
                    //用户不是vip
                    oldUser.setVip(1);
                    oldUser.setVipExpire(SimpleDate.getDate(CalendarUtil.add(SimpleDate.getDate(new Date()), 7)));
                }

                //更新老用户vip到期时间
                this.updateUserInfo(oldUser);

            }

        }

        return reg(user);
    }


    //根据手机号查询用户
    @Override
    public User serchUserByPhone(String phone) throws DataNotFoundException, IllegalParamsException {
        //验证手机号
        CommonCheckUtil.checkPhone(phone);
        //先从redis里找
        User userFromRedis = (User) redisUtil.get("userinfo:" + phone);
        if (userFromRedis != null) {
            return userFromRedis;
        }
        //redis没有从db找
        List<User> userByPhone = findUserByPhone(phone);
        if (userByPhone.size() == 0) {
            throw new DataNotFoundException("用户不存在");
        }

        User user = userByPhone.get(0);
        return user;
    }

    //修改用户资料
    @Override
    public Integer changeUserInfo(User user) throws DataNotFoundException, InsertDataException {
        //验证用户是否存在
        User data = this.serchUserByPhone(user.getPhone());
        //验证数据格式
        if (!StringUtils.isEmpty(user.getEmail())) {
            CommonCheckUtil.checkEmail(user.getEmail());
        }
        if (!StringUtils.isEmpty(user.getGender())) {

            CommonCheckUtil.checkGender(user.getGender());
        }
        if (!StringUtils.isEmpty(user.getNickname())) {
            CommonCheckUtil.checkNickname(user.getNickname());
        }
        if (!StringUtils.isEmpty(user.getQq())) {
            CommonCheckUtil.checkQQ(user.getQq());
        }
        if (!StringUtils.isEmpty(user.getUsername())) {

            CommonCheckUtil.checkUsername(user.getUsername());
        }
        if (!StringUtils.isEmpty(user.getPhone())) {

            CommonCheckUtil.checkPhone(user.getPhone());
        }
        //如果没有修改过用户名,并且本次不修改
        if (data.getCheckUsername() == 0 && data.getUsername().equals(user.getUsername())) {

            user.setModifiyTime(SimpleDate.getDate(new Date()));
            return updateUserInfo(user);
        }
        //如果没有修改过用户名,本次修改
        if (data.getCheckUsername() == 0 && !data.getUsername().equals(user.getUsername())) {
            //将状态修改成1,修改用户资料
            user.setCheckUsername(1);
            user.setModifiyTime(SimpleDate.getDate(new Date()));
            return updateUserInfo(user);
        }

        //如果用户已经修改过用户名，并且本次不在修改
        if (data.getCheckUsername() != 0 && data.getUsername().equals(user.getUsername())) {
            user.setModifiyTime(SimpleDate.getDate(new Date()));
            return updateUserInfo(user);
        }
        //如果用户已经修改了用户名，并且还要再修改则抛出异常
        if (data.getCheckUsername() != 0 && !data.getUsername().equals(user.getUsername())) {
            throw new InsertDataException("修改数据失败，用户名只可修改一次！");
        }
        return null;
    }

    /*
    1.查询用户是否存在
    2.查询原密码，并对比新密码
    3.修改原密码
     */
    @Override
    public Integer changeUserPwd(String newPwd, String oldPwd, String phone)
            throws DataNotFoundException, IllegalParamsException, InsertDataException {
        //验证密码
        CommonCheckUtil.checkPassword(newPwd);
        CommonCheckUtil.checkPassword(oldPwd);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String newP = new BCryptPasswordEncoder().encode(newPwd);
        //验证用户是否存在
        User user = serchUserByPhone(phone);
        //查询原密码，并对比
        String data = user.getPassword();
        if (!bCryptPasswordEncoder.matches(oldPwd, data)) {
            //密码不正确
            throw new IllegalParamsException("原密码输入不正确");
        }
        user.setPassword(newP);
        Integer rows = updateUserInfo(user);
        return rows;
    }

    public Integer changeUserHeaderImg(MultipartFile file, String tempPath, String phone) throws UploadFileException,
            InsertDataException {
        String fileName = ToolUpLoad.fileUpload(file, tempPath);
        User user = new User();
        user.setPhone(TokenDecode.getUserInfo().get("username"));
        user.setHeadImg(tempPath + "/" + fileName);
        updateUserInfo(user);
        return null;
    }

    @Override
    public String getToken(String phone) throws LoginTimeOutException {
        String token = redisUtil.get("expire:" + phone).toString();
        if (token == null) {
            throw new LoginTimeOutException("登录超时！请重新登录。");
        }
        //继续存35分钟
        redisUtil.expire("expire:" + phone, 60 * 35);
        return token;
    }

    @Override
    public User getUserInfo(String phone) {
        return userMapper.getUserInfo(phone);
    }


    /**
     * 通过vip到期时间验证用户vip合法性，不合法修改vip状态
     */
    @Override
    public void checkVip(User user) {

        //如果当前用户是vip
        if (user.getVip() == 1) {
            //查看过期时间
            String vipExpire = user.getVipExpire();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date vipEx = null;
            try {
                vipEx = sdf.parse(vipExpire);
            } catch (ParseException e) {
                throw new IllegalParamsException("参数错误！");
            }
            //如果当前vip时间已经过期了，则需要修改vip状态为0
            if (new Date().getTime() > vipEx.getTime()) {
                user.setVip(0);
                this.updateUserInfo(user);
            }

        }
    }

    //修改用户的元宝、积分、月票。。。。
    @Override
    public Integer updateUsergoldOrTicketOrScoreOrCoupon(Map<String, Object> params) throws InsertDataException {
        String phone = params.get("phone").toString();
        User user = new User();
        user.setPhone(phone);
        String gold = params.get("gold").toString();
        if (gold != null) {
            user.setGold(Integer.valueOf(gold));
        }
        String score = params.get("score").toString();
        if (score != null) {
            user.setScore(Integer.valueOf(score));
        }

        String ticket = params.get("ticket").toString();
        if (ticket != null) {
            user.setTicket(Integer.valueOf(ticket));
        }

        String coupon = params.get("coupon").toString();
        if (coupon != null) {
            user.setCoupon(Integer.valueOf(coupon));
        }
        Integer rows = updateUserInfo(user);
        //修改后应记录，此处和用户无关了开启异步线程
        accountRecordAsyn.add(params);
        return rows;
    }


    //*************************************private*********************************************


    /**
     * 使用手机号注册
     */
    private Integer reg(User user) {
        Integer rows = rows = userMapper.insertUser(user);
        if (rows != 1) {
            throw new InsertDataException("添加数据失败，请联稍后重试！");
        }
        return rows;
    }

    /**
     * 使用手机号查询用户是否存在
     *
     * @param phone
     * @return
     */
    private List<User> findUserByPhone(String phone) {
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        List<User> userListByMap = userMapper.getUserListByMap(map);
        return userListByMap;
    }


    /**
     * 修改用户资料
     */
    private Integer updateUserInfo(User user) {
        Integer rows = userMapper.updateUser(user);
        if (rows != 1) {
            throw new InsertDataException("修改用户资料失败，请稍后重试！");
        }
        //修改完之后查一下存redis
        List<User> userByPhone = findUserByPhone(user.getPhone());
        redisUtil.set("userinfo:" + user.getPhone(), userByPhone.get(0), 60 * 35);
        return rows;
    }


}
