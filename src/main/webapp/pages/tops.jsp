<%--
  Created by IntelliJ IDEA.
  User: ilyat
  Date: 27.06.2021
  Time: 21:04
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
        document.getElementById(topId).submit();

    }

    function search() {
        var command = "search_tops";
        var searchString = document.getElementById("search").value
        var userObj = {
            "command": command,
            "searchString": searchString
        }
        var url = "http://localhost:8080${pageContext.request.contextPath}/ajax_controller";


        $.ajax({
            url: url,
            method: "GET",
            data: userObj,
            dataType: "json",
            error: function (message) {
                console.log(message);
            },
            success: function (data) {
                console.log(data);
                showTops(data);
            }
        });
    }

    function searchR(rating) {
        var command = "search_tops_rating";
        var searchString = document.getElementById("search").value
        var userObj = {
            "command": command,
            "searchString": searchString,
            "rating": rating
        }
        var url = "http://localhost:8080${pageContext.request.contextPath}/ajax_controller";


        $.ajax({
            url: url,
            method: "GET",
            data: userObj,
            dataType: "json",
            error: function (message) {
                console.log(message);
            },
            success: function (data) {
                console.log(data);
                showTops(data);
            }
        });
    }

    function showTops(tops) {
        console.log(tops);
        var section = document.getElementById("tops");
        section.innerHTML = "";
        for (var i = 0; i < tops.length; i++) {
            var div = document.createElement('div');
            div.className = 'col-3'
            var str = '<form action="controller" method="post" id="' + tops[i].id + '" name="' + tops[i].id + '"> <input type="hidden" name="command"  value="to_top_page"/> <input type="hidden" name="topid" value="' + tops[i].id + '"/><a id="' + tops[i].id + '" class="card mb-3" style="width: 100%; text-decoration: none; color: black; border: none" > <div class="card-body" style="padding: 0"> <img src="${pageContext.request.contextPath}/upload?imageName=' + tops[i].image + '" height="255px" width="100%"alt="' + tops[i].image + '"> </div> <div class="card-body" style="text-align: center; padding: 0">' + tops[i].title + '</div> </a> </form>'
            div.innerHTML = str;
            section.appendChild(div);
            var topId = tops[i].id;
            document.getElementById(topId).addEventListener("click", function () {
                toTop(topId)
            }, false);
        }
    }

    function showTopsByRating(count) {
        var div = document.getElementById('buttonRating');
        if (count === 'up') {
            searchR(1);
            div.innerHTML = '<button type="button" id="buttonR" class="btn btn-outline-dark"><fmt:message key="label.rating"/> <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-up-square" viewBox="0 0 16 16"> <path fill-rule="evenodd" d="M15 2a1 1 0 0 0-1-1H2a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V2zM0 2a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V2zm8.5 9.5a.5.5 0 0 1-1 0V5.707L5.354 7.854a.5.5 0 1 1-.708-.708l3-3a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L8.5 5.707V11.5z"></path> </svg></button>';
            document.getElementById('buttonR').addEventListener("click", function () {
                showTopsByRating('down')
            }, false);
        } else if (count === 'down') {
            searchR(-1);
            div.innerHTML = '<button type="button" id="buttonR" class="btn btn-outline-dark"><fmt:message key="label.rating"/> <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-down-square" viewBox="0 0 16 16"> <path fill-rule="evenodd" d="M15 2a1 1 0 0 0-1-1H2a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V2zM0 2a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V2zm8.5 2.5a.5.5 0 0 0-1 0v5.793L5.354 8.146a.5.5 0 1 0-.708.708l3 3a.5.5 0 0 0 .708 0l3-3a.5.5 0 0 0-.708-.708L8.5 10.293V4.5z"></path> </svg> </button>';
            document.getElementById('buttonR').addEventListener("click", function () {
                showTopsByRating('any')
            }, false);
        } else if (count === 'any') {
            search();
            div.innerHTML = '<button type="button" id="buttonR" class="btn btn-outline-dark"><fmt:message key="label.rating"/> <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-app" viewBox="0 0 16 16"> <path d="M11 2a3 3 0 0 1 3 3v6a3 3 0 0 1-3 3H5a3 3 0 0 1-3-3V5a3 3 0 0 1 3-3h6zM5 1a4 4 0 0 0-4 4v6a4 4 0 0 0 4 4h6a4 4 0 0 0 4-4V5a4 4 0 0 0-4-4H5z"></path> </svg> </button>';
            document.getElementById('buttonR').addEventListener("click", function () {
                showTopsByRating('up')
            }, false)
        }
    }
</script>
<jsp:include page="header.jsp"/>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Tops</title>
</head>
<body>

<div style="margin-left: 20%">
    <input class="form-control mr-sm-2" id="search" type="text" placeholder="<fmt:message key="label.search"/>"
           maxlength="15" pattern="^[a-zA-Zа-яА-Я-\\s]{0,15}$" style="width: 900px">
    <div style="display: flex; margin-top:10px;">
        <button class="btn btn-outline-dark my-2 my-sm-0" onclick="search()"><fmt:message key="label.search"/></button>
        <div id="buttonRating" style="margin-left:10px;">
            <button type="button" class="btn btn-outline-dark" onclick="showTopsByRating('up')">
                <fmt:message key="label.rating"/>
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-app"
                     viewBox="0 0 16 16">
                    <path d="M11 2a3 3 0 0 1 3 3v6a3 3 0 0 1-3 3H5a3 3 0 0 1-3-3V5a3 3 0 0 1 3-3h6zM5 1a4 4 0 0 0-4 4v6a4 4 0 0 0 4 4h6a4 4 0 0 0 4-4V5a4 4 0 0 0-4-4H5z"></path>
                </svg>
            </button>
        </div>
    </div>
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
                                 height="255px" width="100%"
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
</body>
</html>
