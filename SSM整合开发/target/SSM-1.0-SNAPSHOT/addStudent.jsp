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
    <title>添加学生信息</title>
    <style>
        .myStudent{
            font-size: 30px;
            color: red;
        }
    </style>
</head>
<body>

<div align="center" class="myStudent">注册学生信息</div>
<form action="${path }/student/addStudent.do" method="post">
    <table align="center" border="2px">
        <tr>
            <th>
                学号：
            </th>
            <td>
                <%--需要保证请求参数名和Student类中的属性名一致--%>
                <input type="text" name="num" />
            </td>
        </tr>
        <tr>
            <th>
                省份：
            </th>
            <td>
                <%--需要保证请求参数名和Student类中的属性名一致--%>
                <input type="text" name="province" />
            </td>
        </tr>
        <tr>
            <th>
                城市：
            </th>
            <td>
                <%--需要保证请求参数名和Student类中的属性名一致--%>
                <input type="text" name="city" />
            </td>
        </tr>
        <tr>
            <th>
                姓名：
            </th>
            <td>
                <%--需要保证请求参数名和Student类中的属性名一致--%>
                <input type="text" name="name" />
            </td>
        </tr>
        <tr>
            <th>
                年龄：
            </th>
            <td>
                <%--需要保证请求参数名和Student类中的属性名一致--%>
                <input type="text" name="age" />
            </td>
        </tr>
        <tr>
            <th>
                分数：
            </th>
            <td>
                <%--需要保证请求参数名和Student类中的属性名一致--%>
                <input type="text" name="score" />
            </td>
        </tr>
        <tr>
            <th>
                教室：
            </th>
            <td>
                <%--需要保证请求参数名和Student类中的属性名一致--%>
                <input type="text" name="room" />
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="录入" />
            </td>
            <td>
                <input type="reset" value="重置" />
            </td>
        </tr>
    </table>
</form>
</body>
</html>
