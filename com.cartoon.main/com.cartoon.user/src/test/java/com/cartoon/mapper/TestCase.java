package com.cartoon.mapper;

import com.cartoon.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCase {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void reg() {
        String[] split = "7ed790b17f960c25331263e8b0ff921.jpg".split("\\.");
        System.out.println(Arrays.toString(split));
    }

    @Test
    public void testCalendar() throws Exception {
        String newDate = "2020-06-01 13:43:45";
        Integer num = 30;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currdate = format.parse(newDate);
        System.out.println("现在的日期是：" + currdate);
        Calendar ca = Calendar.getInstance();
        ca.setTime(currdate);
        ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
        currdate = ca.getTime();
        String enddate = format.format(currdate);
        System.out.println("增加天数以后的日期：" + enddate);
    }

    @Test
    public void testRedis(){
        Map<String,Object> params = new HashMap<>();
        params.put("1","a");
        redisUtil.hmset("testredis",params);
        params.clear();
        params.put("2","b");
        redisUtil.hmset("testredis",params,10);
        params.clear();
        params.put("3","c");
        redisUtil.hmset("testredis",params,20);

    }

}
