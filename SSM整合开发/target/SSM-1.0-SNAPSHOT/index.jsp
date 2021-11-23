<%--
  Created by IntelliJ IDEA.
  User: YunboCheng
  Date: 2021/11/22
  Time: 9:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% application.setAttribute("path",request.getContextPath());%>
<html>
<head>
    <title>SSM整合开发</title>
    <style>
        .myStudent{
            font-size: 30px;
            color: red;
        }
    </style>
</head>
<body>
    <div align="center" class="myStudent"><a href="${path}/addStudent.jsp">注册学生信息</a></div>
    <div align="center" class="myStudent"><a href="${path }/student/selectStudent.do">查询学生信息</a></div>
    <div align="center" class="myStudent"><a href="${path }/selectAll.jsp">查询学生信息</a></div>
</body>
</html>
