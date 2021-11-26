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



## 第三章 SSM整合开发

- SSM 编程，即 SpringMVC + Spring + MyBatis 整合，是当前最为流行的 JavaEE 开发技术架 构。其实 SSM 整合的实质，仅仅就是将 MyBatis整合入 Spring。因为 SpringMVC原本就是 Spring 的一部分，不用专门整合。
- **SSM 整合的实现方式可分为两种：基于 XML 配置方式，基于注解方式。**

### 3.1 搭建SSM开发环境

#### 3.1.1 添加pom.xml依赖

```xml
<!--servlet依赖-->
<dependency>
<groupId>javax.servlet</groupId>
<artifactId>javax.servlet-api</artifactId>
<version>3.1.0</version>
<scope>provided</scope>
</dependency>
<!-- jsp依赖 -->
<dependency> 
 <groupId>javax.servlet.jsp</groupId> 
 <artifactId>jsp-api</artifactId> 
 <version>2.2.1-b03</version> 
 <scope>provided</scope>
</dependency>
<dependency>
<!--springMVC依赖-->
<groupId>org.springframework</groupId>
<artifactId>spring-webmvc</artifactId>
<version>5.2.5.RELEASE</version>
</dependency>
<!--SpringM处理事务的依赖-->
<dependency>
<groupId>org.springframework</groupId>
<artifactId>spring-tx</artifactId>
<version>5.2.5.RELEASE</version>
</dependency>
<!--spring处理数据库连接的依赖-->
<dependency>
<groupId>org.springframework</groupId>
<artifactId>spring-jdbc</artifactId>
<version>5.2.5.RELEASE</version>
</dependency>
<!--将数据转换为json格式的依赖-->
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
<!--spring整合mybatis的依赖-->
<dependency>
<groupId>org.mybatis</groupId>
<artifactId>mybatis-spring</artifactId>
<version>1.3.1</version>
</dependency>
<!--mybatis的依赖-->
<dependency>
<groupId>org.mybatis</groupId>
<artifactId>mybatis</artifactId>
<version>3.5.1</version>
</dependency>
<!--数据库连接驱动-->
<dependency>
<groupId>mysql</groupId>
<artifactId>mysql-connector-java</artifactId>
<version>5.1.9</version>
</dependency>
<!--数据路连接池依赖-->
<dependency>
<groupId>com.alibaba</groupId>
<artifactId>druid</artifactId>
<version>1.1.12</version>
</dependency>
```

#### 3.1.2 配置web.xml

**（1）注册ContextLoaderListenter监听器**

![image-20211122203151417](https://gitee.com/YunboCheng/imageBad/raw/master/image/image-20211122203151417.png)

- 注册 ServletContext 监听器的实现类 ContextLoaderListener，用于创建 Spring 容器及将创 建好的 Spring 容器对象放入到 ServletContext 的作用域中。

**(2) 注册字符集处理器**

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211122203331.png)

- 注册字符集过滤器，用于解决请求参数中携带中文时产生乱码问题。

**（3）配置中央调度器**

- 配置中央调度器时需要注意，SpringMVC的配置文件名与其它 Spring配置文件名不相同。 这样做的目的是 Spring 容器创建管理 Spring 配置文件中的 bean， SpringMVC 容器中负责视 图层 bean 的初始。

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211122203506.png)

### 3.2 SSM整合注解开发

项目：ssm

需求：完成学生注册和信息浏览。

#### 3.2.1 建表 Student

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211122223456.png)

#### 3.2.2 新建 Web 工程

工程名称 ssm

#### 3.2.3 maven 依赖

- **添加项目搭建的时候哪些依赖即可。**

#### 3.2.4  定义包，组织程序的结构

![image-20211122220732844](https://gitee.com/YunboCheng/imageBad/raw/master/image/image-20211122220732844.png)

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211122220742.png)

#### 3.2.5  编写配置文件

**(1) Jdbc 属性配置文件 jdbc.properties**

```properties
jdbc.url=jdbc:mysql://localhost:3306/school
jdbc.username=root
jdbc.password=567cybtfboys
```

**(2) 配置web.xml**

