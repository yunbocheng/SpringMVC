<%--
  Created by IntelliJ IDEA.
  User: YunboCheng
  Date: 2021/11/24
  Time: 19:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>年龄异常</title>
</head>
<body>
    <p>年龄异常</p>
    <%--这个错误信息是我们在处理异常放方法中给出的--%>
    提示信息：${msg}<br>
    <%--
        这个错误信息是我们在创建异常类的对象的时候，调用有参构造传递的。
        这个message是异常类中的属性。
        如果属性给定msg，那么就写 ${ex.msg}
    --%>
    系统异常消息：${ex.message}
</body>
</html>
