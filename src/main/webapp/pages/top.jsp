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
<%@ taglib prefix="mytag" uri="item" %>
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
    function addInput() {
        var str = '<input type="text" name="description" id="description" placeholder="<fmt:message key="label.report"/>" required pattern="^[a-zA-Zа-яА-Я-\\s]{1,100}$"><input type ="submit" id="submitR" value="<fmt:message key="label.send"/>" class="submit">';
        document.getElementById('input').innerHTML = str;
        document.getElementById("submitR").addEventListener("click", addReport, false);
    }

    function addReport() {
        var desc = document.getElementById('description').value;
        var topId = document.getElementById('topId').value;
        var command = "add_report"
        var str = '<input type ="submit" value="<fmt:message key="label.report"/>" id="submitR" class="submit" >';
        document.getElementById('input').innerHTML = str;
        document.getElementById("submitR").addEventListener("click", addInput, false);
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
            error: function (message) {
                console.log(message);
            },
            success: function (data) {
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
            error: function (message) {
                console.log(message);
            },
            success: function (data) {
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
            error: function (message) {
                console.log(message);
            },
            success: function (data) {
                console.log(data);

            }
        });

    }

    function createItem() {
        document.getElementById('itemCreateForm').submit();

    }

    function deleteItem(itemId) {
        var topId = document.getElementById('topId').value;

        var command = "delete_item";
        var userObj = {
            "command": command,
            "topid": topId,
            "itemid": itemId
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

    function downItem(itemId) {
        var topId = document.getElementById('topId').value;

        var command = "change_item_place";
        var userObj = {
            "command": command,
            "topid": topId,
            "itemid": itemId,
            "count": -1
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

    function upItem(itemId) {
        var topId = document.getElementById('topId').value;

        var command = "change_item_place";
        var userObj = {
            "command": command,
            "topid": topId,
            "itemid": itemId,
            "count": 1
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

    function updateItem(itemId) {
        var title = document.getElementById('title' + itemId).value;
        var description = document.getElementById('description' + itemId).value;

        var command = "update_item";
        var userObj = {
            "command": command,
            "description": description,
            "itemid": itemId,
            "title": title
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
<jsp:include page="header.jsp"/>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    <title>${top.getTitle()}</title>

</head>
<body>
<input id="error_message" type="hidden" value="${error_message}"/>
<input id="refreshed" value="no" type="hidden"/>
<div style=" display: flex; flex-direction: row; align-items: flex-start; margin-left: 10%; margin-top: 10px; margin-bottom: 10px">
    <div>
        <img src="${pageContext.request.contextPath}/upload?imageName=${top.getImage()}" height="150px" width="150px"
             alt="${top.getImage()}">
    </div>
    <div style="display: flex; flex-direction: column;  align-items: flex-start; margin-left: 20px">
        <h2>${top.getTitle()}</h2>
        <h2>${top.getDescription()}</h2>
        <input type="hidden" name="topid" id="topId" value="${top.getId()}"/>
        <c:if test="${top.getUser() != sessionScope.user.getId() && sessionScope.user != null && sessionScope.user.getStatus() == 1}">
            <div id="input">
                <input type="submit" value="<fmt:message key="label.report"/>" class="submit" id="submitR"
                       onclick="addInput()">
            </div>
            <div>
                <input type="submit" value="<fmt:message key="label.like"/>" class="submit" id="like" onclick="like()">
                <input type="submit" value="<fmt:message key="label.dislike"/>" class="submit" id="dislike"
                       onclick="dislike()">
            </div>
        </c:if>
    </div>
</div>
</br>
<div style=" margin-left: 10%;  margin-bottom: 20px">

    <c:if test="${top.getUser() == sessionScope.user.getId() || sessionScope.user.getRole() == 1 }">
        <form name="topForm" id="deleteForm" action="controller" method="post" enctype="">
            <input type="hidden" name="command" value="delete_top"/>
            <input type="hidden" name="topid" value="${top.getId()}"/>
            <input type="submit" name="submit" value="<fmt:message key="label.delete"/>">
        </form>
        <form name="topForm" id="itemCreateForm" action="upload" method="post" enctype="multipart/form-data">
            <input type="hidden" name="command" value="create_item"/>
            <input type="hidden" name="topid" value="${top.getId()}"/>
            <input type="text" name="title" id="itemTitle" value="" placeholder="<fmt:message key="label.title"/>"
                   maxlength="15" required pattern="^[a-zA-Zа-яА-Я-\s\d]{1,15}$">
            <input type="text" name="description" id="itemDescription" value=""
                   placeholder="<fmt:message key="label.description"/>" maxlength="40" required
                   pattern="^[a-zA-Zа-яА-Я-\s\d]{1,40}$">
            <label for="file"><fmt:message key="label.choose_file_to_upload"/></label>
            <input type="file" id="file" name="imageName">
        </form>
        <input type="submit" name="submit" onclick="createItem()" value="<fmt:message key="label.add_item"/>">
    </c:if>
</div>

<c:if test="${top.getUser() == sessionScope.user.getId() || sessionScope.user.getRole() == 1 }">
    <div id="items" class="row mb-3" style="width: 85%; margin-left: 10%">
        <c:forEach var="item" items="${top.getItems()}" varStatus="status">
            <div class="col-3">
                <div class="card-body" style="padding: 0 ">
                    <img src="${pageContext.request.contextPath}/upload?imageName=${item.getImage()}" height="100"
                         width="100"
                         alt="${item.getImage()}">
                </div>
                <div class="card-body" style="">
                    <p><fmt:message key="label.place"/> : <c:out value="${item.getPlace()}"/></p>
                    <p>
                            <fmt:message key="label.title"/> : <input type="text" id="title${item.getId()}"
                                                                      value="${item.getTitle()}" maxlength="15"
                                                                      pattern="^[a-zA-Zа-яА-Я-\s\d]{1,15}$"/>
                    <p><fmt:message key="label.description"/> : <input type="text" id="description${item.getId()}"
                                                                       value="${item.getDescription()} " maxlength="40"
                                                                       pattern="^[a-zA-Zа-яА-Я-\s\d]{1,40}$"/></p>
                    <div style=" display: flex; flex-direction: row; align-items: flex-start;">
                        <button type="button" id="buttonR1" class="btn btn-outline-dark"
                                onclick="upItem(${item.getId()})">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                 class="bi bi-arrow-down-square" viewBox="0 0 16 16">
                                <path fill-rule="evenodd"
                                      d="M15 2a1 1 0 0 0-1-1H2a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V2zM0 2a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V2zm8.5 2.5a.5.5 0 0 0-1 0v5.793L5.354 8.146a.5.5 0 1 0-.708.708l3 3a.5.5 0 0 0 .708 0l3-3a.5.5 0 0 0-.708-.708L8.5 10.293V4.5z"></path>
                            </svg>
                        </button>
                        <button type="button" id="buttonR2" class="btn btn-outline-dark"
                                onclick="downItem(${item.getId()})">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                 class="bi bi-arrow-up-square" viewBox="0 0 16 16">
                                <path fill-rule="evenodd"
                                      d="M15 2a1 1 0 0 0-1-1H2a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V2zM0 2a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V2zm8.5 9.5a.5.5 0 0 1-1 0V5.707L5.354 7.854a.5.5 0 1 1-.708-.708l3-3a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L8.5 5.707V11.5z"></path>
                            </svg>
                        </button>

                        <input type="submit" name="save" onclick="updateItem(${item.getId()})"
                               value="<fmt:message key="label.save"/>">
                        <input type="submit" name="delete" onclick="deleteItem(${item.getId()})"
                               value="<fmt:message key="label.delete"/>">
                    </div>
                </div>


            </div>
        </c:forEach>
    </div>
</c:if>
<%--<mytag:item/>--%>

<c:if test="${(top.getUser() != sessionScope.user.getId() && sessionScope.user.getRole() != 1 ) || sessionScope.user == null }">
<div id="items" class="row mb-3" style="width: 85%; margin-left: 10%">
    <c:forEach var="item" items="${top.getItems()}" varStatus="status">
        <div class="col-3">
            <div class="card mb-3" style="width: 100%; color: black; border: black">
                <div class="card-body" style="padding: 0 ">
                    <img src="${pageContext.request.contextPath}/upload?imageName=${item.getImage()}" height="100"
                         width="100"
                         alt="${item.getImage()}">
                </div>
                <div class="card-body" style="">
                    <mytag:item item="${item}"/>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
<div>
    </c:if>
</body>
</html>
