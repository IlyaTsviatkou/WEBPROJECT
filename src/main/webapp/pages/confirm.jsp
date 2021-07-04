<%--
  Created by IntelliJ IDEA.
  User: ilyat
  Date: 01.06.2021
  Time: 01:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<jsp:include page="header.jsp"/>
<head>
    <title><fmt:message key="label.confirmed"/></title>
</head>
<body>
    <h><fmt:message key="label.your_account_confirmed"/></h>
</body>
</html>
