# SpringMVC

## 第一章 SpringMVC概述

### 1.1 SpringMVC概念

- SpringMVC也叫Spring Web MVC。是Spring框架的一部分，是在Spring3.0之后发布的。其实就是Spring框架的一个模块，所以可以也是使用Spring的IoC技术实现对象的创建和赋值，使用AOP的切面技术为方法添加功能。
- SpringMVC基于spring的一个框架， 实际上就是spring的一个模块， 专门是做web开发的。其实就是一个Servlet的升级版。
-   web开发底层是servlet ， SpringMVC框架是在servlet基础上面加入一些功能，让你做web开发方便。
- SpringMVC就是一个Spring。 Spring是容器，IoC能够管理对象，使用<bean>, @Component, @Repository, @Service, @Controller等标签实现对象的创建。SpringMVC能够创建对象， 放入到容器中（SpringMVC容器）， springmvc容器中放的是控制器对象。

**注意：使用@Collection注解创建的对象其实就是一个普通的类，并不是一个Servlet类，因为真正好的Servlet类需要继承HttpServlet类。SpringMVC只是赋予了这个普通类的一些额外的功能。**

- web开发底层使用的一定是Servlet对象， springmvc中有一个对象是Servlet ： **DispatherServlet(中央调度器)**

**请求原理：**

**DispatherServlet: 负责接收用户的所有请求， 用户把请求给了DispatherServlet， 之后DispatherServlet把请求转发给我们的Controller对象， 最后是Controller对象处理请求。中央调度器就相当于一个中转站，用于接收和发放用户的请求。**

**实现步骤：**

index.jsp --> DispatherServlet(Spring提供的一个Servlet对象) --> 转发、分配给Collection对象(即使用@Controller注解创建的对象)

