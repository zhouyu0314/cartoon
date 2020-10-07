package com.gy.mapper;

import com.gy.config.DataSourceKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class testCase {

    @Test
    public void test01(){
        System.out.println(DataSourceKey.getArea(1));
    }


//    @Autowired
//    private PersonMapper1 personMapper;
//
//    @Autowired
//    private PersonServiceImpl userService;
//
//    @Test
//    public void save() {
//        Person person = new Person();
//        person.setUsername("master");
//        person.setPassword("master");
//        person.setSex(1);
//        person.setAge(18);
//        Assert.assertEquals(1, personMapper.save(person));
//    }
//
//    @Test
//    public void update() {
//        Person person = new Person();
//        person.setId(8L);
//        person.setPassword("newpassword");
//        // 返回插入的记录数 ，期望是1条 如果实际不是一条则抛出异常
//        Assert.assertEquals(1, personMapper.update(person));
//    }
//
//    @Test
//    public void selectById() {
//        Person person = personMapper.selectById(2L);
//        System.out.println("id:" + person.getId());
//        System.out.println("name:" + person.getUsername());
//        System.out.println("password:" + person.getPassword());
//    }
//
//    @Test
//    public void deleteById() {
//        Assert.assertEquals(1, personMapper.deleteById(1L));
//    }
//
//    @Test
//    public void selectAll() {
//        List<Person> people = personMapper.selectAll();
//        people.forEach(person -> {
//            System.out.println("id:" + person.getId());
//            System.out.println("name:" + person.getUsername());
//            System.out.println("password:" + person.getPassword());
//        });
//    }
//
//    @Test
//    public void testTransactional() {
//        userService.testTransactional();
//    }

}
