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
<br/>
<div class="row" style="margin-left: 5%">
    <form name="loginForm" action="controller" method="post">
        <div class="col" style="margin-bottom: 10px">
            <input type="hidden" name="command" value="login"/>
            <input type="text" name="login" class="form-control" aria-label="Default" aria-describedby="inputGroup-sizing-default" style="width: 250px" value="" placeholder="<fmt:message key="label.nickname"/>" minlength="5"
                   maxlength="15" required
                   pattern="^(?=[A-Za-z])[A-Za-z\d]{5,15}">
        </div>
        <div class="col" style="margin-bottom: 10px">
            <input type="password" name="password" class="form-control" aria-label="Default" aria-describedby="inputGroup-sizing-default" style="width: 250px" value="" placeholder="<fmt:message key="label.password"/>"
                   minlength="8"
                   maxlength="20"
                   required pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,20}$">
        </div>
        <div class="col">
            <input type="submit" class="btn btn-outline-dark" name="submit" value="<fmt:message key="label.login"/>">
        </div>
    </form>
</div>
<br/>
</body>
</html>
