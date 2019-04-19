<%--
  Created by IntelliJ IDEA.
  User: Gavin
  Date: 2019/4/17
  Time: 10:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML >
<html>
<head>
    <base href="<%=basePath%>">
    <!-- html 5 的编码设置方式 -->
    <meta charset="UTF-8">
    <title>上传demo</title>
    <script type="text/javascript"  src="static/js/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="http://malsup.github.io/jquery.form.js"></script>
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->

    <script type="text/javascript">

        $(function(){
            //给 “使用ajax上传” 按钮绑定点击事件
            $("#uploadButton").on("click",function(){
                $("#uploatForm").ajaxSubmit({
                    beforeSubmit:function(){//提交前调用
                        //alert('准备提交表单了');
                    },
                    success:function(data){//成功调用
                        //alert("提交成功了");
                        if(typeof(data) == "string"){
                            data = eval('(' + data + ')');;//将返回的json字符串转换为json对象
                            alert(data.message);
                        }
                    }
                });
            });
        });

    </script>
</head>

<body>
<br>
<br>
<form action="upload" method="post" id="uploatForm" enctype="multipart/form-data">
    <input type="file"  name="upload" /><span style="color:red;font-size:13px">不能超过4M</span>
    <br>
    <input type="submit" value="上传(请使用下面的ajax上传---同步上传也可以使用)" disabled="disabled"/>
    <br>
    <input type="button" value="使用ajax上传" id="uploadButton" >
</form>
</body>
</html>
