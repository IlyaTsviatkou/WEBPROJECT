<%--
  Created by IntelliJ IDEA.
  User: ilyat
  Date: 26.03.2021
  Time: 02:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<script>
    function toTop(topId) {
        // var topId = document.getElementById('topid').value;
        document.getElementById(topId).submit();
    }
</script>
<jsp:include page="header.jsp"/>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title><fmt:message key="label.profile"/></title>
    <h3><fmt:message key="label.nickname"/> : ${sessionScope.user.getLogin()}</h3>
    <h3><fmt:message key="label.email"/> : ${sessionScope.user.getEmail()}</h3>
</head>
<body>
<c:if test="${tops.size() > 0}">
    <h2><fmt:message key="label.my_tops"/></h2>
    <div style="margin-left: 20%">
        <div id="tops" class="row mb-3" style="width: 75%">
            <c:forEach var="top" items="${tops}" varStatus="status">
                <div class="col-3">
                    <form action="controller" method="post" id="${top.getId()}" name="${top.getId()}">
                        <input type="hidden" name="command" value="to_top_page"/>
                        <input type="hidden" name="topid" value="${top.getId()}"/>
                        <a class="card mb-3" style="width: 100%; text-decoration: none; color: black; border: none"
                           onclick="toTop(${top.getId()})">
                            <div class="card-body" style="padding: 0">
                                <img src="${pageContext.request.contextPath}/upload?imageName=${top.getImage()}"
                                     height="255px2" width="100%"
                                     alt="${top.getImage()}">
                            </div>
                            <div class="card-body" style="text-align: center; padding: 0"><c:out
                                    value="${top.getTitle()}"/></div>
                        </a>
                    </form>
                </div>
            </c:forEach>
        </div>
    </div>
</c:if>
</body>
</html>
