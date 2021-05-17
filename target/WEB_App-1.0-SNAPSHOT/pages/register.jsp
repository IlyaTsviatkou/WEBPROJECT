<%--
  Created by IntelliJ IDEA.
  User: ilyat
  Date: 09.04.2021
  Time: 01:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>REGISTRATION</title>
</head>
<body>
<br/>
<form name="registerForm" action="controller" method="post">
    <input type="hidden" name="command" value="register" />
    <input type="text" name="login" value="" placeholder="login"  required pattern="^(?=[A-Za-z])[A-Za-z\d\_]{5,20}$">
    <input type="password" name="password" value="" placeholder="password" required pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,30}$">
    <input type="text" name="email" value="" placeholder="email" maxlength="100" >
    <br/>
    <input type="submit" name="submit" value="register">
</form>
</body>
</html>
