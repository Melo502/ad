<%--
  Created by IntelliJ IDEA.
  User: 86183
  Date: 2024/12/15
  Time: 15:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>广告投放</title>
    <!-- 引入Bootstrap CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/placeAd.css">
</head>
<body>
<!-- 导航栏 -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">广告管理系统</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item me-3">
                        <span class="navbar-text">
                            下午好！<strong>${advertiser.username}</strong>, 欢迎你！
                        </span>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/logout.do?method=advertiserLogout">退出</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="container">
    <h1 class="text-center mt-4">广告投放</h1>

    <!-- 选择投放广告的目标网站 -->
    <div class="row mt-4">
        <div class="col-md-8 offset-md-2">
            <form action="advertiserServlet.do" method="post">
                <input type="hidden" name="method" value="placeAd">

                <!-- 目标网站选择 -->
                <div class="form-group">
                    <label for="website">目标网站</label>
                    <select id="website" name="website" class="form-control" required>
                        <option value="" disabled selected>请选择目标网站</option>
                        <option value= "${applicationScope.NEWS_SITE}">新闻</option>
                        <option value="${applicationScope.BOOK_SITE}">书城</option>
                    </select>
                </div>

                <!-- 显示选中的网站地址 -->
                <div class="form-group mt-2">
                    <label>网站地址:</label>
                    <p id="websiteAddress" class="form-control-plaintext"></p>
                </div>

                <!-- 广告选择区域 -->
                <div class="form-group mt-4">
                    <label>选择广告</label>
                    <table class="table table-striped table-hover">
                        <thead class="thead-dark">
                        <tr>
                            <th scope="col"><input type="checkbox" id="selectAll"></th>
                            <th scope="col">标题</th>
                            <th scope="col">状态</th>
                            <th scope="col">价格</th>
                            <th scope="col">类型</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="ad" items="${adList}" varStatus="status">
                            <tr>
                                <td><input type="checkbox" name="adIds" value="${ad.id}"></td>
                                <td>${ad.title}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${ad.status == '活跃'}">
                                            <span class="badge badge-active">活跃</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-inactive">停用</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>$${ad.price}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${status.index % 4 == 0}">
                                            <span class="badge badge-primary">${ad.typeKeyWords}</span>
                                        </c:when>
                                        <c:when test="${status.index % 4 == 1}">
                                            <span class="badge badge-success">${ad.typeKeyWords}</span>
                                        </c:when>
                                        <c:when test="${status.index % 4 == 2}">
                                            <span class="badge badge-warning">${ad.typeKeyWords}</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-info">${ad.typeKeyWords}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>

                <div class="text-center">
                    <button type="submit" class="btn btn-primary btn-lg mt-3">投放广告</button>
                    <a href="advertiserServlet.do?method=viewAd" class="btn btn-primary btn-lg mt-3">取消</a>
                </div>
            </form>

            <!-- 显示消息 -->
            <div id="message" class="mt-3">
                <c:if test="${not empty message}">
                    <div class="alert alert-info">${message}</div>
                </c:if>
            </div>
        </div>
    </div>
</div>

<!-- 引入Bootstrap JS -->
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/selectPlaceAd.js"></script>
</body>
</html>
