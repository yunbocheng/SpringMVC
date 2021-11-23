SSM整合开发：
SSM编程，即SpringMVC+Spring+MyBatis整合，是当前最为流行的JavaEE开发技术架构。
其实SSM整合的实质。仅仅就是将MyBatis正和到Sprig。因为SpringMVC原本就是Spring
的一部分，不用我们专门整合。

SSM整合的实现方法可分为两种：基于XML配置文件、基于注解方式。

SpringMVC : 视图层，界面层，负责接收请求，显示处理结果。
Spring : 业务层，管理Service，dao，工具类等
MyBatis : 持久层。访问数据库

用户发起请求-->SpringMVC接收-->Spring中的Service对象-->MyBatis处理数据

SSM也叫做SSI(IBatis也就是MyBatis的前身)

1. 第一个容器SpringMVC容器，管理Controller控制器对象的。
2. 第二个容器Spring容器，管理Service，Dao，工具类对象的。
我们要做的把使用的对象交给合适的容器创建，管理。把Controller还有web开发的相关对象
交给SprigMVC容器，这些web用的对象写在SpringMVC配置文件中。

我们把Service、Dao对象放到Spring配置文件中，让Spring管理这些对象。

SpringMVC容器和Spring容器是有关系的，关系已经定好了。

SpringMVC容器是Spring容器的子容器，类似Java中的继承。类似与父子关系。
子类可以访问父类的信息。在子容器中的Controller可以访问父类中的Service对象，
就可以实现Controller使用Service对象。

我们不需要自己实现这个关系，这个关系是框架帮我们实现好的，我们直接调用Service即可。
我们只需要将对象放到对应的容器中即可。

实现步骤：
    0. 使用School的mysql数据库，使用Student表
    1. 新建Maven项目
    2. pom.xml中加入依赖
        Springmvc、Spring、MyBatis三个框架的依赖，jackson依赖，mysql驱动、druid连接池
        jsp、servlet依赖、lombok依赖(简化Get、Set、无参构造等，以及异常处理)

    3. 写web.xml
        1) 注册DispatcherServlet : 目的 1) 创建SpringMVC容器对象，才能创建Controller类对象。
                                       2) 创建的是Servlet，才能接收用户的请求。
        2) 注册spring监听器：ContextLoaderListener,目的：创建Spring容器对象，才可以创建Service、Dao对象等。

        3) 注册字符集过滤器，解决post乱码问题。

    4. 创建包，controller、service包、dao包，实体类包

    5. 写SpringMVC、Spring、MyBatis的配置文件
        1) SpringMVC配置文件
        2) Spring配置文件
        3) MyBatis主配置文件
        4) 数据库的属性配置文件

    6. 写代码，dao接口和mapper文件、service和实现类 controller，实体类。

    7. 写jsp页面。