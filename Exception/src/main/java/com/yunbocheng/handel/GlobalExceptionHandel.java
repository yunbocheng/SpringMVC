package com.yunbocheng.handel;

import com.yunbocheng.exception.AgeException;
import com.yunbocheng.exception.NameException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author : YunboCheng
 * @date : 11:23 2021/11/24
 */

/*
* @ControllerAdvice :控制类增强(也就是给控制器类增加功能-->异常处理的功能)
*   位置：在类的上面
*   特点：必须让框架知道这个注解所在的包名，需要在SpringMVC的配置文件中加入组件扫描器。
*        指定@ControllerAdvice所在的包名
*   这个使用的是Spring中的AOP技术，就是在原有的业务代码中加入一些与业务无关的方法。
*   比如日志、错误信息等。
* */
@ControllerAdvice
public class GlobalExceptionHandel {
    // 定义方法来处理发生的异常
    /*
    * 处理异常的方法和处理控制器的方法一样，
    * 可以有多种参数：普通数据类型、Object对象类型、List、Map集合都可以。
    * 可以有多种返回值类型ModelAndView、String、void对象、list对象集合。
    *
    * 形参：Exception。表示Controller中抛出的异常对象。
    *      通过形参可以获取发生的异常信息。
    *
    * @ExceptionHandel(value = 异常类.class)：表示异常的类型，当发生此类型异常时
    *                               由当前方法进行处理。
    * */

    /*
    * SpringMVC处理异常的方式，他会在Controller类中接收到一个异常，之后在这个处理异常的类中
    * 寻找处理这个异常的方法，通过@ExceptionHandler注解的value值进行匹配。
    * 如果Controller类抛出的异常和@ExceptionHandler注解的value值都没有匹配到，
    * 那么就执行最后没有value值的@ExceptionHandler注解所对应的方法。
    *
    * 注意 ：@ExceptionHandler没有value属性的注解只能存在一个。相当于if...else中的else。
    * */
    @ExceptionHandler(value = NameException.class)
    public ModelAndView doNameException(Exception exception){
        // 处理NameException的异常。
        /*
        * 异常发生我们要处理的逻辑：
        *   1. 需要把异常记录下来，记录到日志文件或者数据库中。
        *      记录日志发生的时间，哪个方法发生的，异常信息内容。
        *   2. 发送通知，把异常的信息通过邮件、短信、维信发送给相关人员。
        *
        *   3. 给用户一个很好的复杂。
        * */
        ModelAndView mv = new ModelAndView();
        /*这里的异常信息只是一个提示用户的信息，这个信息是我们自己给的，显示给前端jsp页面的*/
        mv.addObject("msg","姓名必须的是程云博，其他用户不可以访问！");
        // 异常对象，这个就相当于系统抛出异常的那个对象。比如：NullPointException对象。
        // 或者是我们自定义的 AgeException、NameException。

        mv.addObject("ex",exception);
        mv.setViewName("nameError");
        return mv;
    }

    @ExceptionHandler(value = AgeException.class)
    public ModelAndView doAgeException(Exception exception){
        // 处理AgeException的异常。
        /*
         * 异常发生我们要处理的逻辑：
         *   1. 需要把异常记录下来，记录到日志文件或者数据库中。
         *      记录日志发生的时间，哪个方法发生的，异常信息内容。
         *   2. 发送通知，把异常的信息通过邮件、短信、维信发送给相关人员。
         *
         *   3. 给用户一个很好的复杂。
         * */
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg","您的年龄不符合要求！");
        // 异常对象
        mv.addObject("ex",exception);
        mv.setViewName("ageError");
        return mv;
    }

    // 处理其他异常，NameException、AgeException以外的异常，不知道的异常类型
    // 当错误信息不是NameException、AgeException的时候，就交给这个方法来处理异常
    @ExceptionHandler
    public ModelAndView doOtherException(Exception exception){
        // 处理其他的异常。
        /*
         * 异常发生我们要处理的逻辑：
         *   1. 需要把异常记录下来，记录到日志文件或者数据库中。
         *      记录日志发生的时间，哪个方法发生的，异常信息内容。
         *   2. 发送通知，把异常的信息通过邮件、短信、维信发送给相关人员。
         *
         *   3. 给用户一个很好的复杂。
         * */
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg","您的年龄不符合要求！");
        // 异常对象
        mv.addObject("ex",exception);
        mv.setViewName("defaultError");
        return mv;
    }
}
