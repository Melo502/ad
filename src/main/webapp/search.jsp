<%--
  Created by IntelliJ IDEA.
  User: 86182
  Date: 2024/12/20
  Time: 00:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>搜索结果</title>
</head>
<body>
<h1>搜索结果</h1>
<c:forEach var="news" items="${searchResults}">
    <p>${news.title} - ${news.publishDate}</p>
    <a href="/news?id=${news.id}">查看</a>
</c:forEach>
</body>
</html>

