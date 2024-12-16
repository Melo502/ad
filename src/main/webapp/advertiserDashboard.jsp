<%--
  Created by IntelliJ IDEA.
  User: 86182
  Date: 2024/12/16
  Time: 12:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>广告主面板</title>
    <link rel="stylesheet" type="text/css" href="styles/style3.css">
</head>
<body>

<div class="header">
    <h2>广告主管理面板</h2>
</div>

<div class="ad-list">
    <h3>我的广告</h3>
    <table border="1">
        <thead>
        <tr>
            <th>广告标题</th>
            <th>广告类别</th>
            <th>广告状态</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="ad" items="${ads}">
            <tr>
                <td>${ad.title}</td>
                <td>${ad.adCategory}</td>
                <td>${ad.status}</td>
                <td>
                    <a href="adDetail.jsp?id=${ad.id}">查看详细信息</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="newbutton">
    <a href="addAd.jsp" class="button">新增广告</a>
</div>
</body>
</html>

