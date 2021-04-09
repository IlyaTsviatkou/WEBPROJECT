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
    <title>LOGIN</title>
</head>
<body>
<form name="loginForm" action="controller" method="post">
    <input type="hidden" name="command" value="login" />
    <input type="text" name="login" value="" placeholder="login" required pattern="^(?=[A-Za-z])[A-Za-z\d\_]{5,}$">
    <input type="password" name="password" value="" placeholder="password" required pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$">
    <br/>
    <input type="submit" name="submit" value="login">
</form>
<br/>
</body>
</html>
