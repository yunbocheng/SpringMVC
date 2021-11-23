<%--
  Created by IntelliJ IDEA.
  User: YunboCheng
  Date: 2021/11/23
  Time: 15:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查询全部的学生</title>
    <%--加入JQuery依赖处理--%>
    <script type="text/javascript" src="JQuery/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">
        /*给button按钮绑定大单机事件*/
        $(function (){
            /*设置ajax的请求时机：在当前的页面加载后，执行loadStudentData()函数查询全部学生对象*/
            loadStudentData();
                /*此时给ajax请求绑定了按钮单机事件，点击按钮才会触发ajax请求。*/
                $("button").click(function (){
                    loadStudentData();
                })
            })
        function loadStudentData(){
            $.ajax({
                url:"student/selectAll.do",
                type:"post",
                dataType:"json",
                success:function (resp){
                    /*
                        加入以下代码可以再次进行ajax请求的时候将上一次ajax请求的结果进行删除
                        保证请求的数据不堆积。也就是清除旧数据
                    * */
                    $("#myBody").html("")
                    /*
                        此时返回的resp是一个json数组
                        i:代表下标值
                        n:代表数组中的json对象
                    */
                    $.each(resp,function (i,n){
                        $("#myBody").append("<tr>")
                            .append("<td>" + n.num + "</td>")
                            .append("<td>" + n.province+ "</td>")
                            .append("<td>" + n.city + "</td>")
                            .append("<td>" + n.name + "</td>")
                            .append("<td>" + n.age + "</td>")
                            .append("<td>" + n.score + "</td>")
                            .append("<td>" + n.room + "</td>")
                            .append("</tr>")

                    })
                }
            })
        }
    </script>
</head>
<body>
    <button>点击查询全部学生对象</button>
    <table>
        <thead>
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
        </thead>
        <tbody id="myBody">

        </tbody>
    </table>
</body>
</html>
