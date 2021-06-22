<%--
  Created by IntelliJ IDEA.
  User: ilyat
  Date: 17.06.2021
  Time: 18:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<jsp:include page="header.jsp" />
<head>
    <title>ADMIN PANEL</title>
</head>
<body>
    </br>
    </br>
    </br>
    <form action="controller" method="post" name="findUsers">
        <input type="hidden" name="command" value="find_users_by_login" />
        <input type="text" name="login" value="" placeholder="login" required pattern="^(?=[A-Za-z])[A-Za-z\d\_]{1,}$">
        <input type="submit" name="submit" value="find">
    </form>
    <form name="topPageForm" action="controller" method="post">
        <input type="hidden" name="command" value="to_top_page" />
        <input type="text" name="title" value="" placeholder="title" maxlength="45">
        <br/>
        <input type="submit" name="submit" value="<fmt:message key="label.top_page"/>">
    </form>
    <jsp:include page="users.jsp" />
</body>

</html>
