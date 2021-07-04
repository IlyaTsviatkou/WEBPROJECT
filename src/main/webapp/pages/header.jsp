<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>

<html>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
        integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
        integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    <h1><a style="text-decoration: none;color: black;" href="http://localhost:8080${pageContext.request.contextPath}/">TOPOFTOPS!</a>
    </h1>

    <c:if test="${sessionScope.user.getLogin() != null}">
        <fmt:message key="label.nickname"/>: ${sessionScope.user.getLogin()}
        <c:if test="${sessionScope.user.getStatus() == 1}">
            <form name="profile" action="controller" method="post">
                <input type="hidden" name="command" value="to_profile_page">
                <input type="submit" name="submit" value="<fmt:message key="label.profile"/>">
            </form>
        </c:if>
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
