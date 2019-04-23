<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
<!-- 注意！表单必须添加 enctype 属性，值为"multipart/form-data" -->
<form action="FileEncryptorAction.action" method="post" enctype="multipart/form-data">
    请选择加密类型：
    <select name="encryptorType">
        <option value="AES">AES</option>
        <option value="ECC">ECC</option>
        <option value="RSA">RSA</option>
        <option value="PIC">PIC</option>
    </select>
    <%--选择加密文件：--%>
    <%--<input type="file" name="file" />--%>
    <%--加密后文件存放地址：--%>
    <%--<input type="file" name="encryptorFile"/><br>--%>
    <input type="submit" value="上传"/>
</form>
</body>
</html>