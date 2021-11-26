package com.yunbocheng.controller;

import com.yunbocheng.entity.Student;
import com.yunbocheng.exception.AgeException;
import com.yunbocheng.exception.MyUserException;
import com.yunbocheng.exception.NameException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping(value = "/exception.do")
    public ModelAndView exception(String name,int age) throws MyUserException {
        ModelAndView mv = new ModelAndView();
        // 根据请求参数抛出异常
        /*
        * 处理的顺序：
        *   代码从上往下执行，如果满足第一个if条件的话，那么程序会进入到这个异常的类中，
        *   不会在继续执行这个处理器方法，此时跳转到异常处理的类中，也就是被@ControllerAdvice注解
        *   标注的类。这里就是 GlobalExceptionHandel 这个类。
        *
        * 在异常类中，会根据抛出异常的类型在异常类中寻找被@ExceptionHandler(value = NameException.class)
        * 标注的方法上的这个注解中的value属性的值，进行异常的处理。
        * */
        if (!"程云博".equals(name)){
            throw  new NameException("输入的姓名不正确！");
        }
        if (age == 0|| age > 80){
            /*
                这里指定的抛出异常的信息相当于系统抛出的异常，类似于NullPointException
                属于系统界别的异常
            * */
            throw new AgeException("年龄不符合要求！");
        }
        mv.addObject("myName",name);
        mv.addObject("myAge",age);
        mv.setViewName("show");
        return mv;
    }
}
