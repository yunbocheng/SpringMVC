<%--
  Created by IntelliJ IDEA.
  User: YunboCheng
  Date: 2021/11/24
  Time: 9:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% application.setAttribute("path",request.getContextPath());%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<p>一个拦截器</p>
<form action="${path }/doSome.do">
    姓名：<input type="text" name="name"/><br/>
    年龄：<input type="text" name="age"/><br/>
    <input type="submit" value="提交请求">
</form>
</body>
</html>
