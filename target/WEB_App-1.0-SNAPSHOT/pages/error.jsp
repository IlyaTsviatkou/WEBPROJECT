<%@ page language="java" contentType="text/html; charset=US-ASCII"
         pageEncoding="US-ASCII" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
    <title>Error Page</title>
</head>
<body>
Hi There, error code is <%=response.getStatus() %><br>
Please go to <a href="/WEB_App_war_exploded/">home page</a>
</body>
</html>