package com.yunbocheng.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author : YunboCheng
 * @date : 11:42 2021/11/18
 */

/*
* 这个类中我们使用Java代码处理异常(也就是try...catch)
* 我们使用框架来处理异常
* */

@Controller
public class MyController {
    @RequestMapping(value = "/doSome.do")
    public ModelAndView exception(String name,int age)  {
        ModelAndView mv = new ModelAndView();
        mv.addObject("myName",name);
        mv.addObject("myAge",age);
        mv.setViewName("show");
        return mv;
    }
}
