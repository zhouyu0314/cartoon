package com.cartoon.test;

import com.cartoon.config.DateUtil;
import com.cartoon.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MathTest {
    @Autowired
    private RedisUtil redisUtil;
    @Test
    public void test01()throws Exception{
        int i = (int) (Math.random() * 10 + 1);
        List<Date> dateMenus = DateUtil.getDateMenus();
        System.out.println(dateMenus);
        for (Date dateMenu : dateMenus) {
            System.out.println(DateUtil.format(dateMenu, DateUtil.PATTERN_YYYYMMDDHH));
            Date date = DateUtil.addDateHour(dateMenu, -2);
            System.out.println("---------------");
            System.out.println(DateUtil.format(date, DateUtil.PATTERN_YYYYMMDDHH));
            System.out.println("---------------");
        }



//        List<Date> dates = DateUtil.getDates(5);
//        System.out.println(dates);
    }

    @Test
    public void test02(){

    }
}
