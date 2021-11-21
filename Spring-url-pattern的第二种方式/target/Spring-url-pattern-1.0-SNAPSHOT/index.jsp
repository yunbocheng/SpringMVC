<%--
  Created by IntelliJ IDEA.
  User: YunboCheng
  Date: 2021/11/21
  Time: 14:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% application.setAttribute("path",request.getContextPath());%>
<html>
<head>
    <title>使用斜杠"/",配置中央调度器的url-pattern</title>
    <script src="static/jQuery/jquery-3.6.0.min.js"></script>
</head>
<body>
<%--
    这里讲述一下绝对路径与相对路径的问题
       1. 直接书写路径名 returnResult 前边不可以加斜杠，
       2. /returnResult 会访问失败，此时没有这个项目的根路径

       3. /项目的根路径(tomcat中设置的名称)/returnResult
       4. 或者使用EL表达式
          使用application域 : 需要在jsp的头文件中配置 <% application.setAttribute("path",request.getContextPath());%>
                             将根目录存储到一个path中，然后使用EL表达式 ${path }/returnResult

          使用pageContext域：直接使用EL表达式进行拼接 ${pageContext.request.contextPath}/returnResult

--%>
<form action="${path }/returnResult" method="post">
    <input type="text" name="name" />
    <input type="text" name="age" />
    <input type="submit" value="提交" />
</form>
<br>
<img src="static/image/mmexport1595327593119.jpg" alt="图片加载失败！"/>
</body>
</html>
