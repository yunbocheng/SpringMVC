<%--
  Created by IntelliJ IDEA.
  User: YunboCheng
  Date: 2021/11/18
  Time: 14:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
    在网络中有一条规则：
        如果不是放在WEB-INF下(也就是在webapp下)的jsp界面不能通过url输入地址直接获取到。
        但是放在WEB-INF下的jsp界面在地址栏输入文件名是访问不到的，会报404
        在WEB-INF下的只能通过相应的请求进行获取。
        这样可以保证访问的安全性。
--%>
<html>
<head>
    <title>展示页面</title>
</head>
<body>
    <h2>这是请求成功的展示页面</h2>
    <h3>${msg}</h3>
    <h3>${fun}</h3>
</body>
</html>
