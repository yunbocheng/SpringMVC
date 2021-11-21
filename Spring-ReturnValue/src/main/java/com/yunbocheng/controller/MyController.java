package com.yunbocheng.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunbocheng.entity.Student;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : YunboCheng
 * @date : 11:42 2021/11/18
 */

@Controller
public class MyController {
    /*
    * 处理器方法返回String--表示逻辑视图名称，需要配置视图解析器
    * 使用视图解析器
    * */
    @RequestMapping(value = "/returnString-view.do")
    public String returnView(String name, int age, HttpSession session){
        /*
        * show : 此时代表的是逻辑视图名称，此时需要配置视图解析器
        *
        * 这里使用逐一接收参数的方式，此时需要保证请求参数名和处理器方法的形参名一致。
        *
        * 当返回值类型是String的时候，此时相当于只是执行了 forWord("/show")
        * 不携带任何的数据，只是单纯的为了跳转页面。
        * */
        // 我们这里虽然不是创建的ModelAndView对象，不能自动赋值，但是我们可以手动赋值
        // 使用和 Servlet 中一样的方式，request.setAttribute("myName",name)
        // 或者使用 session.setAttribute("myName",name)进行赋值
        session.setAttribute("myName",name);
        session.setAttribute("myAge",age);
        return "show";
    }

    /*
    * 不使用视图解析器，使用全路径名称，需要将视图解析器注释掉或者删除
    * */
    @RequestMapping(value = "/returnString-view2.do")
    public String returnView2(String name, int age, HttpSession session){
        /*
         * WEB-INF/view/show.jps  : 此时代表的是全路径视图名称，此时不可以配置视图解析器
         *                          如果配置了视图解析器，框架会把WEB-INF/view/show.jps
         *                           当作一个 逻辑名称。此时的路径信息是 ：
         *                          /WEB-INF/view/WEB-INF/view/show.jsp.jsp
         *                          他会把视图解析器的路径拼接上。
         *                          即使此时你将这个jsp页面转移到别的路径下，还是是拼接上视图解析器中的路径
         *                          /WEB-INF/view/WEB-INF/jsp/show.jsp.jsp
         *                          此时只能将视图解析器注释掉或者删除
         * 当返回值类型是String的时候，此时相当于只是执行了 forWord("/show")
         * 不携带任何的数据，只是单纯的为了跳转页面。
         *
         *
         * */
        session.setAttribute("myName",name);
        session.setAttribute("myAge",age);
        return "/WEB-INF/jsp/show.jsp";
    }

