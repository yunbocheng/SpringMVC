<%--
  Created by IntelliJ IDEA.
  User: YunboCheng
  Date: 2021/11/23
  Time: 20:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>获取信息</title>
</head>
<body>
    <%--
        这是使用的重定向的方式获取跳转到的页面，此时我们不能直接使用EL表达式获取数据
        因为此时的数据在第二次请求的request作用域中不存在name、age数据。
        此时的这两个数据存在于第二次的get请求中的URL参数中

        这里我们需要使用方法来获取get的URL中的数据。
    --%>
    姓名：${param.myName}<br>
    年龄：${param.myAge}<br>
    <%--
        param.myAge 相当于 request.getParamter("myName")
    --%>
    参数信息(姓名)：<%=request.getParameter("myName")%>
    参数信息(年龄)：<%=request.getParameter("myAge")%>
</body>
</html>
