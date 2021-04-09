<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="pages/error.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="resources.pagecontent"/>
<!DOCTYPE html>
<html>
<style>

</style>
<head>
    <title>TOPofTOPs</title>
</head>
<body>
<h1>"WEB APP!"
</h1>

Кодировка запроса: ${ pageContext.request.characterEncoding }
<br/>
Локализация: ${sessionScope.locale}
<br/>
<br/>
<div>
    <form name="toLoginPage" action="controller" method="post">
        <input type="hidden" name="command" value="to_login_page">
        <input type="submit" name="submit" value="<fmt:message key="label.login"/>">
    </form>
    <form name="toRegisterPage" action="controller" method="post">
        <input type="hidden" name="command" value="to_register_page">
        <input type="submit" name="submit" value="<fmt:message key="label.register"/>">
    </form>
</div>
<form name="sortForm" action="controller" method="post">
    <input type="hidden" name="command" value="find_all_users"/>
    <input type="submit" name="submit" value="<fmt:message key="label.showall"/>">
</form>
<br/>
<form name="changeLocal" action="controller" method="post">
    <p><b><fmt:message key="label.your_language"/></b></p>
    <input type="hidden" name="command" value="change_local"/>
    <p><input name="language" type="radio" value="rus"> <fmt:message key="label.russian"/> </p>
    <p><input name="language" type="radio" value="eng"> <fmt:message key="label.english"/> </p>
    <p><input type="submit" value="<fmt:message key="label.submit"/>"></p>
</form>
</body>
</html>