<%--
  Created by IntelliJ IDEA.
  User: YunboCheng
  Date: 2021/11/22
  Time: 9:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--这里需要获取到该项目的根路径--%>
<%
    String basePath = request.getScheme() + "://" +
                    request.getServerName() + ":" + request.getServerPort() +
                    request.getContextPath() + "/";
    /*以上代码获取到的是该项目的根路径，
    * getScheme(): 获取URL中的请求协议
    * getServerName() : 获取URL中的IP地址
    * getServerPort() : 获取URL中的端口号
    * getContextPath() : 获取URL中的资源文件路径
    * */
%>
<html>
<head>
    <title>base标签的使用方式</title>
    <base href="<%=basePath%>">
</head>
<body>
<a href="user/some.do">base标签的使用方式(base标签中的路径+user/some.do为最终的URL路径)</a>
</body>
</html>