![image-20211118102329984](https://gitee.com/YunboCheng/imageBad/raw/master/image/image-20211118102329984.png)



### 1.2 SpringMVC优点

- **基于MVC结构：** 基于 MVC 架构，功能分工明确。解耦合。
- **容易理解，上手快；使用简单：**就可以开发一个注解的 SpringMVC 项目，SpringMVC 也是轻量级的，jar 很小。不依赖的 特定的接口和类。
- **作为Spring框架一部分，能够使用 Spring 的 IoC 和 Aop 方便整合。Strtus,MyBatis,Hiberate,JPA 等其他框架。**
- **SpringMVC 强化注解的使用，在控制器，Service，Dao 都可以使用注解。方便灵活：**使用@Controller 创建处理器对象,@Service 创建业务对象，@Autowired 或者@Resource 在控制器类中注入Service, Service类中注入Dao。

### 1.3 第一个注解SpringMVC程序

- 所谓 SpringMVC 的注解式开发是指：在代码中通过对类与方法的注解，便可完成处理器 在 springmvc容器的注册。**注解式开发是重点**。

**见项目Hello-SpringMVC**

**springmvc请求的处理流程**

 1）发起some.do
 2）tomcat(web.xml--url-pattern知道 *.do的请求给DispatcherServlet)
 3）DispatcherServlet（根据springmvc.xml配置知道 some.do---doSome()）
 4）DispatcherServlet把some.do转发个MyController.doSome()方法
 5）框架执行doSome（）把得到ModelAndView进行处理， 转发到show.jsp

**上面的过程简化的方式**
  some.do---DispatcherServlet---MyController

**核心原理**

![image-20211118163958761](https://gitee.com/YunboCheng/imageBad/raw/master/image/image-20211118163958761.png)

**SpringMVC执行过程的源码分析**

1. tomcat启动，创建容器的过程通过load-on-start标签指定的1，创建DisaptcherServlet对象， 
   DisaptcherServlet它的父类是继承HttpServlet的， 它是一个serlvet， 在被创建时，会执行init（）方法。在init（）方法中。

   ```java
    //创建容器，读取配置文件
    WebApplicationContext ctx = new ClassPathXmlApplicationContext("springmvc.xml");
    //把容器对象放入到ServletContext中
    getServletContext().setAttribute(key, ctx);
   ```

  上面创建容器作用： 创建@controller注解所在的类的对象， 创建MyController对象，这个对象放入到 springmvc的容器中， 容器是map ，类似map.put("myController",MyController对象)

2. 执行servlet的service()

   ```java
   protected void doService(HttpServletRequest request, HttpServletResponse response);
   protected void service(HttpServletRequest request, HttpServletResponse response);
   ```


   ```java
     DispatcherServlet.doDispatch(request, response){
   
         调用MyController的.doSome()方法
     }
   ```



## 第二章 SpringMVC注解式开发(重点)

- 所谓 SpringMVC 的注解式开发是指，在代码中通过对类与方法的注解，便可完成处理器 在 springmvc 容器的注册。注解式开发是重点。

我们使用一个项目来举例子：primary-annotation

这个项目所要完成的功能：用户提交一个请求，服务端处理器在接收到这个请求后，给出一条欢迎信息， 在响应页面中显示该信息。

**第一步：新建一个Maven项目**

![image-20211119123118975](https://gitee.com/YunboCheng/imageBad/raw/master/image/image-20211119123118975.png)

**第二步：pom.xml**

- 在创建好的web项目后，加入Servlet依赖，SpringMVC依赖

```xml
<dependency>
<groupId>javax.servlet</groupId>
<artifactId>javax.servlet-api</artifactId>
<version>3.1.0</version>
<scope>provided</scope>
</dependency>
<dependency>
<groupId>org.springframework</groupId>
<artifactId>spring-webmvc</artifactId>
<version>5.2.5.RELEASE</version>
</dependency>
<!--spring-webmvc中包含spring-web依赖-->
```

**第三步：注册中央调度器**

![image-20211119123449104](https://gitee.com/YunboCheng/imageBad/raw/master/image/image-20211119123449104.png)

**（1） 全限定性类名**

- 该中央调度器为一个 Servlet，名称为 DispatcherServlet。中央调度器的全限定性类名在 导入的 Jar 文件 spring-webmvc-5.2.5.RELEASE.jar 的第一个包 org.springframework.web.servlet 下可找到。

**（2） < load-on-startup/>**

- 在中添加的作用是，标记是否在Web服务器（这里是Tomcat） 启动时会创建这个 Servlet 实例，即是否在 Web 服务器启动时调用执行该 Servlet 的init()方 法，而不是在真正访问时才创建。**它的值必须是一个整数。**

**Servlet对象被加载创建的优先级**

➢ 当值大于等于 0 时，表示容器在启动时就加载并初始化这个 servlet，数值越小，该 Servlet 的优先级就越高，其被创建的也就越早； 

➢ 当值小于 0 或者没有指定时，则表示该 Servlet 在真正被使用时才会去创建。 

➢ 当值相同时，容器会自己选择创建顺序。

**（3） < url-pattern/>**

- **对于，可以写为 / ，建议写为*.do 的形式。**

 **（4）配置文件位置与名称**

- 注册完毕后，可直接在服务器上发布运行。此时，访问浏览器页面，控制台均会抛出 FileNotFoundException 异常。即默认要从项目根下的 WEB-INF 目录下找名称为 Servlet 名称 -servlet.xml 的配置文件。这里的“Servlet 名称”指的是注册中央调度器标签 中指定的 Servlet 的 name 值。本例配置文件名为 springmvc-servlet.xml。

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211119125553.png)

- 而一般情况下，配置文件是放在类路径下，即 resources 目录下。所以，在注册中央调 度器时，还需要为中央调度器设置查找 SpringMVC 配置文件路径，及文件名。

![image-20211119154250361](https://gitee.com/YunboCheng/imageBad/raw/master/image/image-20211119154250361.png)

- 打开 DispatcherServlet 的源码，其继承自 FrameworkServlet，而该类中有一个属性 contextConfigLocation，用于设置 SpringMVC 配置文件的路径及文件名。该初始化参数的属性 就来自于这里。

![image-20211119154321095](https://gitee.com/YunboCheng/imageBad/raw/master/image/image-20211119154321095.png)

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211119154459.png)

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211119154534.png)

**1.3.4 创建 SpringMVC 配置文件**

- 在工程的类路径即 src 目录下创建 SpringMVC 的配置文件 springmvc.xml。该文件名可以 任意命名。

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211119154655.png)

**1.3.5 创建处理器**

在类上与方法上添加相应注解即可。

- @Controller：表示当前类为处理器 
- @RequestMapping：表示当前方法为处理器方法。该方法要对 value 属性所指定的 URI 进行处理与响应。被注解的方法的方法名可以随意。

![image-20211119154753449](https://gitee.com/YunboCheng/imageBad/raw/master/image/image-20211119154753449.png)

- 若有多个请求路径均可匹配该处理器方法的执行，则@RequestMapping 的 value 属性中 可以写上一个数组。
- ModelAndView 类中的 addObject()方法用于向其 Model 中添加数据。Model 的底层为一 个 HashMap

**Model 中的数据存储在 request 作用域中，SringMVC 默认采用转发的方式跳转到视图， 本次请求结束，模型中的数据被销毁。**

**1.3.6 声明组件扫描器**

在 springmvc.xml 中注册组件扫描器

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211119154842.png)

**1.3.7 定义目标页面**

在 webapp 目录下新建一个子目录 jsp，在其中新建一个 jsp 页面 show.jsp。

![image-20211119154916725](https://gitee.com/YunboCheng/imageBad/raw/master/image/image-20211119154916725.png)

**1.3.8 修改视图解析器的注册**

- SpringMVC 框架为了避免对于请求资源路径与扩展名上的冗余，在视图解析器 InternalResouceViewResolver 中引入了请求的前辍与后辍。而 ModelAndView 中只需给出要跳 转页面的文件名即可，对于具体的文件路径与文件扩展名，视图解析器会自动完成拼接。

![image-20211119154947554](https://gitee.com/YunboCheng/imageBad/raw/master/image/image-20211119154947554.png)

把 show.jsp 文件放到 /WEB-INF/jsp/路径中

**1.3.9 修改处理器**

![image-20211119155008074](https://gitee.com/YunboCheng/imageBad/raw/master/image/image-20211119155008074.png)

**使用逻辑视图名称，show 是逻辑视图名称。**

**1.3.10 使用 SpringMVC 框架 web 请求处理顺序**

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211119155132.png)

**1.4 SpringMVC 的 MVC 组件**

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211119155206.png)

**1.5 SpringMVC 执行流程（理解）**

**1.5.1 流程图**

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211119155231.png)

**1.5.2 执行流程简单分析**

（1）浏览器提交请求到中央调度器 

（2）中央调度器直接将请求转给处理器映射器。 

（3）处理器映射器会根据请求，找到处理该请求的处理器，并将其封装为处理器执行链后 返回给中央调度器。 

（4）中央调度器根据处理器执行链中的处理器，找到能够执行该处理器的处理器适配器。 

（5）处理器适配器调用执行处理器。 

（6）处理器将处理结果及要跳转的视图封装到一个对象 ModelAndView 中，并将其返回给 处理器适配器。 

（7）处理器适配器直接将结果返回给中央调度器。

（8）中央调度器调用视图解析器，将 ModelAndView 中的视图名称封装为视图对象。

（9）视图解析器将封装了的视图对象返回给中央调度器 

（10）中央调度器调用视图对象，让其自己进行渲染，即进行数据填充，形成响应对象。 

（11）中央调度器响应浏览器。

## 第二章 SpringMVC注解开发（重点）

### 2.1 @RequestMapping 定义请求规则

#### 2.1.1 指定模块名称

- 通过@RequestMapping 注解可以定义处理器对于请求的映射规则。该注解可以注解在方 法上，也可以注解在类上，但意义是不同的。value 属性值常以“/”开始。
- @RequestMapping 的 value 属性用于定义所匹配请求的 URI。但对于注解在方法上与类 上，其 value 属性所指定的 URI，意义是不同的。
- 一个@Controller 所注解的类中，可以定义多个处理器方法。当然，不同的处理器方法 所匹配的 URI 是不同的。这些不同的 URI 被指定在注解于方法之上的@RequestMapping 的 value 属性中。但若这些请求具有相同的 URI 部分，则这些相同的 URI，可以被抽取到注解在 类之上的@RequestMapping 的 value 属性中。此时的这个 URI 表示模块的名称。URI 的请求 是相对于 Web 的根目录。
- 换个角度说，要访问处理器的指定方法，必须要在方法指定 URI 之前加上处理器类前定 义的模块名称

**项目：requestMapping-modelName。**

**第一步：修改处理器类 MyController。**

![image-20211119201215000](https://gitee.com/YunboCheng/imageBad/raw/master/image/image-20211119201215000.png)

**第二步：添加视图页面**

- 在/WEB-INF/jsp 目录下添加 some.jsp 与 other.jsp 页面，删除原 welcome.jsp 页面。在WEB-INF目录之下的界面是安全的，不可以在URL地址栏直接输入界面名称进行获取。

![image-20211119201538344](https://gitee.com/YunboCheng/imageBad/raw/master/image/image-20211119201538344.png)

**2.1.2 对请求提交方式的定义**

- 对于@RequestMapping，其有一个属性 method，用于对被注解方法所处理请求的提交 方式进行限制，即只有满足该 method 属性指定的提交方式的请求，才会执行该被注解方法。 
- Method 属性的取值为 RequestMethod 枚举常量。常用的为 RequestMethod.GET 与 RequestMethod.POST，分别表示提交方式的匹配规则为 GET 与 POST 提交。

![image-20211119201753990](https://gitee.com/YunboCheng/imageBad/raw/master/image/image-20211119201753990.png)

- 以上处理器方法只能处理 POST 方式提交的请求。客户端浏览器常用的请求方式，及其 提交方式有以下几种：

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211119201811.png)

- 也就是说，只要指定了处理器方法匹配的请求提交方式为 POST，则相当于指定了请求 发送的方式：要么使用表单请求，要么使用 AJAX 请求。其它请求方式被禁用。

**超链接的请求方式是GET，可以使用POST请求方式的只能是表单以及AJAX方式。**

- 当然，若不指定 method 属性，则无论是 GET 还是 POST 提交方式，均可匹配。即对于 请求的提交方式无要求。

**第一步：修改处理器类 MyController**

![image-20211119202009584](https://gitee.com/YunboCheng/imageBad/raw/master/image/image-20211119202009584.png)

**第二步：修改 index 页面**

![image-20211119202101832](https://gitee.com/YunboCheng/imageBad/raw/master/image/image-20211119202101832.png)

### 2.2 处理器方法的参数

- 处理器方法可以包含以下四类参数，这些参数会在系统调用时由系统自动赋值，即程序员可在方法内直接使用。

- HttpServletRequest 
- HttpServletResponse 
- HttpSession 
- 请求中所携带的请求参数

#### 2.2.1 逐个参数接收

**只要保证请求参数名与该请求处理方法的参数名相同即可。此时可以直接获取，如果不同的话，在后面存在处理方式。**

**第一步：修改 index 页面**

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211119202338.png)

**第二步：修改处理器类 MyController**

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211119202408.png)

**第三步：添加 show 页面**

**在/WEB-INF/jsp 下添加 show.jsp 页面。**

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211119202543.png)

#### 2.2.2 请求参数中文乱码问题

**只有POST请求存在中文乱码问题，GET请求不存在中文乱码问题。**

- 对于前面所接收的请求参数，若含有中文，则会出现中文乱码问题。Spring 对于请求参数中的中文乱码问题，给出了专门的字符集过滤器：spring-web-5.2.5.RELEASE.jar 的 org.springframework.web.filter 包下的 CharacterEncodingFilter 类。

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211119202626.png)

**（1） 解决方案**

- 在 web.xml 中注册字符集过滤器，即可解决 Spring 的请求参数的中文乱码问题。不过， 最好将该过滤器注册在其它过滤器之前。因为过滤器的执行是按照其注册顺序进行的。
- 这个过滤器可以我们自己创建，也可以使用框架中提供好的过滤器(CharacterEncodingFilter)

![image-20211119202744167](https://gitee.com/YunboCheng/imageBad/raw/master/image/image-20211119202744167.png)

**（2） 源码分析**

**字符集设置核心方法：**

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211119202936.png)

#### 2.2.3 校正请求参数名@RequestParam

- 所谓校正请求参数名，是指若请求 URL 所携带的参数名称与处理方法中指定的参数名 不相同时，则需在处理方法参数前，添加一个注解@RequestParam(“请求参数名”)，指定请 求 URL 所携带参数的名称。该注解是对处理器方法参数进行修饰的。value 属性指定请求参 数的名称。

**第一步：修改 index 页面**

将表单中的参数名称修改的与原来不一样。

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211119203044.png)

**第二步：修改处理器类 MyController**

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211119203113.png)

