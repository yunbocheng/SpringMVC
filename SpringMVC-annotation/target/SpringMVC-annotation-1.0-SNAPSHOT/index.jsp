<%--
  Created by IntelliJ IDEA.
  User: YunboCheng
  Date: 2021/11/18
  Time: 11:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <p>第一个SpringMVC项目</p><br>
    <%--以下的请求方式必须和controller类中的请求方式是一样的，否则会报错 405 --%>
    <%--
        <p><a href="/some.do">发起some.do的请求</a></p>
        在a请求的前边不可以加 "/" 否则会找不到根路径
    --%>
    <%--a连接只能提交GET请求--%>
    <p><a href="User/some.do">发起some.do的请求是get请求方式</a></p>
    <br>

    <%--other.do的请求方式是post--%>
    <form action="User/other.do" method="post">
        <input type="submit" value="post请求other.do">
    </form>
    <br>

    <%--first.do的请求是任意的，没有限制--%>
    <form action="User/first.do" method="post">
        <input type="submit" value="first.dao的请求方式是任意的">
    </form>

</body>
</html>

