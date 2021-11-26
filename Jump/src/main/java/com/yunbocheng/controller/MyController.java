package com.yunbocheng.controller;

import com.yunbocheng.entity.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author : YunboCheng
 * @date : 11:42 2021/11/18
 */

@Controller
public class MyController {

    /*
    * 使用请求转发的方式 forward
    * forward 语法： mv.setViewName("forward:跳转页面的全路径");
    *
    * forward特点：不和视图解析器一同使用，就当项目中没有这个视图解析器。
    *
    * */
    @RequestMapping(value = "/some.do")
    public ModelAndView doSome(String name,int age){
        ModelAndView mv = new ModelAndView();
        mv.addObject("myName",name);
        mv.addObject("myAge",age);
        /*使用forward请求转发的方式*/
        /*这个是请求转发的方式，可以请求到WEB-INF下的页面*/
        mv.setViewName("forward:/WEB-INF/view/show.jsp");
        return mv;
    }

    /*
    * 使用重定向的方式 redirect
    * 使用请求转发的方式 redirect
    * redirect 语法： mv.setViewName("redirect:跳转页面的全路径");
    * redirect 特点：不和视图解析器一同使用，就当项目中没有这个视图解析器。
    *
    * http://localhost:8080/myWeb/show.jsp?myName=%E7%A8%8B%E4%BA%91%E5%8D%9A&myAge=20
    * 当我们使用 redirect 重定向的时候，框架会对重定向做一些数据：
    *   1. 框架会把Model中的简单类型的数据，转为String使用，作为show.jsp的get请求参数使用。
    *      目的是在 some2.do与show.jsp 两次请求之间传递数据
    *      也就是将some2.do请求中的 name、age数据传递给 show.jsp
    *
    *   2. 为什么会存在这种传递参数的方式
    *       因为此时发生了两次的请求，产生了两个request域对象
    *       第一次将name、age数据存储到了 some.do请求的request作用域对象中。
    *       但是第二次请求的产生的request作用域中不存在这个name、age数据，
    *       所以框架的会帮我们把这些数据拼接到第二次请求get的后面
    *
    *   3. 在目标show.jsp页面中可以使用参数集合对象 ${param}获取请求参数值。
    *       ${param.myName}、${param.myAge}
    *
    *   4. redirect重定向不可以访问WEB-INF下的页面。
    * */
    @RequestMapping(value = "/some2.do")
    public ModelAndView doSome2(String name,int age){
        ModelAndView mv = new ModelAndView();
        /*此时的数据放到request作用域中*/
        mv.addObject("myName",name);
        mv.addObject("myAge",age);
        /*使用redirect请求转发的方式*/
        /*这个是重回定向的方式，请求不到WEB-INF下的网页*/
        mv.setViewName("redirect:/show.jsp");
        // http://localhost:8080/myWeb/show.jsp?myName=lisi&myAge=20
        return mv;
    }

    /*
    * 使用redirect转发一个对象，看redirect重定向的get请求是如何拼接的
    * 此时会发现，参数是对象和普通数据类型的get请求的字符串是一样的。
    * */
    @RequestMapping(value = "/some3.do")
    public ModelAndView doSome3(Student student){
        ModelAndView mv = new ModelAndView();
        /*使用redirect请求转发的方式*/
        /*这个是重回定向的方式，请求不到WEB-INF下的网页*/
        mv.addObject("myName",student.getName());
        mv.addObject("myAge",student.getAge());
        // http://localhost:8080/myWeb/show.jsp?myName=lisi&myAge=20
        mv.setViewName("redirect:/show.jsp");
        return mv;
    }

    /*
    * 以上的redirect重定向的方式是Ggt请求，这里我们使用POST请求试试
    * 不论我们第一次请求使用的是什么方式，当第二次请求的时候会使用GET请求的方式
    * */
    @RequestMapping(value = "/some4.do",method = RequestMethod.POST)
    public ModelAndView doSome4(Student student){
        ModelAndView mv = new ModelAndView();
        /*使用redirect请求转发的方式*/
        /*这个是重回定向的方式，请求不到WEB-INF下的网页*/
        mv.addObject("myName",student.getName());
        mv.addObject("myAge",student.getAge());
        // http://localhost:8080/myWeb/show.jsp?myName=lisi&myAge=20
        mv.setViewName("redirect:/show.jsp");
        return mv;
    }
}
