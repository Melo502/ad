<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>错误</title>
</head>
<body>
<h1>发生错误</h1>
<p style="color:red;">
    <%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "未知错误" %>
</p>
<a href="advertiserServlet.do?method=viewAd">返回首页</a>
</body>
</html>
