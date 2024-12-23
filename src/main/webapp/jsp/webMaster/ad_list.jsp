<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>广告列表 - 网站长</title>
    <!-- 引入Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- 引入Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
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
                            下午好！<strong>${webmaster.username}</strong>, 欢迎你！
                        </span>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/logout.do?method=webmasterLogout">退出</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- 主内容 -->
<div class="container mt-5">
    <h2>广告列表</h2>
    <!-- 搜索表单 -->
    <form class="row g-3 mb-4" method="get" action="${pageContext.request.contextPath}/webmasterServlet.do">
        <input type="hidden" name="action" value="ad_list">
        <div class="col-md-6">
            <label for="searchTitle" class="form-label">搜索广告标题</label>
            <input type="text" class="form-control" id="searchTitle" name="searchTitle" value="${searchTitle}" placeholder="输入广告标题">
        </div>
        <div class="col-md-2 align-self-end">
            <button type="submit" class="btn btn-primary"><i class="bi bi-search me-2"></i> 搜索</button>
        </div>
    </form>

    <!-- 导航按钮 -->
    <a href="${pageContext.request.contextPath}/webmasterServlet.do?action=ad_analysis" class="btn btn-primary mb-3">
        <i class="bi bi-bar-chart-fill me-2"></i> 广告分析
    </a>
    <a href="${pageContext.request.contextPath}/webmasterServlet.do?action=ad_records" class="btn btn-secondary mb-3 ms-2">
        <i class="bi bi-graph-up me-2"></i> 广告点击记录
    </a>
    <a href="${pageContext.request.contextPath}/jsp/webMaster/ad_placeAPI.jsp" class="btn btn-success mb-3 ms-2">
        <i class="bi bi-graph-up me-2"></i> 广告接收API
    </a>

    <!-- 展示广告列表 -->
    <c:if test="${not empty ads}">
        <table class="table table-striped table-bordered">
            <thead class="table-dark">
            <tr>
                <th>广告ID</th>
                <th>标题</th>
                <th>描述</th>
                <th>图片</th>
                <th>链接</th>
                <th>类型关键词</th>
                <th>状态</th>
                <th>价格</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="ad" items="${ads}">
                <tr>
                    <td>${ad.id}</td>
                    <td>${ad.title}</td>
                    <td>${ad.description}</td>
                    <td>
                        <img src="/${ad.imageUrl}" alt="${ad.title}" width="100">
                    </td>
                    <td>
                        <a href="${ad.url}" target="_blank">${ad.url}</a>
                    </td>
                    <td>${ad.typeKeyWords}</td>
                    <td>${ad.status}</td>
                    <td>$${ad.price}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${empty ads}">
        <div class="alert alert-info" role="alert">
            暂无广告信息。
        </div>
    </c:if>
</div>

<!-- 引入Bootstrap JS和依赖 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
