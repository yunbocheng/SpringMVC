package com.yunbocheng.handler;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author : YunboCheng
 * @date : 20:55 2021/11/24
 */

/*
* 这是一个拦截器类，需要继承HandlerInterceptor接口
* 并且实现其中的三个类。
*
* 这个类用于拦截用户的请求。
* */

public class MyInterceptor implements HandlerInterceptor {

    // 实现HandlerInterceptor这个接口中的三个方法，查看源码可以看到者三个方法是使用Default声明的方法
    // 所以我们不需要全部的实现这三个方法
    // 我们这里实现这三个方法。

    /*
    * preHandle() : 这个方法叫做预处理方法。
    * 参数：
    *   Object handler ：被拦截的控制器对象(也就是项目中的MyController对象)
    * 返回值：boolean
    *   true : 表示此时请求通过了拦截器的验证，可以执行处理器方法处理这个请求。
    *   false : 表示此时的请求没有通过拦截器的验证，不可以执行处理器方法处理这个请求。
    *
    *  特点：
    *   1. 方法是在控制器方法(MyController的doSome)之前先执行的。
    *      用户的请求首先到达此方法
    *   2. 在这个方法中可以获取请求的信息。验证请求是否符合要求。
    *      可以验证用户是否可以登录，验证用户是否有权限访问某个连接地址(url)
    *      如果验证失败，可以截断请求，请求不能被处理。
    *      如果验证成功，可以放行请求，此时控制器方法才可以执行。
    * */
    private long bTime;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        bTime = System.currentTimeMillis();
        System.out.println("拦截器的MyInterceptor的perHandler()");

        // 在这个方法中进行业务逻辑的判断，返回true还是false，进而控制这个请求可不可以交给处理器方法进行处理。

        /*当请求被拦截器拦截下来的时候，给浏览器一个返回页面结果*/
        /*request.getRequestDispatcher("/tips.jsp").forward(request,response);*/
        return true;
    }

    /*
    * postHandle : 后处理方法。
    *
    * 参数：
    *   Object handler ： 被拦截的处理器对象MyController。
    *   ModelAndView modelAndView : 处理器方法的返回值。
    *
    * 返回值：void
    *
    * 特点：
    *   1. 方法是在处理方法之后执行的(MyController.doSome())
    *   2. 能够获取到处理器方法的返回值ModelAndView，可以修改ModelAndView中的
    *      数据和视图，可以影响最后的执行结果。
    *   3. 主要对原来的执行结果进行二次修饰。
    * */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        /*执行这个处理结果的方法的前提是预处理方法返回的为true*/
        System.out.println("拦截器的MyInterceptor的postHandle()");

        // 对原来的处理器方法的返回值进行处理。
        if (modelAndView != null){
            // 添加返回值中的数据
            modelAndView.addObject("myDate",new Date());
            // 修改返回值的数据
            modelAndView.addObject("myAge",40);
            // 修改返回的视图
            modelAndView.setViewName("other");
        }
    }


    /*
    * afterCompletion : 最后执行的方法
    *   参数：
    *      Object handler : 被拦截的处理器对象。
    *      Exception ex : 程序中发生的异常。
    *  特点：
    *      1. 是在请求处理完成后执行的。框架中的规定是当你的视图处理完成后，对视图执行了forward。就认为是请求处理完成了。
    *      2. 一般是做资源回收工作的，程序请求过程中创建一些对象，在这里可以删除，把占用的内存回收。
    * */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("拦截器的MyInterceptor的afterCompletion");
        long eTime = System.currentTimeMillis();
        System.out.println("计算preHandler到请求处理完成的时间：" + (eTime - bTime));
    }
}
