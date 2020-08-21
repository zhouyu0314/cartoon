package com.cartoon.test;

import com.cartoon.config.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MathTest {
    @Test
    public void test01(){
        int i = (int) (Math.random() * 10 + 1);
        List<Date> dateMenus = DateUtil.getDateMenus();
        System.out.println(dateMenus);
        List<Date> dates = DateUtil.getDates(5);
        System.out.println(dates);
    }

    @Test
    public void test02(){
        System.out.println(DateUtil.getDateMenus());
    }
}
