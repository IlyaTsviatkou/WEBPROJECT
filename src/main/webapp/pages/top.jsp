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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
    function addInput() {
            var str = '<input type="text" name="description" id="description" placeholder="Description"><input type ="submit" id="submitR" class="submit">';
            document.getElementById('input').innerHTML = str;
            document.getElementById ("submitR").addEventListener ("click", addReport, false);
    }
    function addReport() {
        var desc = document.getElementById('description').value;
        var topId = document.getElementById('topId').value;
        var command = "add_report"
        var str = '<input type ="submit" value="report" id="submitR" class="submit">';
        document.getElementById('input').innerHTML = str;
        document.getElementById ("submitR").addEventListener ("click", addInput, false);
        var userObj = {
            "description": desc,
            "command": command,
            "topid": topId
        }
        var url = "http://localhost:8080${pageContext.request.contextPath}/ajax_controller";

        $.ajax({
            url: url,
            method: "post",
            data: userObj,
            error: function(message) {
                console.log(message);
            },
            success: function(data) {
                console.log(data);

            }
        });
    }
    function like() {
        var topId = document.getElementById('topId').value;
        var command = "estimate_as_like"
        var userObj = {
            "command": command,
            "topid": topId
        }
        var url = "http://localhost:8080${pageContext.request.contextPath}/ajax_controller";

        $.ajax({
            url: url,
            method: "post",
            data: userObj,
            error: function(message) {
                console.log(message);
            },
            success: function(data) {
                console.log(data);

            }
        });
    }
    function dislike() {
        var topId = document.getElementById('topId').value;
        var command = "estimate_as_dislike"
        var userObj = {
            "command": command,
            "topid": topId
        }
        var url = "http://localhost:8080${pageContext.request.contextPath}/ajax_controller";

        $.ajax({
            url: url,
            method: "post",
            data: userObj,
            error: function(message) {
                console.log(message);
            },
            success: function(data) {
                console.log(data);

            }
        });

    }
</script>
<head>
    <title>${top.getTitle()}</title>

</head>
<body>
<input id="error_message" type="hidden" value="${error_message}"/>
<%--fixme get path from resources--%>
<img src="${pageContext.request.contextPath}/upload?imageName=${top.getImage()}" height="50" width="50" alt="${top.getImage()}">
Title : ${top.getTitle()}
DESCRIPTION : ${top.getDescription()}
    <input type="hidden" name="topid" id="topId" value="${top.getId()}"/>
<c:if test="${top.getUser() != sessionScope.user.getId()}">
    <div id="input">
    <input type ="submit" value="report" class="submit"  id="submitR" onclick="addInput()" >
    </div>
<%--fixme add check if user has voted already --%>
    <div>
        <input type ="submit" value="like" class="submit"  id="like" onclick="like()" >
        <input type ="submit" value="dislike" class="submit"  id="dislike" onclick="dislike()" >
    </div>
</c:if>
</br>
</br>
<form name="topForm" action="upload" method="post" enctype="multipart/form-data">
    <input type="hidden" name="command" value="create_item"/>
    <input type="hidden" name="topid" value="${top.getId()}"/>
    <input type="text" name="title" value="" placeholder="title" maxlength="45" required >
    <input type="text" name="description" value="" placeholder="description" maxlength="200" >
    <label for="file">Choose file to upload</label>
    <input type="file" id="file" name="imageName" >
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
    <img src="${pageContext.request.contextPath}/upload?imageName=${item.getImage()}" height="50" width="50" alt="${item.getImage()}">
    <p> Title: <c:out value="${item.getTitle()}" /></p>
    <p> Description: <c:out value="${item.getDescription()}" /></p>
    <input type="submit" name="delete" value="<fmt:message key="label.delete"/>">
    </br>
    </br>
</form>
</c:forEach>

</body>
</html>
