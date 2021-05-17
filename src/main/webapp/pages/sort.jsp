<%--
  Created by IntelliJ IDEA.
  User: ilyat
  Date: 23.03.2021
  Time: 01:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
</head>
<br/>
<table>
    <h1>USERS</h1>
<c:forEach var="elem" items="${users}" varStatus="status">
    <tr>
        <td><c:out value="${elem.getId()}" /></td>
        <td><c:out value="${elem.getLogin()}" /></td>
        <td><c:out value="${elem.getEmail()}" /></td>
    </tr>
</c:forEach>
</table>
<body>

</body>
</html>
