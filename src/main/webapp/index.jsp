<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="pages/error.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
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
<br/>
<fmt:message key="label.login"/>: ${sessionScope.user.getLogin()}

<br/>
<br/>
<br/>
<div>
    <fmt:message key="label.login"/>
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
<form name="topForm" action="controller" method="post">
    <input type="hidden" name="command" value="to_create_top_page"/>
    <input type="submit" name="submit" value="<fmt:message key="label.create_top"/>">
</form>
<br/>
<form name="changeLocal" action="controller" method="post">
    <p><b><fmt:message key="label.your_language"/></b></p>
    <input type="hidden" name="command" value="change_local"/>
    <p><input name="language" type="radio" value="rus"> <fmt:message key="label.russian"/> </p>
    <p><input name="language" type="radio" value="eng"> <fmt:message key="label.english"/> </p>
    <p><input type="submit" value="<fmt:message key="label.submit"/>"></p>
</form>
<form name="topPageForm" action="controller" method="post">
    <input type="hidden" name="command" value="to_top_page" />
    <input type="text" name="title" value="" placeholder="title" maxlength="45">
    <br/>
    <input type="submit" name="submit" value="<fmt:message key="label.top_page"/>">
</form>
</body>
</html>