**required 属性：**

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211119203138.png)

### 2.2.4 对象参数接收

- 将处理器方法的参数定义为一个对象，只要保证请求参数名与这个对象的属性同名即可。

**第一步：定义实体类Student，这个类用于封装用户的请求参数值**

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211119203310.png)

注意：这个实体类需要存在无参构造方法以及set和get方法。

**第二步：修改处理器类 MyController**

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211119203407.png)

**第三步：修改show页面**

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211119203429.png)

## 2.3 处理器方法的返回值

**使用@Controller 注解的处理器的处理器方法，其返回值常用的有四种类型：**

- 第一种：ModelAndView 
- 第二种：String 
- 第三种：无返回值 void 
- 第四种：返回自定义类型对象

**根据不同的情况，使用不同的返回值。**

### 2.3.1 返回 ModelAndView

- 若处理器方法处理完后，需要跳转到其它资源，且又要在跳转的资源间传递数据，此时 处理器方法返回 ModelAndView 比较好。当然，若要返回 ModelAndView，则处理器方法中 需要定义 ModelAndView 对象。
- 在使用时，若该处理器方法只是进行跳转而不传递数据，或只是传递数据而并不向任何 资源跳转（如对页面的 Ajax 异步响应），此时若返回 ModelAndView，则将总是有一部分多 余：要么 Model 多余，要么 View 多余。即此时返回 ModelAndView 将不合适。

