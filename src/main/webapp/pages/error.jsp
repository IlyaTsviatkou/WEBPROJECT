<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
<jsp:include page="header.jsp"/>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
    <title><fmt:message key="label.error_page"/></title>
</head>
<body>
<c:if test="${sessionScope.error_message != null}">
    <fmt:message key="label.error_message1"/> ${sessionScope.error_message}<br>
    <fmt:message key="label.error_message2"/> <a href="/WEB_App_war_exploded/"><fmt:message key="label.home_page"/></a>

</c:if>
<c:if test="${sessionScope.error_message == null}">
    <fmt:message key="label.error_message1"/> <%=response.getStatus() %><br>
    <fmt:message key="label.error_message2"/> <a href="/WEB_App_war_exploded/"><fmt:message key="label.home_page"/></a>
</c:if>
</body>
</html>