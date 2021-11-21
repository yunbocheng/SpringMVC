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

    <p>提交参数给Controller</p><br>

    <p>请求参数与处理器的方法中的形参一致时</p>
    <form action="receiveproperty.do" method="post">
        姓名：<input type="text" name="name"/><br/>
        年龄：<input type="text" name="age"/><br/>
        <input type="submit" value="提交参数">
    </form>

    <%--测试当请求参数处理方法的形参名不同的时候的处理方案--%>
    <p>请求参数与处理器的方法中的形参不一致时</p>
    <form action="receivechange.do" method="post">
        姓名：<input type="text" name="rName"/><br/>
        年龄：<input type="text" name="rAge"/><br/>
        <input type="submit" value="提交参数">
    </form>

    <p>使用Java对象接收请求参数，此时需要一个类的实体类对象，用来将参数信息封装到一个对象中</p>
    <form action="receiveObject.do" method="post">
        姓名：<input type="text" name="name"/><br/>
        年龄：<input type="text" name="age"/><br/>
        性别：<input type="text" name="rSex">
        <input type="submit" value="提交参数">
    </form>
</body>
</html>

