package com.yunbocheng.controller;

import com.yunbocheng.entity.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @author : YunboCheng
 * @date : 11:42 2021/11/18
 */

@Controller
public class MyController {

    /*
    * 接收请求参数：
    *   要求：处理器(控制器)方法的形参名和请求中参数名必须一致。
    *       同名的请求参数赋值给同名的形参。与位置无关。
    *
    * 框架接收请求参数原理
    *   1. 使用request对象接收请求参数
    *      String name = request.getParameter("name");
    *      String age = request.getParameter("age");
    *   2. springmvc框架通过 DispatcherServlet 调用 MyController的doSome()方法
    *      调用方法时，按名称对应，把接收的参数赋值给形参
    *       doSome(name,Integer.valueOf(age))
    *       框架会提供类型转换的功能，能把String转换为int，long，double等类型
    *
    *   400状态码：是客户端错误，表示提交请求参数饿过程中，发生了错误。
    *             比如给int类型的年龄传递了一个空字符串，或者是"abc"等。
    *             此时String age = request.getParameter("null")
    *             此时获取到的是一个空字符串 null
    *             然后需要将这个String类型的age转换为int类型
    *             Integer.valueOf(age); 因为age是空字符串，不可以转换为int值，
    *              此时会发生错误，报400的错误。
    *   400状态码会在网页上显示错误，但是不会再控制台打印错误信息。
    *       错误信息：java.lang.NumberFormatException(类型转换异常)
    *
    *   为了解决这种转换的异常：
    *       1. 可以将基本数据类型转换为对应的包赚类，但是这个只能null的问题。String类型以及Double类型仍然无法转换为Integer类型
    *       2. 可以直接声明为字符串，这样的话就可以接收一切类型。比如：22岁、22.5岁
    *   如果声明为String类型的话，后边要使用的话可以使用 Integer.valueOf(age) 手动转换，然后进行相应的操作
    *
    *  如果参数不可以正常的接收，那么这个doSome(String name,Integer age)方法根本不会执行
    *
    * */
    /*以下的方式是：逐个接收参数的方式，同名的参数匹配同名的形参，与名称有关，与位置无关 */
    @RequestMapping(value = "/receiveproperty.do")
    public ModelAndView doSome(String name, int age){
        ModelAndView mv = new ModelAndView();
        /*在框架中提供了自动类型转换的方式，可以把String类型自动转换为int、long、double类型*/

        /*
        * 在SpringMVC中，post的请求方式是存在中文乱码问题
        *               get请求不存在中文乱码问题
        * 将处理乱码的方式写在代码里太繁琐，这样的话每一个方法都需要处理一次乱码问题。
        * 所以我们此时放在一个 过滤器中统一处理中文乱码问题
        *
        *
        * */
/*        if (name != null){
            // 转码
            name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
        }*/

        mv.addObject("msg","成功接收到了用户的参数值");
        mv.addObject("myName",name);
        mv.addObject("myAge",age);
        mv.setViewName("show");
        return mv;
    }

    /*
    * 当请求参数名和处理器方法的形参名不一样的时候
    *
    * @RequestParam : 在逐个接收请求参数的方法中。参数的解决请求中参数信息和处理器方法的形参名不一样的问题
    *   属性：
    *       1.value：请求中的参数名称(也就是jsp中的用户传递的参数)
    *                只有一个属性的时候，value值可以省略，但是当两个属性的时候
    *                就不可以省略value
    *       2.require : 是一个boolean类型，默认是true
    *                   true：表示请求中必须包含此参数(rName,rAge)
    *                   此时当我们发送的请求没有这两个参数的时候，
    *                   此时就会报400的错误(服务端错误)。也就是用户输入的参数错误
    *
    *                   false ：此时代表这个处理器方法中可以不包含此参数(rName,rAge)
    *
    *       3.这个方式就可以用在让请求中必须包含哪些参数，可以不包含哪些参数信息。
    *
    *   位置：
    *    在处理器方法的形参定义的前面
    * */
    @RequestMapping(value = "/receivechange.do")
    public ModelAndView doSome1(@RequestParam(value = "rName",required = false) String name,
                                @RequestParam(value = "rAge",required = false) int age){
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg","成功接收到了用户的参数值");
        mv.addObject("myName",name);
        mv.addObject("myAge",age);
        mv.setViewName("show");
        return mv;
    }

    /*
    * 处理器方法形参是Java对象，这个对象的属性名和请求中参数名一样。
    * 框架会创建形参的Java对象（Student这个对象不是我们创建的。是框架自动调用实体类的无参构造方法创建的），
    * 给属性赋值（自动调用Java对象的set方法来进行赋值，我们直接调用get方法取值即可）。
    * 请求中的参数是name，框架会调用setName()进行自动的赋值。
    *
    * 这就说明：当我们赋值，此时在实体类中必须存在类的无参构造方法以及set方法。
    *         当我们取值时，需要使用get方法获取参数值，说明此时在实体类中需要有get方法
    *
    *
    * 这个和spring中的set注入是一样的。先使用实体类的无参构造创建一个对象
    * 之后调用set方法进行对象属性的赋值。
    * * */
    @RequestMapping(value = "/receiveObject.do")
    /*
        这个方法的参数中可以传递任意多个形参,这些对象以及参数都会框架自动创建并且赋值。
         public ModelAndView doSome2(Student student,School school,int age){
            ...
         }
    */
    /*
    * 注意在使用java对象传递参数的时候，不可以使用 @RequestParam注解
    * 因为是对象，属性全部封装到了这个对象中，此时根本就不存在指定的形参，形参只是一个实例对象
    * 属性全部封装到了这个对象中，比较不出来，只有逐个赋值的时候，使用@RequestParam才有意思
    * 当然，使用对象以及逐个赋值可以一起使用，如下：
    * */
    public ModelAndView doSome2(Student student,@RequestParam(value = "rSex") String sex){
        System.out.println("receiveObject，name = "+ student.getName() + "receiveObject，age = " + student.getAge());
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg","成功接收到了用户的参数值");
        mv.addObject("myName",student.getName());
        mv.addObject("myAge",student.getAge());
        mv.addObject("mySex",sex);
        mv.setViewName("show1");
        return mv;
    }

    /*
    * 注意：处理器方法中的形参是任意的，List集合、Map集合以及普通类型或者Object都是可以的。
    *      只是使用ajax请求的时候List、Map的请求数据类型太复杂了。并且也之只有处理ajax的时候才
    *       会涉及到List集合、Map集合等。
    *       使用普通的jsp的input以及a链接的请求的时候根本不会使用到List集合、Map集合。
    * */
}
