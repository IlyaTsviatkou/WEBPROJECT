<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="pages/error.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<!DOCTYPE html>
<html>
<style>

</style>
<jsp:include page="pages/header.jsp" />
<head>

</head>
<body>
</body>
</br>
</br>
</br>
<c:if test="${sessionScope.user.getRole() == 1}">
<form name="toAdminPanelForm" action="controller" method="post">
    <input type="hidden" name="command" value="to_admin_panel_page"/>
    <input type="submit" name="submit" value="<fmt:message key="label.admin_panel"/>">
</form>
</c:if>
<c:if test="${sessionScope.user.getLogin() != null}">
    <form name="topForm" action="controller" method="post">
        <input type="hidden" name="command" value="to_create_top_page"/>
        <input type="submit" name="submit" value="<fmt:message key="label.create_top"/>">
    </form>
    <br/>
</c:if>

<form name="changeLocal" action="controller" method="post">
    <p><b><fmt:message key="label.your_language"/></b></p>
    <input type="hidden" name="command" value="change_local"/>
    <p><input name="language" type="radio" value="rus"> <fmt:message key="label.russian"/> </p>
    <p><input name="language" type="radio" value="eng"> <fmt:message key="label.english"/> </p>
    <p><input type="submit" value="<fmt:message key="label.submit"/>"></p>
</form>

</body>
</html>