<%--
  Created by IntelliJ IDEA.
  User: Gavin
  Date: 2019/4/22
  Time: 9:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta http-equiv="refresh" content='5; url=http://localhost:8888/index.jsp'>
<script type="text/javascript">
    var time =5;
    function  aaa()
    {
        window.setTimeout('aaa()', 1000);
        time=time-1;
        document.getElementById("bb").innerHTML=time;

    }
</script>
<html style="background-color: #9d9d9d">
<head>
    <title>fail</title>
</head>
<body onload="aaa()">
<center>
    <strong>
        <br><br><br><br><br><br><br><br><br><br><br><br>
        文件加解密失败！<br><br>
        <span id="bb" style="color:red;">time </span>秒后将自动跳转到系统首页。
        <br>
    </strong>
</center>

</body>
</html>