- 配置中央调度器。将SpringMVC配置文件中的对象(Controller)全部加载到SpringMVC容器中。
- 配置监听器。将Spring配置文件中的全部对象(Dao、Service)全部加载到Spring容器中。
- 配置过滤器。解决post请求中的中文乱码问题。
- web.xml可以加载出Spring.xml以及SpringMVC.xml文件。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

  <welcome-file-list>
    <welcome-file>index.jsp</welcome-file>
  </welcome-file-list>

  <!--
    注册中央调度器，创建SpringMVC容器对象
    将SpringMVC配置文件中的所有被@Controller注解的类为Servlet对象
  -->
  <servlet>
    <servlet-name>myWeb</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:conf/SpringMVC.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>myWeb</servlet-name>
    <url-pattern>*.do</url-pattern>
  </servlet-mapping>

  <!--
    注册监听器，创建Spring容器对象，创建Spring.xml的配置文件中的所有对象。
    监听器加载spring主配置文件的路径是: /WEB-INF/spring.xml(Spring主配置文件)
    我们此时修改为自己的保存spring.xml的路径。
  -->
  <context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:conf/Spring.xml</param-value>
  </context-param>
  <listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>

  <!--注册声明过滤器，解决中文乱码问题-->
  <filter>
  <!--名字是自定义的-->
  <filter-name>characterEncodingFilter</filter-name>
  <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
  <!--设置项目中使用字符编码-->
  <init-param>
    <param-name>encoding</param-name>
    <param-value>utf-8</param-value>
  </init-param>
  <!--强制请求对象(HttpServletRequest)使用encoding编码的值，也就是上边声明的这个 utf-8-->
  <init-param>
    <param-name>forceRequestEncoding</param-name>
    <param-value>true</param-value>
  </init-param>
  <!--设置应答对象(HttpServletResponse)使用encoding编码的值，也就是上边声明的这个 utf-8-->
  <init-param>
    <param-name>forceResponseEncoding</param-name>
    <param-value>true</param-value>
  </init-param>
  </filter>
  <filter-mapping>
  <filter-name>characterEncodingFilter</filter-name>
  <!--
    /* : 表示强制所有的请求先通过过滤器处理。
  -->
  <url-pattern>/*</url-pattern>
  </filter-mapping>

</web-app>

```

**(3) Spring配置文件 applicationContext.xml**

- Spring主配置文件组件扫描器扫描的是Dao、Service的对象。
- 配置连接数据库的信息。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

    <!--Spring配置文件：声明service,dao,工具类等对象-->

    <!--声明数据库配置文件的位置-->
    <context:property-placeholder location="classpath:conf/jdbc.properties" />

    <!--声明数据源，连接数据库-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init"
          destroy-method="close">
        <!--
            使用value还是ref，得看name属性对应的setUrl()、setUsername()方法的形参中类型
            如果是简单数据类型(基本数据类型、包装类、String)就使用value
            如果是引用数据类型(Object object)、List、Map等需要使用ref
        -->
        <property name="url" value="${jdbc.url}?serviceTimezone=GMT%2B&amp;charsetEncoding=utf-8&amp;useUnicode=true"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>

     <!--使用SqlSessionFactoryBean创建SqlSessionFactory对象-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <property name="configLocation" value="classpath:/conf/MyBatis.xml"/>
    </bean>

    <!--声明MyBatis的扫描器，创建Dao对象，也就是dao接口的实现类，使用的是JDK的动态代理创建-->
    <!--创建dao的动态代理对象-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
        <!--这个也是路径信息，是包的全限定名称-->
        <property name="basePackage" value="com.yunbocheng.dao"/>
    </bean>

    <!--
        因为StudentDao没有对应的实现类，需要使用JDK动态代理来获取接口的实现类。
        而service接口存在实现类，直接是实现类上使用@Service声明这个创建的是一个Service对象。
    -->
    <!--声明service的注解的@Service所在包的位置，完成service的创建-->
    <context:component-scan base-package="com.yunbocheng.service"/>

    <!--事务配置：注解的配置，aspectJ的配置-->


</beans>
```

**(3) SpringMVC配置文件**

- SpringMVC主配置文件组件扫描器的是Controller(Servlet)对象。

- 配置视图解析器，简化跳转页面时的路径。
- 注册注解驱动，解决ajax请求转为json格式数据以及解决静态资源访问你的冲突(只有)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc https://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!--SpringMVC配置文件，声明controller和其他web相关的对象-->

    <!--声明组件扫描器，使用动态代理的方式创建Servlet的动态代理对象-->
    <context:component-scan base-package="com.yunbocheng.controller" />

    <!--配置视图解析器-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <!--这里使用value属性，因为使用的是set注入的方式进行赋值-->
        <property name="prefix" value="/WEB-INF/jsp/" />
        <property name="suffix" value=".jsp" />
    </bean>

    <!--注解驱动，因为处理ajax请求(转换格式)以及静态资源(解决冲突)都需要用到注解驱动-->
    <mvc:annotation-driven/>
</beans>
```

**(4) 配置Mybatis.xml主配置文件**

- 配置输出日志信息设置。
- 设置参与请求数据道的类型的别名。
- 指定mapper映射文件的位置。

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--设置输出日志-->
        <settings>
            <setting name="logImpl" value="STDOUT_LOGGING" />
        </settings>

    <!--设置别名，使用报名的方式，包中的类名就是别名-->
      <typeAliases>
         <package name="com.yunbocheng.entity"/>
     </typeAliases>

    <mappers>
        <!--
            使用package ：指定dao接口包的位置，表示将包下面的sql映射文件找到
            name: Dao接口的包名
            使用package指定映射文件的要求：
                1. sql映射文件(StudentDao.xml)和接口名一致
                2. sql映射文件和Dao接口在同一目录
        -->
        <package name="com.yunbocheng.dao"/>
    </mappers>
</configuration>
```

#### 3.2.6 实体类

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    // 属性名和数据库的列名一致，如果不一样的话需要使用resultMap进行修改
    private String num;

    private String province;

    private String city;

    private String name;

    private Integer age;

    private Double score;

    private String room;
}
```

#### 3.2.7 Dao接口和sql映射文件。

注意：

必须保证StudentDao接口与StudentDao.xml文件处于同一目录下。

必须保证接口的名称与配置文件的名称一致。

- Dao接口的方法

```java
public interface StudentDao {

    // 录入学生信息
    int insertStudent(Student student);

    // 查询全部的学生信息
    List<Student> selectStudent();
}
```

- sql映射文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.yunbocheng.dao.StudentDao" >

    <resultMap id="student" type="com.yunbocheng.entity.Student">
        <id column="num" property="num"/>
        <result column="province" property="province"/>
        <result column="city" property="city"/>
        <result column="name" property="name"/>
        <result column="age" property="age"/>
        <result column="score" property="score"/>
        <result column="room" property="room"/>
    </resultMap>

    <select id="selectStudent" resultMap="student">
        /*在实际开发中，尽量不写*号，写列名*/
        select num,province,city,name,age,score,room from tab_student order by num;
    </select>

    <insert id="insertStudent" parameterType="Student">
        insert into tab_student(num,province,city,name,age,score,room) values (#{num},#{province},#{city},#{name},#{age},#{score},#{room})
    </insert>
</mapper>

```

#### 3.2.8 Service接口与实现类

- Service接口

```java
public interface StudentService {

    // 录入学生信息
    int addStudent(Student student);

    // 查询全部学生信息
    List<Student> findStudent();
}
```

- Service接口的实现类（实现StudentService接口）

```java
/*声明Service对象*/
@Service
public class StudentServiceImpl implements StudentService {

    /*
        以前我们使用的是手动注入，在spring主配置文件中使用set手动注入。
        这里使用 引用类型的自动注入@Autowired,@Resource
        spring只能给引用类型的数据自动赋值，不可以给引用类型的数据赋值。
        @Resource默认使用的是byName，不成功的时候使用ByType
    */
    /*
        dao接口的首字母小写。这种使用的就是@resource的byName的方式
        @Resource(name = "studentDao")
    */
    /*
    * 这种使用的就是@Resource的byType方式
    * 使用自动注入的方式创建一个StudentDao对象
    * */
    @Resource
    private StudentDao studentDao;

    @Override
    public int addStudent(Student student) {
        return studentDao.insertStudent(student);
    }

    @Override
    public List<Student> findStudent() {
        return studentDao.selectStudent();
    }
}
```

#### 3.2.9 处理器定义(也就是Servlet对象)

```java
@Controller
@RequestMapping(value = "/student")
public class StudentController {

    // 使用自动注入的方式创建一个Service对象
    @Resource
    private StudentService service;


    // 注册学生
    @RequestMapping(value = "/addStudent.do")
    /*在形参的部分接收前端传递过来的参数，此时使用student对象接收参数*/
    public ModelAndView addStudent(Student student){
        ModelAndView mv = new ModelAndView();
        String str = "学生 【\" + student.getName() + \"】 注册失败！";
        int nums = service.addStudent(student);
        System.out.println(nums);
        if (nums == 1){
            // 注册成功
            str = "学生 【" + student.getName() + "】 注册成功！";
        }
        // 添加数据
        mv.addObject("result",str);
        // 指定结果页面
        mv.setViewName("show");
        return mv;
    }
}
```

#### 3.2.10 定义一个视图-->首页文件-->index.jsp

- 可以使用以下的方式指定当前项目的根路径。

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211122224021.png)

- 我们这里使用 application 域存储根路径。

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% application.setAttribute("path",request.getContextPath());%>
<html>
<head>
    <title>SSM整合开发</title>
    <style>
        .myStudent{
            font-size: 30px;
            color: red;
        }
    </style>
</head>
<body>
    <div><img src="images/spring事务处理.png" alt="图片加载失败！"></div>
    <div align="center" class="myStudent"><a href="addStudent.jsp">注册学生信息</a></div>
    <div align="center" class="myStudent"><a href="selectStudent.jsp">查询学生信息</a></div>
</body>
</html>
```

#### 3.2.11 注册学生信息界面-->addStudent.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% application.setAttribute("path",request.getContextPath());%>
<html>
<head>
    <title>添加学生信息</title>
    <style>
        .myStudent{
            font-size: 30px;
            color: red;
        }
    </style>
</head>
<body>
<div><img src="images/spring事务处理.png" alt="图片加载失败！"></div>
<div align="center" class="myStudent">注册学生信息</div>
<form action="${path }/student/addStudent.do" method="post">
    <table align="center" border="2px">
        <tr>
            <th>
                学号：
            </th>
            <td>
                <%--需要保证请求参数名和Student类中的属性名一致--%>
                <input type="text" name="num" />
            </td>
        </tr>
        <tr>
            <th>
                省份：
            </th>
            <td>
                <%--需要保证请求参数名和Student类中的属性名一致--%>
                <input type="text" name="province" />
            </td>
        </tr>
        <tr>
            <th>
                城市：
            </th>
            <td>
                <%--需要保证请求参数名和Student类中的属性名一致--%>
                <input type="text" name="city" />
            </td>
        </tr>
        <tr>
            <th>
                姓名：
            </th>
            <td>
                <%--需要保证请求参数名和Student类中的属性名一致--%>
                <input type="text" name="name" />
            </td>
        </tr>
        <tr>
            <th>
                年龄：
            </th>
            <td>
                <%--需要保证请求参数名和Student类中的属性名一致--%>
                <input type="text" name="age" />
            </td>
        </tr>
        <tr>
            <th>
                分数：
            </th>
            <td>
                <%--需要保证请求参数名和Student类中的属性名一致--%>
                <input type="text" name="score" />
            </td>
        </tr>
        <tr>
            <th>
                教室：
            </th>
            <td>
                <%--需要保证请求参数名和Student类中的属性名一致--%>
                <input type="text" name="room" />
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="录入" />
            </td>
            <td>
                <input type="reset" value="重置" />
            </td>
        </tr>
    </table>
</form>
</body>
</html>

```

#### 3.2.12 显示处理结果界面-->show.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>展示数据</title>
    <style>
        div{
            color: red;
            font-size: 30px;
        }
    </style>
</head>
<body>
    <div align="center" >${result}</div>
</body>
</html>
```

**通过以上的配置即可完成一个SSM使用注解开发的基本配置。**



## 第四章  SpringMVC 核心技术

### 4.1 请求重定向和转发

- 当处理器对请求处理完毕后，向其它资源进行跳转时，有两种跳转方式：请求转发与重 定向。而根据所要跳转的资源类型，又可分为两类：跳转到页面与跳转到其它处理器。
-  注意，对于请求转发的页面，可以是WEB-INF中页面；而重定向的页面，是不能为WEB-INF 中页的。因为重定向相当于用户再次发出一次请求，而用户是不能直接访问 WEB-INF 中资 源的

![image-20211124223840114](https://gitee.com/YunboCheng/imageBad/raw/master/image/image-20211124223840114.png)

SpringMVC 框架把原来 Servlet 中的请求转发和重定向操作进行了封装。现在可以使用简 单的方式实现转发和重定向。

- forward:表示转发，实现 request.getRequestDispatcher("xx.jsp").forward()
- redirect:表示重定向，实现 response.sendRedirect("xxx.jsp"

#### 4.4.1 请求转发

- 处理器方法返回 ModelAndView 时，需在 setViewName()指定的视图前添加 forward:，且 此时的视图不再与视图解析器一同工作，这样可以在配置了解析器时指定不同位置的视图。 视图页面必须写出相对于项目根的路径。forward 操作不需要视图解析器。 
- 处理器方法返回 String,在视图路径前面加入 forward: 视图完整路径。

```java
    @RequestMapping(value = "/some.do")
    public ModelAndView doSome(String name,int age){
        ModelAndView mv = new ModelAndView();
        mv.addObject("myName",name);
        mv.addObject("myAge",age);
        /*使用forward请求转发的方式*/
        /*这个是请求转发的方式，可以请求到WEB-INF下的页面*/
        mv.setViewName("forward:/WEB-INF/view/show.jsp");
        return mv;
    }
```

#### 4.1.2 请求重定向

- 在处理器方法返回的视图字符串的前面添加 redirect:，则可实现重定向跳转。

```java
    @RequestMapping(value = "/some2.do")
    public ModelAndView doSome2(String name,int age){
        ModelAndView mv = new ModelAndView();
        /*此时的数据放到request作用域中*/
        mv.addObject("myName",name);
        mv.addObject("myAge",age);
        /*使用redirect请求转发的方式*/
        /*这个是重回定向的方式，请求不到WEB-INF下的网页*/
        mv.setViewName("redirect:/show.jsp");
        // http://localhost:8080/myWeb/show.jsp?myName=lisi&myAge=20
        return mv;
    }
```

### 4.2 异常处理

- **SpringMVC 框架处理异常的常用方式：使用@ExceptionHandler 注解处理异常。**

#### 4.2.1 @ExceptionHandler 注解

- 使用注解@ExceptionHandler 可以将一个方法指定为异常处理方法。该注解只有一个可 选属性 value，为一个 Class数组，用于指定该注解的方法所要处理的异常类，即所要匹 配的异常。 

- 而被注解的方法，其返回值可以是 ModelAndView、String，或 void，方法名随意，方法 参数可以是 Exception 及其子类对象、HttpServletRequest、HttpServletResponse 等。系统会 自动为这些方法参数赋值。 
- 对于异常处理注解的用法，也可以直接将异常处理方法注解于 Controller 之中。

**（1） 自定义异常类**

**定义三个异常类：NameException、AgeException、MyUserException。其中 MyUserException 是另外两个异常的父类。**

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211124224206.png)

```java
public class MyUserException extends Exception{

    /*继承父类的有参构造个无参构造方法*/

    public MyUserException() {
        super();
    }

    public MyUserException(String message) {
        super(message);
    }
}
```

```java
/*
* 当输入的年龄存在异常的时候抛出异常
* */
public class AgeException extends MyUserException{
    public AgeException() {
        super();
    }

    public AgeException(String message) {
        super(message);
    }
}
```

```java
/*
* 当用户的姓名存在异常的时候抛出的异常，抛出NameException
* */
public class NameException extends MyUserException{
    public NameException() {
        super();
    }

    public NameException(String message) {
        super(message);
    }
}

```

**（2） 修改 Controller 抛出异常**

```java
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

```

**（3） 定义异常响应页面**

**定义三个异常响应页面。**

![image-20211124224421872](https://gitee.com/YunboCheng/imageBad/raw/master/image/image-20211124224421872.png)

- 不过，一般不这样使用。而是将异常处理方法专门定义在一个类中，作为全局的异常处 理类。
- 需要使用注解@ControllerAdvice，字面理解就是“控制器增强”，是给控制器对象增强 功能的。使用@ControllerAdvice 修饰的类中可以使用@ExceptionHandler
- 当使用@RequestMapping 注解修饰的方法抛出异常时，会执行@ControllerAdvice 修饰的 类中的异常处理方法。
- @ControllerAdvice 是使用@Component 注解修饰的，可以 扫描到@ControllerAdvice 所在的类路径(包名)，创建对象。

**（4） 定义全局异常处理类**

```java
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

```

**（5） 定义 Spring 配置文件**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc https://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!--SpringMVC配置文件，声明controller和其他web相关的对象-->

    <!--声明组件扫描器，使用动态代理的方式创建Servlet的动态代理对象-->
    <context:component-scan base-package="com.yunbocheng.controller" />

    <!--配置视图解析器-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <!--这里使用value属性，因为使用的是set注入的方式进行赋值-->
        <property name="prefix" value="/WEB-INF/view/" />
        <property name="suffix" value=".jsp" />
    </bean>

    <!--注解驱动，因为处理ajax请求(转换格式)以及静态资源(解决冲突)都需要用到注解驱动-->
    <mvc:annotation-driven/>

    <!--处理异常的组件扫描器，指定处理异常类所在的包-->
    <context:component-scan base-package="com.yunbocheng.handel"/>
</beans>
```

### 4.3 拦截器

- SpringMVC 中的 Interceptor 拦截器是非常重要和相当有用的，它的主要作用是拦截指定的用户请求，并进行相应的预处理与后处理。其拦截的时间点在“处理器映射器根据用户提 交的请求映射出了所要执行的处理器类，并且也找到了要执行该处理器类的处理器适配器， 在处理器适配器执行处理器之前”。当然，在处理器映射器映射出所要执行的处理器类时， 已经将拦截器与处理器组合为了一个处理器执行链，并返回给了中央调度器。

#### 4.3.1  拦截器的执行

**自定义拦截器**

```java
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
```

**自定义拦截器，需要实现 HandlerInterceptor 接口。而该接口中含有三个方法：**

➢ preHandle(request,response, Object handler)： 该方法在处理器方法执行之前执行。其返回值为 boolean，若为 true，则紧接着会执行处理器方 法，且会将 afterCompletion()方法放入到一个专门的方法栈中等待执行。 

➢ postHandle(request,response, Object handler,modelAndView)： 该方法在处理器方法执行之后执行。处理器方法若最终未被执行，则该方法不会执行。 由于该方法是在处理器方法执行完后执行，且该方法参数中包含 ModelAndView，所以该方法可以修 改处理器方法的处理结果数据，且可以修改跳转方向。

 ➢ afterCompletion(request,response, Object handler, Exception ex)： 当 preHandle()方法返回 true 时，会将该方法放到专门的方法栈中，等到对请求进行响应的所有 工作完成之后才执行该方法。即该方法是在中央调度器渲染（数据填充）了响应页面之后执行的，此 时对 ModelAndView 再操作也对响应无济于事。

**afterCompletion 最后执行的方法，清除资源，例如在 Controller 方法中加入数据**

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211124224919.png)

**拦截器中方法与处理器方法的执行顺序如下图：**

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211124224935.png)

**换一种表现方式，也可以这样理解：**

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211124224949.png)

**（1） 注册拦截器**

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211124225100.png)

- 用于指定当前所注册的拦截器可以拦截的请求路径，而/**表示拦截所 有请求。

**（2） 修改 index 页面**

![image-20211124225203409](https://gitee.com/YunboCheng/imageBad/raw/master/image/image-20211124225203409.png)

**（3） 修改处理器**

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211124225238.png)

**（4） 修改 show 页面**

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211124225251.png)

**（5） 控制台输出结果**

![](https://gitee.com/YunboCheng/imageBad/raw/master/image/20211124225305.png)

