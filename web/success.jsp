<%--
  Created by IntelliJ IDEA.
  User: Gavin
  Date: 2019/5/13
  Time: 10:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta http-equiv="refresh" content='5; url=http://localhost:8888/'>
<script type="text/javascript">
    var time =5;
    function  aaa()
    {
        window.setTimeout('aaa()', 1000);
        time=time-1;
        document.getElementById("bb").innerHTML=time;

    }
</script>
<html>
<head>
    <title>success</title>
</head>
<body onload="aaa()">
文件加解密成功！<br>
<span id="bb" style="color:red;">time </span>秒后将自动跳转到系统首页。
<br>
</body>
</html>
