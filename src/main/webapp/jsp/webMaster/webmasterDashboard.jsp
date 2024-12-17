<%--
  Created by IntelliJ IDEA.
  User: 86182
  Date: 2024/12/16
  Time: 12:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>网站长管理面板</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style6.css">
</head>
<body>

<div class="header">
    <h2>网站长管理面板</h2>

</div>

<!-- 广告列表展示 -->
<div class="ad-list">
    <h3>发布的广告</h3>
    <table border="1">
        <thead>
        <tr>
            <th>广告标题</th>
            <th>广告类别</th>
            <th>投放状态</th>
            <th>点击数</th>
            <th>展示数</th>
            <th>花费</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="ad" items="${ads}">
            <tr>
                <td>${ad.title}</td>
                <td>${ad.adCategory}</td>
                <td>${ad.status}</td>
                <td>${ad.clickCount}</td>
                <td>${ad.impressionCount}</td>
                <td>${ad.totalSpent}</td>
                <td>
                    <a href="adDetails.jsp?id=${ad.id}">查看投放情况</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- 上传API接口 -->
<div class="api-upload">
    <h3>上传广告发布API接口</h3>
    <form action="uploadApiServlet" method="POST" enctype="multipart/form-data">
        <label for="apiFile">选择API接口文件：</label>
        <input type="file" id="apiFile" name="apiFile" accept=".zip,.tar,.tar.gz,.json" required><br>
        <input type="submit" value="上传API接口">
    </form>
</div>
<div class="newbutton">
    <a href="${pageContext.request.contextPath}/jsp/other/login.jsp" class="button">退出登录</a>
</div>

</body>
</html>

