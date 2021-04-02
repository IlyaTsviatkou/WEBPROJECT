<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="pages/error.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1>"WEB APP!"
</h1>
<br/>
<form name="loginForm" action="controller" method="post">
    <input type="hidden" name="command" value="login" />
    <input type="text" name="login" value="" placeholder="login" required pattern="^(?=[A-Za-z])[A-Za-z\d\_]{5,}$">
    <input type="password" name="password" value="" placeholder="password" required pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$">
    <br/>
    <input type="submit" name="submit" value="login">
</form>
<br/>
<form name="sortForm" action="controller" method="post">
    <input type="hidden" name="command" value="find_all_users" />
    <input type="submit" name="submit" value="ShowALl">
</form>
<br/>
<br/>
<form name="registerForm" action="controller" method="post">
    <input type="hidden" name="command" value="register" />
    <input type="text" name="login" value="" placeholder="login" required pattern="^(?=[A-Za-z])[A-Za-z\d\_]{5,}$">
    <input type="password" name="password" value="" placeholder="password" required pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$">
    <input type="text" name="information" value="" placeholder="information">
    <br/>
    <input type="submit" name="submit" value="register">
</form>
<hr/>
<a href="controller">LOGGER TEST</a>
</body>
</html>