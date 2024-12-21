<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>错误 - 广告管理系统</title>
    <!-- 引入Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <!-- 引入Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <div class="alert alert-danger" role="alert">
        <h4 class="alert-heading">出错了！</h4>
        <p>${error}</p>
        <hr>
        <a href="javascript:history.back()" class="btn btn-secondary"><i class="bi bi-arrow-left me-2"></i> 返回</a>
        <a href="${pageContext.request.contextPath}/jsp/admin/adminDashboard.jsp" class="btn btn-primary"><i class="bi bi-house-fill me-2"></i> 首页</a>
    </div>
</div>

<!-- 引入Bootstrap JS和依赖 -->
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</body>
</html>
