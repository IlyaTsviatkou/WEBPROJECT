<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1>"WEB APP!"
</h1>
<br/>
<form name="loginForm" action="hello-servlet" method="post">
    <input type="hidden" name="command" value="login" />
    <input type="text" name="login" value="" placeholder="login">
    <input type="text" name="password" value="" placeholder="password">
    <br/>
    <input type="submit" name="submit" value="login">
</form>
<br/>
<form name="sortForm" action="hello-servlet" method="post">
    <input type="hidden" name="command" value="sort_by_id" />
    <input type="submit" name="submit" value="sort">
</form>
<br/>
<br/>
<form name="registerForm" action="hello-servlet" method="post">
    <input type="hidden" name="command" value="register" />
    <input type="text" name="login" value="" placeholder="login">
    <input type="text" name="password" value="" placeholder="password">
    <input type="text" name="information" value="" placeholder="information">
    <br/>
    <input type="submit" name="submit" value="register">
</form>
<hr/>
<a href="hello-servlet">LOGGER TEST</a>
</body>
</html>