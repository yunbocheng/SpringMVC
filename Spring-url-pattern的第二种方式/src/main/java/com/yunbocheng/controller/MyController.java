package com.yunbocheng.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;

/**
 * @author : YunboCheng
 * @date : 14:12 2021/11/21
 */
/*
以前需要使用继承HttpServlet的方法声明这是一个Servlet对象，此时使用这个方式声明这是一个Servlet对象
此时这个类只是一个普通的类
添加注解 @Controller，之后使用springmvc中的标签扫描器来创建这个普通的Servlet对象，只要有了Servlet对象
可以使用这个Servlet对象来处理请求。接收和显示请求。或者调用Service出路业务。

*/

@Controller
public class MyController {

    @RequestMapping(value = "/returnResult",method = RequestMethod.POST)
    public ModelAndView returnResult(String name,int age){
        // 创建ModelAndView对象
        ModelAndView mv = new ModelAndView();
        // 添加数据，也就是准备在显示页面显示的数据
        mv.addObject("name",name);
        mv.addObject("age",age);
        // 添加视图，也就是处理完请求之后添砖的页面
        // 这里也是使用的视图解析器的方式
        mv.setViewName("show");
        return mv;
    }
}
