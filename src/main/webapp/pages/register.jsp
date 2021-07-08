<%--
  Created by IntelliJ IDEA.
  User: ilyat
  Date: 09.04.2021
  Time: 01:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<jsp:include page="header.jsp"/>
<head>
    <title><fmt:message key="label.registration"/></title>
</head>
<body>
<br/>
<div style="margin-left: 15px">
    <form name="registerForm" action="controller" method="post">
        <input type="hidden" name="command" value="register"/>
        <input type="text" name="login" value="" placeholder="<fmt:message key="label.nickname"/>" minlength="5"
               maxlength="15"
               required pattern="^(?=[A-Za-z])[A-Za-z\d]{5,15}">
        <input type="password" name="password" value="" placeholder="<fmt:message key="label.password"/>" minlength="8"
               maxlength="20"
               required pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,20}">
        <input type="email" name="email" value="" placeholder="<fmt:message key="label.email"/>" minlength="5"
               maxlength="30"
               required>

        <br/>
        <input type="submit" name="submit" value="<fmt:message key="label.register"/>">
    </form>
</div>
</body>
</html>