### 2.3.2 返回 String

- 处理器方法返回的字符串可以指定逻辑视图名，通过视图解析器解析可以将其转换为物理视图地址

**返回内部资源逻辑视图名**

- 若要跳转的资源为内部资源，则视图解析器可以使用 InternalResourceViewResolver 内部 资源视图解析器。此时处理器方法返回的字符串就是要跳转页面的文件名去掉文件扩展名后 的部分。这个字符串与视图解析器中的 prefix、suffix 相结合，即可形成要访问的 URI。

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211119203838.png)

**直接修改处理器类 MyController**

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211119203915.png)

**重点：也可以直接返回资源的物理视图名。不过，此时就不需要再在视图解析器中再配 置前辍与后辍了。**

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211119203959.png)

### 2.2.3 返回void（了解）

- **对于处理器方法返回 void 的应用场景，AJAX 响应。**
- 若处理器对请求处理后，无需跳转到其它任何资源，此时可以让处理器方法返回 void。例如，对于 AJAX 的异步请求的响应。

**第一步：maven加入jsckson依赖**

- 由于本项目中服务端向浏览器传回的是 JSON 数据，需要使用一个工具类将字符串包装 为 JSON 格式，所以需要导入 JSON 的依赖。

**使用pom.xml的时候需要注意，在< artifactId>的标签中不可以包含空格以及汉字**

