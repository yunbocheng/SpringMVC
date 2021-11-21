这个项目研究的是：研究中央调度器的url-pattern设置为 "/"

发起的请求是由哪些服务器处理的

index.jsp是tomcat处理的
js是tomcat处理的
p1.png是由tomcat处理的
html是由tomcat处理的
以上都是静态资源文件。

请求属于动态资源文件
some.do 是由SpringMVC框架中的DispatcherServlet处理的

tomcat本身是可以处理静态资源文件的。jsp、js、html、图片等都属于静态资源。

以下这段代码是tomcat的config中自带的配置信息，是默认的。这段代码有两个作用：
    1. 可以处理静态资源界面的资源
    2. 处理为映射到其他Servlet的请求。http://localhost:8080/myWeb/c
       没有任何一个Servlet来处理这个请求。此时的这个请求就由tomcat处理。
<servlet>
<servlet-name>default</servlet-name>
<servlet-class>org.apache.catalina.servlets.DefaultServlet</servlet-class>
<init-param>
<param-name>debug</param-name>
<param-value>0</param-value>
</init-param>
<init-param>
<param-name>listings</param-name>
<param-value>false</param-value>
</init-param>
<load-on-startup>1</load-on-startup>
</servlet>

此时的 url-pattern 使用的是斜杠 /  表示静态资源和未映射的请求都使用这个default处理
<!-- The mapping for the default servlet -->
<servlet-mapping>
<servlet-name>default</servlet-name>
<url-pattern>/</url-pattern>
</servlet-mapping>

<!-- The mappings for the JSP servlet -->
<servlet-mapping>
<servlet-name>jsp</servlet-name>
<url-pattern>*.jsp</url-pattern>
<url-pattern>*.jspx</url-pattern>
</servlet-mapping>