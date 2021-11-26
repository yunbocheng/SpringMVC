<%--
  Created by IntelliJ IDEA.
  User: YunboCheng
  Date: 2021/11/23
  Time: 19:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% application.setAttribute("path",request.getContextPath());%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<p>使用请求转发的方式</p>
<form action="${path }/some.do">
    姓名：<input type="text" name="name"/><br/>
    年龄：<input type="text" name="age"/><br/>
    <input type="submit" value="请求转发(forward)">
</form>
<br>
<p>使用重定向的方式</p>
<form action="${path }/some2.do">
    姓名：<input type="text" name="name"/><br>
    年龄：<input type="text" name="age"/><br>
    <input type="submit" value="重定型()"/>
</form>
<br>
<p>处理器方法使用一个对象接收数据(GET)</p>
<form action="${path}/some3.do">
    姓名：<input type="text" name="name"/><br>
    年龄：<input type="text" name="age"/><br>
    <input type="submit" value="重定型()对象"/>
</form>
<br>
<p>处理器方法使用一个对象接收数据(POST)</p>
<form action="${path}/some4.do" method="post">
    姓名：<input type="text" name="name"/><br>
    年龄：<input type="text" name="age"/><br>
    <input type="submit" value="重定型()对象"/>
</form>
</body>
</html>
