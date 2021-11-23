<%--
  Created by IntelliJ IDEA.
  User: YunboCheng
  Date: 2021/11/22
  Time: 15:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>查询学生信息</title>
    <style>
        .myDiv{
            color: red;
            font-size: 30px;
        }
    </style>
</head>
<body>
<div class="myDiv">
    全部学生信息
</div>

<%--使用JSTL遍历学生信息--%>
<table align="center" border="2px">
    <tr>
        <th>
            学号
        </th>
        <th>
            省份
        </th>
        <th>
            城市
        </th>
        <th>
            姓名
        </th>
        <th>
            年龄
        </th>
        <th>
            分数
        </th>
        <th>
            教室
        </th>
    </tr>
    <c:forEach items="${list}" var="stuent">
        <tr>
            <td>
                    ${stuent.num}
            </td>
            <td>
                    ${stuent.province}
            </td>
            <td>
                    ${stuent.city}
            </td>
            <td>
                    ${stuent.name}
            </td>
            <td>
                    ${stuent.age}
            </td>
            <td>
                    ${stuent.score}
            </td>
            <td>
                    ${stuent.room}
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
