<%--
  Created by IntelliJ IDEA.
  User: pc
  Date: 2018/10/16
  Time: 13:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    function writeToScreen(message) {
        var content = ${"#content"}.val();
        alert(content);
    }
</script>
<html>
<head>
    <title>Title</title>
</head>
<body>
<textarea id="content">dasd</textarea>
<button onclick="writeToScreen('fuck')">anniu</button>
</body>
</html>
