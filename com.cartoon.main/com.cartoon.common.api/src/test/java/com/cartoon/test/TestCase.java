package com.cartoon.test;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestCase {

    private String CHECK_PHONE = "^1[3|4|5|7|8][0-9]\\d{4,8}$";
    private String CHECK_PASSWORD = "^([A-Z]|[a-z]|[0-9]|[-=;,./~!@#$%^*()_+}{:?]){6,20}$";
    private static final String CHECK_QQ = "^[1-9]\\d{5,11}$";
    //验证性别
    private static final String CHECK_GENDER = "[0|1|2]";

    @Test
    public  void checkPhone() {
       String qq = "181332969";
        if (!qq.matches(CHECK_QQ))
            System.out.println("手机号码格式违法，请重新输入！");
    }
}
