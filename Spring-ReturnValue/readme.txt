Hello-SpringMVC : 处理器方法的返回值类型

处理器方法的返回值表示请求的处理结果
    1. ModelAndView : 返回视图以及数据 当处理器方法处理之后需要返回视图以及数据的时候，返回值类型使用ModelAndView，对视图执行forward
                      此时执行的操作是：跳转指定的界面且在界面上显示获取到的数据

    2. String : 只有视图，是视图的名称，可以使用逻辑名称，也可以使用完整的视图路劲。(需要配置视图解析器)
                此时执行的操作只是跳转界面，不会获取到任何数据。

    3. void : 不能表示数据，也不能表示视图。
              这种方式在处理AJAX的时候可以使用的到，可以使用void返回值。
              通过HttpServletResponse输出数据。响应ajax请求。
              ajax请求服务器端返回的就是数据，和视图无关。因为ajax是异步刷新。

    4. Object : 返回的是数据，一个对象。例如：String、Integer、Map、List、Student等都是对象
                对象有属性，属性就是数据。所以返回Object表示的是数据，和视图无关。
                可以使用对象表示的数据，响应ajax请求，此时只能响应ajax请求，
                因为返回的是只能是数据，步可以是视图，所以不可以跳转页面，此时只能完成ajax请求(异步刷新)

     现在做ajax请求，主要使用的是json的数据格式。
     面试题：springmvc返回值对象输出json格式应该怎么处理：
        就是以下的三步：1. 加入jackson依赖。
                     2. 添加 <mvc:annotation-driver> 注解驱动。
                     3. 在处理器方法上加入 @ResponseBody注解
     实现步骤：
        1. 加入处理json的工具库的依赖，springmvc默认使用的是jackson的依赖。
        2. 在Java代码中我们需要手动将一个响应的Java对象转换为一个json格式的字符串
                    ObjectMapper com = new ObjectMapper();
                    json = com.writeValueAsString(student);
           但是在springmvc中我们只需要在springmvc的配置文件中加入 <mvc:annotation-driver> 注解驱动。
        3. 在处理器方法上面加入 @ResponseBody注解 ，类似于普通Java代码中输出结果的这部分
                response.setContentType("application/jason;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println(json);
        使用这三步就可以完成将一个Java对象转换为一个json格式的字符串。

        springmnv处理器返回Object，可以将java对象转换为json输出到浏览器，响应ajax的内部原理。
        1. <mvc:annotation-driver> 注解驱动。
            注解驱动实现的功能是 ：完成Java对象到json、xml、text、二进制等数据格式的转换。
            HttpMessageConverter接口：消息转换器。这个接口Java对象到json、xml、text等数据格式的转换方法
            这个接口有很多的实现类，这些实现类完成了Java对象到json、xml、text等数据格式的转换。
            这个接口一共存在7个实现类

            没有加入注解驱动标签时的状态
            org.springframework.http.converter.ByteArrayHttpMessageConverter
            org.springframework.http.converter.StringHttpMessageConverter
            org.springframework.http.converter.xml.SourceHttpMessageConverter
            org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter

            加入注解驱动标签时的状态
            org.springframework.http.converter.ByteArrayHttpMessageConverter
            org.springframework.http.converter.StringHttpMessageConverter
            org.springframework.http.converter.ResourceHttpMessageConverter
            org.springframework.http.converter.ResourceRegionHttpMessageConverter
            org.springframework.http.converter.xml.SourceHttpMessageConverter
            org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter
            org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter
            org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
            通过以上的分析可以知道：如果想要获取到json格式的数据，一定需要添加注解驱动，只有添加注解驱动
            才可以创建出HttpMessageConverter接口的实现类json.MappingJackson2HttpMessageConverter
            只有这个实现类才可以处理json格式的数据。


        2. @ResponseBody注解 : 放在方法的上面，通过HttpServletResponse输出数据，响应ajax请求的。

实现步骤：
    1. 新建web Maven工程 （如果使用idea提供的Maven Web 框架，需要修改web.xml中的头文件，因为版本太低，不可以使用EL表达式
    2. 加入依赖 spring-webmvc,这个就代表SpringMVC，
       间接把spring的依赖都加入到项目中。
       jsp、servlet依赖。

    3. 重点：在web.xml中注册springmvc的核心对象DispatcherServlet
        1) DispatcherServlet叫做中央调度器，是一个Servlet，它的父类是继承HttpServlet的。
        2) DispatcherServlet也叫做前端控制器（front controller）
        3) DispatcherServlet负责接收用户提交的请求，调用其他的控制器对象(就是使用@Controller注解创建的对象)
           并把请求的处理结果显示给用户。只要使用SpringMVC模式必定存在DispatcherServlet(中央调度器对象)

        4.创建一个发起请求的页面 index.jsp

        5.创建控制器(处理器)类
          1) 在类的上面加入@Controller注解，创建对象，并放入到SpringMVC容器中，
          2) 在类中的方法上面加入@RequestMapping注解。

        6. 创建一个显示处理结果的jsp页面

        7. 创建SprigMVC的配置文件(和Spring的配置文件一样)
           1) 声明组件扫描器，指定@Controller注解所在的包名
           2) 声明视图解析器。帮助处理视图。
