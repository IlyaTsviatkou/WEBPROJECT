<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>

<html>
<style>
    header {
        display: flex;
        flex-direction: row;
        align-items: center;
        justify-content: space-between;

        padding: 0 1rem 0 1rem;
        background: grey;
    }
</style>
<header>
<h1>"TOPOFTOPS!"
</h1>

    <c:if test="${sessionScope.user.getLogin() != null}">
        <fmt:message key="label.nickname"/>: ${sessionScope.user.getLogin()}
        <form name="logOut" action="controller" method="post">
            <input type="hidden" name="command" value="logout">
            <input type="submit" name="submit" value="<fmt:message key="label.logout"/>">
        </form>
    </c:if>
    <c:if test="${sessionScope.user.getLogin() == null}">
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
    </c:if>

</header>

</html>
