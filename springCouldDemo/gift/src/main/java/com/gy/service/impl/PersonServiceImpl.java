package com.gy.service.impl;

import com.gy.entity.Person;
import com.gy.mapper.PersonMapper;
import com.gy.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: xiongping22369
 * @Date: 2018/8/17 08:55
 * @Description:
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonMapper personMapper;

    public void testTransactional() {
        Person person = new Person();
        person.setUsername("Transactional");
        person.setPassword("Transactional");
        person.setSex(1);
        person.setAge(18);
        personMapper.save(person);
        Person person1 = new Person();
        person1.setId(2L);
        person1.setPassword("Transactional");
        // 返回插入的记录数 ，期望是1条 如果实际不是一条则抛出异常
        System.out.println(personMapper.update(person1));
        throw new RuntimeException();
    }


}
