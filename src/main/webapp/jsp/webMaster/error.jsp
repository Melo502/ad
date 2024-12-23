<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>错误 - 广告管理系统</title>
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
    <div class="alert alert-danger" role="alert">
        <h4 class="alert-heading">出错了！</h4>
        <p>${error}</p>
        <hr>
        <a href="javascript:history.back()" class="btn btn-secondary">
            <i class="bi bi-arrow-left me-2"></i> 返回
        </a>
        <a href="${pageContext.request.contextPath}/webmasterServlet.do?action=ad_list " class="btn btn-primary">
            <i class="bi bi-house-fill me-2"></i> 首页
        </a>
    </div>
</div>

<!-- 引入Bootstrap JS和依赖 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
