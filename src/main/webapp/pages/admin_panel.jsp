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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
    function getReports() {
        var command = "get_reports";

        var userObj = {
            "command": command
        }
        var url = "http://localhost:8080${pageContext.request.contextPath}/ajax_controller";
        var str = '';

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
                showReports(data);
            }
        });
    }

    function showReports(jsonObj) {
        var section = document.getElementById("output");
        section.innerHTML = "";
        for (var i = 0; i < jsonObj.length; i++) {
            var myArticle = document.createElement('article');
            var myH2 = document.createElement('h2');
            var myPara1 = document.createElement('input');
            var myPara2 = document.createElement('input');
            var myDiv = document.createElement('div');
            var button3 = document.createElement('form')

            myArticle.id = "report" + jsonObj[i].id
            myPara1.type = 'hidden';
            myPara1.id = 'topid';
            myPara1.name = jsonObj[i].top
            myPara1.value = jsonObj[i].top
            myPara2.type = 'hidden';
            myPara2.id = 'userid';
            myPara2.value = jsonObj[i].user
            myH2.textContent = jsonObj[i].description;
            myDiv.id = 'buttonsR' + jsonObj[i].id
            button3.action = 'controller'
            button3.method = 'post'
            button3.name = 'topPageForm'
            button3.innerHTML = '<input type="hidden"  name="command" value="to_top_page"/> <input type="hidden" name="topid" value="' + jsonObj[i].top + '" maxlength="45"/> <input type="submit" class="btn btn-outline-dark" name="submit" value="<fmt:message key="label.visit"/>">'


            myArticle.appendChild(myH2);
            myArticle.appendChild(myPara1);
            myArticle.appendChild(myPara2);
            myArticle.appendChild(myDiv);
            myArticle.appendChild(button3);


            section.appendChild(myArticle);

            initializeR(jsonObj[i].top, jsonObj[i].id);

        }
    }

    function initializeR(top, id) {
        var str = '<td><input type="submit" value="<fmt:message key="label.accept"/>" class="btn btn-outline-danger" id="accept' + id + '"></td> <td><input type="submit" value="<fmt:message key="label.decline"/>" class="btn btn-outline-dark" id="decline' + id + '" ></td>';
        document.getElementById('buttonsR' + id).innerHTML = str;
        document.getElementById("accept" + id).addEventListener("click", function () {
            accept(top)
        }, false);
        document.getElementById("decline" + id).addEventListener("click", function () {
            decline(id)
        }, false);

    }

    function getUsers() {
        var command = "find_users_by_login";
        var login = document.getElementById("login").value
        var userObj = {
            "command": command,
            "login": login
        }
        var url = "http://localhost:8080${pageContext.request.contextPath}/ajax_controller";
        var str = '';

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
                showUsers(data);
            }
        });
    }

    function blockUser(id) {
        var command = "block_user";

        var userObj = {
            "command": command,
            "userid": id
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
                var str = '<td><input type="submit" value="<fmt:message key="label.unblock"/>" class="btn btn-outline-dark" id="unblock' + id + '"></td> <td><input type="submit" value="<fmt:message key="label.delete"/>" class="btn btn-outline-danger" id="delete' + id + '" ></td>';
                document.getElementById('buttonsC' + id).innerHTML = str;
                document.getElementById("unblock" + id).addEventListener("click", function () {
                    unblockUser(id)
                }, false);
                document.getElementById("delete" + id).addEventListener("click", function () {
                    deleteUser(id)
                }, false);

                if (data.length > 100) {
                    $("body").html(data);
                    location.replace("http://localhost:8080${pageContext.request.contextPath}/")
                }
            }
        });
    }

    function initialize(id, count) {
        if (count === 1) {
            var str = '<td><input type="submit" value="<fmt:message key="label.block"/>" class="btn btn-outline-dark" id="block' + id + '"></td> <td><input type="submit" value="<fmt:message key="label.delete"/>" class="btn btn-outline-danger" id="delete' + id + '" ></td>';
            document.getElementById('buttonsC' + id).innerHTML = str;
            document.getElementById("block" + id).addEventListener("click", function () {
                blockUser(id)
            }, false);
            document.getElementById("delete" + id).addEventListener("click", function () {
                deleteUser(id)
            }, false);
        }
        if (count === 2) {
            var str = '<td><input type="submit" value="<fmt:message key="label.unblock"/>" class="btn btn-outline-dark" id="unblock' + id + '"></td> <td><input type="submit" value="<fmt:message key="label.delete"/>" class="btn btn-outline-danger" id="delete' + id + '" ></td>';
            document.getElementById('buttonsC' + id).innerHTML = str;
            document.getElementById("unblock" + id).addEventListener("click", function () {
                unblockUser(id)
            }, false);
            document.getElementById("delete" + id).addEventListener("click", function () {
                deleteUser(id)
            }, false);
        }
    }

    function unblockUser(id) {
        var command = "unblock_user";

        var userObj = {
            "command": command,
            "userid": id
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
                var str = '<td><input type="submit" value="<fmt:message key="label.block"/>" class="btn btn-outline-dark" id="block' + id + '"></td> <td><input type="submit" value="<fmt:message key="label.delete"/>" class="btn btn-outline-danger" id="delete' + id + '" ></td>';
                document.getElementById('buttonsC' + id).innerHTML = str;
                document.getElementById("block" + id).addEventListener("click", function () {
                    blockUser(id)
                }, false);
                document.getElementById("delete" + id).addEventListener("click", function () {
                    deleteUser(id)
                }, false);

                if (data.length > 100) {
                    $("body").html(data);
                    location.replace("http://localhost:8080${pageContext.request.contextPath}/")
                }
            }
        });
    }

    function deleteUser(id) {

        var command = "delete_user";
        var userObj = {

            "command": command,
            "userid": id
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
                var str = '<td><fmt:message key="label.deleted"/></td>';
                document.getElementById('buttonsC' + id).innerHTML = str;
                if (data.length > 100) {
                    $("body").html(data);
                    location.replace("http://localhost:8080${pageContext.request.contextPath}/")
                }
            }
        });
    }

    function showUsers(jsonObj) {
        var section = document.getElementById("output");
        section.innerHTML = "";
        var table = document.createElement('table');
        table.setAttribute('class','table')
        var thead = document.createElement('thead');
        thead.setAttribute('class','thead-dark')
        thead.innerHTML = '<th scope="col">#</th> <th scope="col"><fmt:message key="label.nickname"/></th> <th scope="col"><fmt:message key="label.email"/></th><th></th><th></th>'
        var tbody = document.createElement('tbody');
        table.appendChild(thead);
        table.appendChild(tbody)
        section.appendChild(table);
        for (var i = 0; i < jsonObj.length; i++) {
            var myTr = document.createElement('tr');
            var myTd = document.createElement('td');
            var myTd2 = document.createElement('td');
            var myTd3 = document.createElement('td');
            var myTd8 = document.createElement('td');
            var myDiv = document.createElement('div');

            myTd.textContent = jsonObj[i].id
            myTd2.textContent = jsonObj[i].login
            myTd3.textContent = jsonObj[i].email
            myTd8.innerHTML = '<fmt:message key="label.deleted"/>'
            myDiv.id = 'buttonsC' + jsonObj[i].id
            myTr.appendChild(myTd);
            myTr.appendChild(myTd2);
            myTr.appendChild(myTd3);
            if (jsonObj[i].status === 3) {
                myDiv.appendChild(myTd8)
            }
            myTr.appendChild(myDiv);
            tbody.appendChild(myTr);

            var userId = jsonObj[i].id;
            if (jsonObj[i].status === 0 || jsonObj[i].status === 1) {
                initialize(userId, 1);
            } else if (jsonObj[i].status === 2) {
                initialize(userId, 2);
            }
        }
    }

    function accept(topId) {
        // var topId = document.getElementById('topid').value;
        var command = "accept_report"
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
                var list = document.getElementsByName(topId);
                i = 0;
                while (list.length > 0) {
                    console.log(list[i].parentElement)
                    list[i].parentElement.remove()
                }
                if (data.length > 100) {
                    $("body").html(data);
                    location.replace("http://localhost:8080${pageContext.request.contextPath}/")
                }
            }
        });

    }

    function decline(id) {
        //var topId = document.getElementById('topid').value;
        //var userId = document.getElementById('userid').value;
        var command = "decline_report"
        var userObj = {
            "command": command,
            "reportid": id
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
                document.getElementById('report' + id).remove();
                var list = document.getElementsByName(topId);
                i = 0;
                while (list.length > 0) {
                    console.log(list[i].parentElement)
                    list[i].parentElement.remove()
                }
                if (data.length > 100) {
                    $("body").html(data);
                    location.replace("http://localhost:8080${pageContext.request.contextPath}/")
                }

            }
        });


    }

    function visit(topId) {
        //var topId = document.getElementById('topid').value;
        var command = "to_top_page"
        var userObj = {
            "command": command,
            "topid": topId
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
            }
        });
    }
</script>
<html>
<jsp:include page="header.jsp"/>
<head>
    <title><fmt:message key="label.admin_panel"/></title>
</head>
<body>
<div style="margin-left: 5%;margin-top:10px">
<input type="text" id="login" name="login" class="form-control" aria-label="Default" aria-describedby="inputGroup-sizing-default" style="width: 250px; margin-bottom: 10px" value="" placeholder="<fmt:message key="label.nickname"/>"
       pattern="^(?=[A-Za-z])[A-Za-z\d]" maxlength="15">
<input type="submit" class="btn btn-outline-dark" name="submit" onclick="getUsers()" value="<fmt:message key="label.top_find"/>">

<input type="submit" class="btn btn-outline-danger" name="submit" value="<fmt:message key="label.reports"/>" onclick="getReports()">
<div id="output"></div>
</div>
</body>

</html>
