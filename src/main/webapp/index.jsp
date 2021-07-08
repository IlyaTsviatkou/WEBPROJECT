<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="pages/error.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<!DOCTYPE html>
<html>
<style>

</style>
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

<script>
    function getCheckedRadioValue(name) {
        var elements = document.getElementsByName(name);
        for (var i = 0, len = elements.length; i < len; ++i)
            if (elements[i].checked) return elements[i].value;
    }

    function changeLocal() {

        var command = "change_local";
        var language = getCheckedRadioValue("language")

        var userObj = {
            "command": command,
            "language": language
        }


        var url = "http://localhost:8080${pageContext.request.contextPath}/controller";

        $.ajax({
            url: url,
            method: "post",
            data: userObj,
            error: function (message) {
                console.log(message);

            },
            success: function (data) {
                console.log(data);

                $("body").html(data);

            }
        });
    }

</script>
<jsp:include page="pages/header.jsp"/>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>TOPOFTOPS!</title>
</head>
<body>
</br>
<div style="margin-left: 15px">
    <c:if test="${sessionScope.user.getRole() == 1}">
        <form name="toAdminPanelForm" action="controller" method="post">
            <input type="hidden" name="command" value="to_admin_panel_page"/>
            <input type="submit" name="submit" value="<fmt:message key="label.admin_panel"/>">
        </form>
    </c:if>
    <c:if test="${sessionScope.user.getLogin() != null && sessionScope.user.getStatus() == 1}">
        <form name="topForm" action="controller" method="post">
            <input type="hidden" name="command" value="to_create_top_page"/>
            <input type="submit" name="submit" value="<fmt:message key="label.create_top"/>">
        </form>
        <br/>
    </c:if>


    <%--<form name="changeLocal" id="changeLocal" action="controller" method="post">--%>
    <p><b><fmt:message key="label.your_language"/></b></p>
    <input type="hidden" name="command" value="change_local"/>
    <p><input name="language" type="radio" value="rus"> <fmt:message key="label.russian"/></p>
    <p><input name="language" type="radio" value="eng"> <fmt:message key="label.english"/></p>
    <%--</form>--%>
    <p><input type="submit" onclick="changeLocal()" value="<fmt:message key="label.submit"/>"></p>
    <form name="toTopsForm" action="controller" method="post">
        <input type="hidden" name="command" value="to_tops_page"/>
        <input type="submit" class="btn btn-outline-secondary" value="<fmt:message key="label.tops"/>">
    </form>

</div>
</body>
</html>