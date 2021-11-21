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
    <title>返回值类型</title>
    <script type="text/javascript" src="JQuery/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">
        /*给按钮绑定单击事件*/
        $(function (){
            $(function (){
                /*使用标签选择器*/
                $("button").click(function (){
                    // alert("button click");
                    /*指定ajax的参数信息*/
                    $.ajax({
                        // url:"returnVoid-ajax.do",(返回数据类型为void)
                        // url:"returnStudentJson.do",(返回的数据类型为Object对象)
                        // 以下返回的是 Object的List集合。
                        // url:"returnStudentJsonArray.do",
                        // 以下返回是一个普通String的数据
                        url:"returnStringData.do",
                        data:{
                            /*这两个数据是前端向后端的处理器方法传递的数据，数据名要和处理器方法的形参一致才可以获取到*/
                            name:"zhangsan",
                            age:20
                        },
                        type:"post",
                        /*
                            resp从服务器端返回的是json格式的字符串 {"name":"zhangsan","age":20}
                            JQuery会把这个字符串转换为json对象，赋值给resp形参。
                            所以此时的resp是一个json对象。
                        */
                        // dataType:"json",
                        dataType:"text",
                        /*
                            resp代表响应回来的数据，此时这个数值就是后端传递回来的json这个字符串
                            虽然获取到的是一个json格式的字符串，但是ajax会把这个对象转化那位一个json对象。
                            这个转换是使用jQuery实现的。
                        */
                        success:function (resp){
                            // alert(resp.name + "  " + resp.age);

                            /*
                            * 这个 resp 的值根据返回值数据的不同，也会有所变化
                            * 当返回值是void的时候：返回值是一个json格式的数据 {"name":"李四","age":20}
                            * 当返回值是Object的时候：返回值是一个json格式的数据 {"name":"李四","age":20}
                            * 当返回值是List<Object>的时候：返回值此时是一个json对象数组 [{"name":"张三","age":20},{"name":"李四","age":22}]
                            * 注意：数组中json对象的顺序和存储到List集合中的Object对象的顺序是一样的。
                            * */
                            // 当返回的是一个json对象数组的时候，此时我们可以使用jQuery中的循环函数。
                            // i代表的是循环变量，n代表的是遍历的json对象
                         /*   $.each(resp,function (i,n){
                                alert(n.name + "  " + n.age);
                            })*/

                            /*
                                以下返回的是一个普通的String数据，而不是视图
                                此时返回的数据是一个普通的字符串，而不是json格式的数据
                                所以此时需要修改 ajax中请求的数据类型（dataType）为普通文本类型
                                dataType:"text"
                            */
                            alert("获取到的普通文本为：" + resp);
                        }
                    })
                })
            })
        })
    </script>
</head>
<body>

    <p>处理器方法的返回值类型</p><br>

    <p>处理器方法返回String表示视图名称，逻辑名称</p>
    <form action="returnString-view.do" method="post">
        姓名：<input type="text" name="name"/><br/>
        年龄：<input type="text" name="age"/><br/>
        <input type="submit" value="提交参数">
    </form>

    <p>处理器方法返回String表示视图名称，全路径名称</p>
    <form action="returnString-view2.do" method="post">
        姓名：<input type="text" name="name"/><br/>
        年龄：<input type="text" name="age"/><br/>
        <input type="submit" value="提交参数">
    </form>
    <br>

    <%--给这个按钮添加一个单击事件--%>
    <button id="btn">发起ajax请求</button>
</body>
</html>

