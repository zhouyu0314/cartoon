package com.gy.test;

import com.alibaba.fastjson.JSON;
import com.gy.entity.Gift;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JsonTestCase {

    @Test
    public void test1(){
        String str = "[{'id':830514884901867520, 'giftname':'华为bj', 'price':5999.0, 'count':3}, {'id':830306239173890048, 'giftname':'华为bj', 'price':5999.0, 'count':5}]";
        String str1 = "[{id:830514884901867520, giftname:华为bj, price:5999.0, count:3}, {id:830306239173890048, giftname:华为bj, price:5999.0, count:5}]";
        String str2 = "{id:830514884901867520, giftname:华为bj, price:5999.0, count:3}";
        Gift parse = (Gift)JSON.parse(str2);
        System.out.println(parse);
        List<Gift> gifts = JSON.parseArray(str, Gift.class);
        System.out.println(gifts);
    }
}
