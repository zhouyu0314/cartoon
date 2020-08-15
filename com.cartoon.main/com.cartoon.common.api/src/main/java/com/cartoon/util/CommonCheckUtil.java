package com.cartoon.util;

import com.cartoon.exceptions.IllegalParamsException;

/**
 * 验证参数合法性
 */
public class CommonCheckUtil {
    private static final String CHECK_PHONE = "^1[3|4|5|7|8]\\d{9}$";
    //必须是6-10位字母、数字、下划线（这里字母、数字、下划线是指任意组合，没有必须三类均包含）
    //不能以数字开头
    private static final String CHECK_USERNAME = "^[^0-9][\\w_]{5,9}$";
    private static final String CHECK_PASSWORD = "^([A-Z]|[a-z]|[0-9]|[-=;,./~!@#$%^*()_+}{:?]){6,20}$";
    //验证昵称
    private static final String CHECK_NICKNAME = "^[\u4e00-\u9fa5.·]{0,}$";
    //验证性别
    private static final String CHECK_GENDER = "[0|1|2]";
    //验证邮箱
    private static final String CHECK_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    //验证QQ
    private static final String CHECK_QQ = "^[1-9]\\d{5,11}$";

    public static void checkPhone(String phone) throws IllegalParamsException {
        if (!phone.matches(CHECK_PHONE))
            throw new IllegalParamsException("手机号码格式违法，请重新输入！");
    }

    public static void checkUsername(String username) throws IllegalParamsException {
        if (!username.matches(CHECK_USERNAME))
            throw new IllegalParamsException("用户名格式违法，请重新输入！");
    }

    public static void checkNickname(String nickname) throws IllegalParamsException {
        if (!nickname.matches(CHECK_NICKNAME))
            throw new IllegalParamsException("昵称格式违法，请重新输入！");
    }

    public static void checkGender(Integer gender) throws IllegalParamsException {
        if (!(gender + "").matches(CHECK_GENDER))
            throw new IllegalParamsException("性别格式违法，请重新输入！");
    }


    public static void checkEmail(String email) throws IllegalParamsException {
        if (!email.matches(CHECK_EMAIL))
            throw new IllegalParamsException("邮箱格式违法，请重新输入！");
    }

    public static void checkQQ(String qq) throws IllegalParamsException {
        if (!qq.matches(CHECK_QQ))
            throw new IllegalParamsException("qq格式违法，请重新输入！");
    }


    public static void checkPassword(String password) throws IllegalParamsException {
        if (!password.matches(CHECK_PASSWORD))
            throw new IllegalParamsException("密码格式违法，请重新输入！");
    }

}
