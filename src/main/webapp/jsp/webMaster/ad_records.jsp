<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>广告点击记录 - 网站长</title>
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
    <h2>广告点击记录</h2>
    <!-- 筛选表单 -->
    <form class="row g-3 mb-4" method="get" action="${pageContext.request.contextPath}/webmasterServlet.do">
        <input type="hidden" name="action" value="ad_records">
        <div class="col-md-2">
            <label for="advertiserId" class="form-label">广告主ID</label>
            <input type="number" class="form-control" id="advertiserId" name="advertiserId" value="${advertiserId}" placeholder="输入广告主ID">
        </div>
        <div class="col-md-2">
            <label for="startDate" class="form-label">开始日期</label>
            <input type="date" class="form-control" id="startDate" name="startDate" value="${startDate}">
        </div>
        <div class="col-md-2">
            <label for="endDate" class="form-label">结束日期</label>
            <input type="date" class="form-control" id="endDate" name="endDate" value="${endDate}">
        </div>
        <div class="col-md-2">
            <label for="minIncome" class="form-label">最小收益</label>
            <input type="number" step="1" class="form-control" id="minIncome" name="minIncome" value="${minIncome}" placeholder="输入最小收益">
        </div>
        <div class="col-md-2">
            <label for="maxIncome" class="form-label">最大收益</label>
            <input type="number" step="1" class="form-control" id="maxIncome" name="maxIncome" value="${maxIncome}" placeholder="输入最大收益">
        </div>
        <div class="col-md-2 align-self-end">
            <button type="submit" class="btn btn-primary"><i class="bi bi-filter-circle me-2"></i> 筛选</button>
        </div>
    </form>

    <!-- 导航按钮 -->
    <a href="${pageContext.request.contextPath}/webmasterServlet.do?action=ad_analysis" class="btn btn-primary mb-3">
        <i class="bi bi-bar-chart-fill me-2"></i> 广告分析
    </a>
    <a href="${pageContext.request.contextPath}/webmasterServlet.do?action=ad_list" class="btn btn-secondary mb-3 ms-2">
        <i class="bi bi-list-ul me-2"></i> 广告列表
    </a>

    <!-- 展示广告点击记录 -->
    <c:if test="${not empty records}">
        <table class="table table-striped table-bordered">
            <thead class="table-dark">
            <tr>
                <th>记录ID</th>
                <th>广告主ID</th>
                <th>广告ID</th>
                <th>收益</th>
                <th>点击时间</th>
                <th>投放网站</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="record" items="${records}">
                <tr>
                    <td>${record.id}</td>
                    <td>${advertiserId}</td>
                    <td>${record.adId}</td>
                    <td>$${record.income}</td>
                    <td><fmt:formatDate value="${record.clickTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td>${record.website}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${empty records}">
        <div class="alert alert-info" role="alert">
            暂无广告点击记录。
        </div>
    </c:if>
</div>

<!-- 引入Bootstrap JS和依赖 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
