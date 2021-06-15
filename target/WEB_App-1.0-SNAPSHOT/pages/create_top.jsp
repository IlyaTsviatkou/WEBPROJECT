<%--
  Created by IntelliJ IDEA.
  User: ilyat
  Date: 26.04.2021
  Time: 00:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CREATING TOP</title>
</head>
<body>
<br/>
<form name="createTopForm" action="upload" method="post" enctype="multipart/form-data">
    <input type="hidden" name="command" value="create_top" />
    <input type="text" name="title" value="" placeholder="title" maxlength="45" required >
    <input type="text" name="description" value="" placeholder="description" maxlength="200" >
    <label for="file">Choose file to upload</label>
    <input type="file" id="file" name="imageName" >
    <br/>
    <input type="submit" name="submit" value="create">
</form>
</body>
</html>
