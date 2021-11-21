package com.yunbocheng.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author : YunboCheng
 * @date : 11:42 2021/11/18
 */

/*
* @Controller : 创建处理器对象，对象放在SpringMVC容器中
*
* 可以处理请求的都是控制器(处理器): MyController能处理请求，叫做后端处理器(back controller)
*
 * 位置：在类的上面
*
* 和Spring中讲的@Service、@Component是一样的，都是为了创建对象的。
* */
@Controller
public class MyController {
    /*
    * 处理用户提交的请求，SpringMVC中是使用方法来处理的。
    * 方法是自定义的，可以有多种返回值，多种参数，方法名称自定义
    *
    * 我们准备使用 doSome()这个方法处理 some.do请求。
    * @RequestMapping ： 请求映射，作用是把一个请求地址和一个方法绑定在一起。
    *                    一个请求要指定一个方法处理。
    * 属性：
    *   1. value : 是一个String，表示请求的 uri 地址（some.do）
    *               value是唯一的，不能重复。在使用时，推荐地址以"/"开头
    * 位置：
    *   1. 在方法的上面。常用的
    *   2. 在类的上面
    * 说明：
    *   使用@RequestMapping修饰的方法叫做处理器方法或者控制器方法
    *   使用@RequestMapping修饰的方法是可以处理请求的，类似Servlet中国的doGet()、doPost()、servlet()
    *
    * 返回值：ModelAndView表示本次请求的处理结果
    *   Model：数据，请求处理完成后，要显示给用户的数据
    *   View : 视图，比如：jsp等
    *
    *  @RequestMapping中的value属性是一个数组，可以向其中放入多个请求。
    *               这样这些请求就都可以调用这个方法进行servlet请求的处理
    * @RequestMapping(value = {"/some.do","/first.do"})
    * */
    /*value以及”/" 都可以省略不写*/
    /*@RequestMapping(value = "some.do")*/
    /*@RequestMapping("some.do")*/
    @RequestMapping(value = {"/some.do","first.do"})
    public ModelAndView doSome(){  // 类似于doGet()、doPost()-->service处理请求
        // 处理some.do的请求。这个方法我们先不调用service，先测试一个SpringMVC框架的使用
        // 1.创建一个返回值对象(ModelAndView，这个类是SpringMVC框架汇中的一个类，直接创建)
        ModelAndView mv = new ModelAndView();
        // 2. 添加数据(Model)，SpringMVC框架在请求的最后会把数据放入到request作用域中。
        // 相当于：request.setAttribute("msg","欢迎使用SpringMVC框架做Web开发")
        mv.addObject("msg","欢迎使用SpringMVC框架做Web开发");
        mv.addObject("fun","执行的是doSome方法");

        // 3. 指定视图，指定视图的完整路径
/*        // 相当于框架对视图的forward操作,request.getRequestDispather("/show.jsp").forward(req,resp)
        mv.setViewName("/WEB-INF/view/show.jsp");*/

        // 当配置了视图解析器，可以使用逻辑名称(文件名)，指定视图
        // SpringMVC会使用视图解析器的 前缀 + 逻辑名称 + 后缀 组成完成的路径，这里就是字符串的拼接
        // /WEB-INF/view/ + show + .jsp  (/WEB-INF/view/show.jsp)
        mv.setViewName("show");
        //返回视图mv
        return mv;
    }

    // 在一个类中可以存在多个处理请求的方法，当然@equestMapping中的属性是唯一的。
    @RequestMapping(value = {"/other.do","/second.do"})
    public ModelAndView doOther(){  // 类似于doGet()、doPost()-->service处理请求
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg","欢迎使用SpringMVC框架做Web开发");
        mv.addObject("fun","执行的是doOther方法");
        mv.setViewName("other");
        return mv;
    }
}
