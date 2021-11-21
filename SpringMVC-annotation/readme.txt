Hello-SpringMVC : @RequestMapping注解的使用
需求：用户在页面发起一个请求，请求交给SpringMVC的控制器对象，
     并显示请求的处理结果(在页面显示一个欢迎界面)

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
