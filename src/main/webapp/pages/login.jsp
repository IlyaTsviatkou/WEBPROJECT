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
    <title><fmt:message key="label.login"/></title>
</head>
<body>
<form name="loginForm" action="controller" method="post">
    <input type="hidden" name="command" value="login"/>
    <input type="text" name="login" value="" placeholder="<fmt:message key="label.nickname"/>" maxlength="15" required
           pattern="^(?=[A-Za-z])[A-Za-z\d\_]{5,15}$">
    <input type="password" name="password" value="" placeholder="<fmt:message key="label.password"/>" maxlength="20"
           required pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,20}$">
    <br/>
    <input type="submit" name="submit" value="<fmt:message key="label.login"/>">
</form>
<br/>
</body>
</html>