```xml
<dependency>
<groupId>com.fasterxml.jackson.core</groupId>
<artifactId>jackson-core</artifactId>
<version>2.9.0</version>
</dependency>
<dependency>
<groupId>com.fasterxml.jackson.core</groupId>
<artifactId>jackson-databind</artifactId>
<version>2.9.0</version>
</dependency>
```

**第二步：引入 jQuery 库**

- 由于本项目要使用 jQuery 的 ajax()方法提交 AJAX 请求，所以项目中需要引入 jQuery 的 库。在 WebRoot 下新建一个 Folder（文件夹），命名为 js，并将 jquery-1.11.1.js 文件放入其 中。

![image-20211121224936449](https://gitee.com/YunboCheng/imageBad/raw/master/image/image-20211121224936449.png)

- 当然，该 jQuery 库文件，需要在使用 ajax()方法的 index 页面中引入。

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211121225005.png)

**第三步：定义 index 页面**

- index 页面由两部分内容构成：一个是，用于提交 AJAX 请求；一个是， 用于处理 AJAX 请求。

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211121225044.png)

**点击按钮发起请求**

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211121225101.png)

**第四步：定义对象 Student**

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211121225128.png)

**第五步：修改处理器类 MyController**

- 处理器对于 AJAX 请求中所提交的参数，可以使用逐个接收的方式，也可以以对象的方 式整体接收。只要保证 AJAX 请求参数与接收的对象类型属性同名。

**接收参数的方式：当处理器方法中的形参是一个对象的时候，需要保证形参名与Java对象中的属性名一致。当使用逐一接收的方式，需要保证请求参数名和形参名保持一致。**

以逐个方式接收参数：需要保证ajax的请求参数名和处理器方法的形参名一致。