    /*
        处理器返回结果为void，响应ajax请求
        使用ajax请求获取参数的方式和使用表单获取请求参数的方式一样
        逐个获取以及对象获取
        这里使用逐个接收的方式，必须保证ajax中的请求参数名和处理器方法中的形参名一样。
    */
    @SneakyThrows
    @RequestMapping(value = "/returnVoid-ajax.do")
    public void returnAjax(String name, int age,HttpServletResponse response){
        System.out.println("returnAjax==, name=" +name + " age = " + age);
        // 处理ajax请求，使用json做数据的格式
        // 我们假设service处理完了业务，返回了一个Student对象
        // 此时这个Student代表处理结果
        Student student  = new Student();
        // 这个name和age是ajax请求中的data的数据值
        student.setName(name);
        student.setAge(age);


        // ajax需要的数据格式是json格式，我们需要将结果使用jackson转换为json依赖
        // 获取到的结果是一个json格式的字符串
        String json = "";
        if (student != null){
            // 此时代表存在数据，转换为json格式的数据，调用jackson中的方法转换
            ObjectMapper com = new ObjectMapper();
            json = com.writeValueAsString(student);
            System.out.println(json);
        }

        // 输出数据，设置响应内容的格式，此时要求是一个json格式
        response.setContentType("application/jason;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println(json);
        out.flush();
        out.close();
    }
    /*
    * 总结：
    *   当我们使用jackson进行格式转换的时候，涉及到了大量重复的代码，此时我们需要将这些代码整合到一个其他类型返回值的方法中、
    *   此时使用Object作为返回值类型。
    * */


    /*
    * 此时返回的只是单纯的一个Object对象，这个Object会被转换为一个Json对象
    * 返回值是Object，返回值是一个对象，通过springmvc框架转换为json格式的数据
    * 此时返回的是一个Student对象。响应ajax请求
    *
    * @ResponseBody:
    *   作用：把处理器方法返回的对象转换为json后，通过HttpServletResponse输出给浏览器。
    *   位置：在方法的定义上面。和其他注解没有舒顺序的关系
    *
    * 返回对象框架的处理流程：
    *   1. 框架会把返回Student类型的数据，调用框架中的ArrayList<HttpMessageConverter>
    *      中每个类的canWriter()方法，检查哪个HttpMessageConverter接口的实现类可以处理Student这个类型的数据。
    *      当调用canWriter()方法返回true的时候说明这个类可以处理这个Student对象
    *       也就是这个类 MappingJackson2HttpMessageConverter
    *   2. 框架会调用实现类的write()方法，也就是MappingJackson2HttpMessageConverter类的write()方法。
    *      把李四的Student对象转换为json，调用Jackson的ObjectMapper实现转换为json
    *
    *   3. 框架会调用@ResponseBody把第二步处理获取到的json字符串输出到浏览器，ajax请求处理完毕
    *
    * */
    @RequestMapping(value = "/returnStudentJson.do")
    @ResponseBody
    public Student doStudentJsonObject(String name,Integer age){
        // 调用Servlet，获取请求结果的值，Student对象表示请求处理的结果
        Student student  = new Student();
        student.setAge(20);
        student.setName("李四");
        return student;  //这个对象会被springmvc转换为json
    }


    /*
    * 此时返回的是一个Student的List集合，这个对象集合最终对被转换为一个JSON对象数组
    * 注意：
    *   这里Service方法处理之后返回的是一个Student集合。但是最终会转换为一个JSON对象数组。
    *   返回的是一个JSON数组，此时我们可以使用遍历数组的方式(下标)来获取到这个JSON数组中的参数值。
    *
    * 注意：
    *   在JSON数组中对象的顺序和Student的List集合中添加对象的顺序一致。
    *   也就是student1对应JSON数组中下标为0的JSON对象
    *        student2对应JSON数组中下标为1的JSON对象
    *
    * 我们也可以使用 Map集合，但是Map集合是无序的，不推荐使用。使用不方便
    * */
    @RequestMapping(value = "/returnStudentJsonArray.do")
    @ResponseBody
    public List<Student> doStudentJsonObjectList(String name,Integer age){
        List<Student> list = new ArrayList<>();
        // 调用Service方法，获取请求结果中的数据，Student对象表示结果数据
        // 假设调用Service方法之后的返回的Student的集合中有两个Student对象
        // 第一个Student对象
        Student student1 = new Student();
        student1.setName("张三");
        student1.setAge(20);
        list.add(student1);

        // 第二个Student对象
        Student student2 = new Student();
        student2.setAge(22);
        student2.setName("李四");
        list.add(student2);

        return list;
    }

    /*
    * 处理器方法返回的是String，此时表示的是数据，不是视图。
    *
    * 区分返回值String是数据 ，还是视图，看处理器方法上面有没有@ResponseBody注解
    * 如果有@ResponseBody注解，说明返回值String是数据，反之是视图。
    *
    * 注意：
    *   当返回值是普通文本的时候，默认使用的编码格式是："text/plain;charset=ISO-8850-1"作为ContentType
    *   此时会发生中文乱码问题。
    * 解决方案：给@RequestMapping注解添加一个属性 produces，使用这个属性指定新的ContextType。
    *
    * */
    @RequestMapping(value = "/returnStringData.do",produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String doStringData(String name,Integer age){
        return "Hello SpringMVC 返回对象，表示数据!";
    }
}
