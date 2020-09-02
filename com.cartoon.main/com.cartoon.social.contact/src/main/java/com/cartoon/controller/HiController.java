package com.cartoon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
public class HiController {

    @RequestMapping("/websocket/{name}")
    public String webSocket(@PathVariable("name") String name,Model model){
        try{
            System.out.println("跳转到websocket的页面上");
            //通过Model进行对象数据的传递
            model.addAttribute("username",name);
            return "socket";
        }
        catch (Exception e){
            System.out.println("跳转到websocket的页面上发生异常，异常信息是："+e.getMessage());
            return "error";
        }
    }
}