![image-20211121230501765](https://gitee.com/YunboCheng/imageBad/raw/master/image/image-20211121230501765.png)

**第六步：删除视图页面**

- 由于是服务端直接向浏览器发回数据，所以也就无需视图页面了，所以需要删除 WEB-INF 中的 jsp 目录及其中的 show 页面。

### 2.3.4 返回对象Object

- 处理器方法也可以返回 Object 对象。这个 Object 可以是 Integer，String，自定义对象， Map，List 等。但返回的对象不是作为逻辑视图出现的，而是作为直接在页面显示的数据出 现的。
- **返回对象，需要使用@ResponseBody 注解，将转换后的 JSON 数据放入到响应体中。**

#### (1) 环境搭建

**第一步：添加jackson依赖**

- 由于返回 Object 数据，一般都是将数据转化为了 JSON 对象后传递给浏览器页面的。而 这个由 Object 转换为 JSON，是由 Jackson 工具完成的。所以需要导入 Jackson 的相关 Jar 包。

```xml
<dependency>
<groupId>com.fasterxml.jackson.core</groupId>
<artifactId>jackson-core</artifactId>
<version>2.9.0</version>
</dependency>
<dependency>
<groupId>com.fasterxml.jackson.core</groupId>
<artifactId>jackson-databind</artifactId>
<version>2.9.0</version>
</dependency>
```

**第二步：修改springmvc的主配置文件**

- 将 Object 数据转化为 JSON 数据，需要由消息转换器 HttpMessageConverter 完成。而转 换器的开启，需要由来完成。
- SpringMVC 使用消息转换器实现请求数据和对象，处理器方法返回对象和响应输出之间 的自动转换。
- 当 Spring 容器进行初始化过程中，在处创建注解驱动时，默认 创建了七个 HttpMessageConverter 对象。也就是说，我们注册，就 是为了让容器为我们创建 HttpMessageConverter 对象。

![image-20211121231021505](https://gitee.com/YunboCheng/imageBad/raw/master/image/image-20211121231021505.png)

**针对HttpMessageConverter接口的原理分析：**

- **HttpMessageConverter 接口 : HttpMessageConverter是 Spring3.0 新添加的一个接口， 负责将请求信息转换为一个对象（类型为 T），将对象（类型为 T）输出为响应信息**

**HttpMessageConverter接口定义的方法：**

- boolean canRead(Class clazz,MediaType mediaType): 指定转换器可以读取的对象类型，即 转 换 器 是 否 可 将 请 求 信 息 转 换 为 clazz 类 型 的 对 象 ， 同 时 指 定 支 持 MIME 类 型 (text/html,applaiction/json 等)
- boolean canWrite(Class clazz,MediaType mediaType):指定转换器是否可将 clazz 类型的对 象写到响应流中，响应流支持的媒体类型在 MediaType 中定义。 LIst getSupportMediaTypes()：该转换器支持的媒体类 型。
- T read(Class clazz,HttpInputMessage inputMessage)：将请求信息流转换为 T 类型 的对象。
- void write(T t,MediaType contnetType,HttpOutputMessgae outputMessage):将 T 类型的对象写 到响应流中，同时指定相应的媒体类型为 contentType
- 加入注解驱动后适配器类的 messageConverters 属性值

![image-20211121231302551](https://gitee.com/YunboCheng/imageBad/raw/master/image/image-20211121231302551.png)

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211121231312.png)

#### （2）返回自定义类型对象

- 返回自定义类型对象时，不能以对象的形式直接返回给客户端浏览器，而是将对象转换 为 JSON 格式的数据发送给浏览器的。
- 由于转换器底层使用了Jackson转换方式将对象转换为JSON数据，所以需要导入Jackson 的相关 Jar 包。

**第一步：定义数据类**

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211121231537.png)

**第二步：修改处理器 MyController**

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211121231603.png)

**第三步：修改 index 页面**

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211121231628.png)

#### （3） 返回 List 集合

- **第一步：修改处理器 MyController**

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211121231703.png)

**第二步：修改 index 页面**

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211121231735.png)

#### （4） 返回字符串对象

- 若要返回非中文字符串，将前面返回数值型数据的返回值直接修改为字符串即可。但若 返 回 的 字 符 串 中 带 有 中 文 字 符 ， 则 接 收 方 页 面 将 会 出 现 乱 码 。 此 时 需 要 使 用 @RequestMapping 的 produces 属性指定字符集。
- produces，产品，结果，即该属性用于设置输出结果类型。

**第一步：修改处理器**

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211121231820.png)

**第二步：修改页面**、

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211121231909.png)

