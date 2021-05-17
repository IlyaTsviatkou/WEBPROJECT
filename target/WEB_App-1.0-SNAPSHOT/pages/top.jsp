<%--
  Created by IntelliJ IDEA.
  User: ilyat
  Date: 27.04.2021
  Time: 00:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title>${top.getTitle()}</title>

</head>
<body>
ID : ${top.getId()}
DESCRIPTION : ${top.getDescription()}
IMAGE : ${top.getImage()}
</br>
</br>
<form name="topForm" action="controller" method="post">
    <input type="hidden" name="command" value="create_item"/>
    <input type="hidden" name="topid" value="${top.getId()}"/>
    <input type="text" name="title" value="" placeholder="title" maxlength="45" required >
    <input type="text" name="description" value="" placeholder="description" maxlength="200" >
    <label for="file">Choose file to upload</label>
    <input type="file" id="file" name="file" multiple>
    <input type="submit" name="submit" value="<fmt:message key="label.add_item"/>">
</form>
</br>
</br>
<c:forEach var="item" items="${top.getItems()}" varStatus="status">
<form name="topForm" action="controller" method="post">
    <input type="hidden" name="command" value="delete_item"/>
    <input type="hidden" name="itemid" value="${item.getId()}"/>
    <input type="hidden" name="topid" value="${top.getId()}"/>
<%--    fixme should i use hidden or smth else--%>
    <p> Title: <c:out value="${item.getTitle()}" /></p>
    <p> Description: <c:out value="${item.getDescription()}" /></p>
    <p> Image: <c:out value="${item.getImage()}" /></p>
    <input type="submit" name="delete" value="<fmt:message key="label.delete"/>">
    </br>
    </br>
</form>
</c:forEach>

</body>
</html>
