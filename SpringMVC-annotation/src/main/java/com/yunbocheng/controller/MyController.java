package com.yunbocheng.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author : YunboCheng
 * @date : 11:42 2021/11/18
 */
/*
* 我们可以观察到此时的 请求路径中都存在test，此时我们可以把这个公共的部分抽取出来
* 在类的上面使用 @RequestMapping注解将这写公共的部分抽取出来，设置为一个模块
* 这么做的目的是当请求的模块发生变化的时候，只需要改变这一个地方就可以了，就可以把请求路径全部改变
*
* @RequestMapping :
*   value : 所有请求的共部分(value是可以省略的)，叫做模块名称
*   位置：在类的上面
* */
@Controller
@RequestMapping(value = "/User")
/*@RequestMapping("/test")*/
public class MyController {
    /*
    * @RequestMapping : 请求映射
    *   属性：method ,表示请求的方式。它的值RequestMethod类的枚举值。
    *       例如：表示get请求方式，RequestMethod.GET
    *            表示post请求方式，RequestMethod.POST
    *
    * */
    // 指定some.do使用get请求方式
    /*@RequestMapping(value = "/test/some.do")*/
    @RequestMapping(value = "/some.do",method = RequestMethod.GET)
    public ModelAndView doSome(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg","欢迎使用SpringMVC框架做Web开发");
        mv.addObject("fun","执行的是doSome方法");

        mv.setViewName("show");
        //返回视图mv
        return mv;
    }

    // 指定some.do使用post请求方式
    /*@RequestMapping(value = {"/test/other.do","/test/second.do"})*/
    @RequestMapping(value = "/other.do",method = RequestMethod.POST)
    public ModelAndView doOther(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg","欢迎使用SpringMVC框架做Web开发");
        mv.addObject("fun","执行的是doOther方法");
        mv.setViewName("other");
        return mv;
    }

    // 不指定请求方式，没有限制，使用GET或者POST都可以
    @RequestMapping(value = "/first.do")
    public ModelAndView doFirst(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg","欢迎使用SpringMVC框架做Web开发");
        mv.addObject("fun","执行的是doFirst方法");
        mv.setViewName("first");
        return mv;
    }

    /*
        处理器方法可以包含以下四种类型参数，这些参数会在系统调用的时由系统自动赋值，
        即程序可在方法内直接使用。

        HttpServletRequest （请求域对象）
        HttpServletResponse （响应域对象）
        HttpSession
        请求中所携带的请求参数
    */

    // 以下使用前三种获取参数的对象，直接使用即可
    @RequestMapping(value = "/second.do")
    public ModelAndView doSecond(HttpServletRequest request,
                                 HttpServletResponse response,
                                 HttpSession session){
        ModelAndView mv = new ModelAndView();
        /*直接使用 request.getParameter("name")就可以获取到用户的参数信息*/
        mv.addObject("msg","欢迎使用SpringMVC框架做Web开发" + request.getParameter("name"));
        mv.addObject("fun","执行的是doFirst方法");
        mv.setViewName("first");
        return mv;
    }
}
