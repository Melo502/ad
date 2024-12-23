<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查看广告详情</title>
    <!-- 引入Bootstrap CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
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
    <!-- 页面标题 -->
    <div class="row mt-4">
        <div class="col">
            <h1 class="text-center">广告详情</h1>
        </div>
    </div>

    <!-- 错误消息显示 -->
    <c:if test="${not empty error}">
        <div class="row">
            <div class="col">
                <div class="alert alert-danger" role="alert">
                        ${error}
                </div>
            </div>
        </div>
    </c:if>

    <!-- 广告详情内容 -->
    <c:if test="${not empty ad}">
        <div class="row mt-3">
            <div class="col-md-6">
                <h3>${ad.title}</h3>
                <p><strong>描述:</strong> ${ad.description}</p>
                <p><strong>状态:</strong> ${ad.status}</p>
                <p><strong>链接:</strong> <a href="${ad.url}" target="_blank">${ad.url}</a></p>
                <p><strong>类型关键字:</strong> ${ad.typeKeyWords}</p>
                <p><strong>类型代号:</strong> ${ad.typeId}</p>
                <p><strong>广告主ID:</strong> ${ad.advertiserId}</p>
                <p><strong>单次点击的收益:</strong> ${ad.price}</p>
            </div>
            <div class="col-md-6">
                <img src="/${ad.imageUrl}" alt="广告图片" class="img-fluid">
            </div>
        </div>
        <div class="row mt-4">
            <div class="col text-center">
                <a href="advertiserServlet.do?method=editAd&id=${ad.id}" class="btn btn-warning me-2">
                    <i class="bi bi-pencil-square"></i> 修改
                </a>
                <form action="advertiserServlet.do" method="post" style="display: inline;">
                    <input type="hidden" name="method" value="deleteAd">
                    <input type="hidden" name="id" value="${ad.id}">
                    <button type="submit" class="btn btn-danger" onclick="return confirm('确定要删除这条广告吗？');">
                        <i class="bi bi-trash"></i> 删除
                    </button>
                </form>
                <a href="advertiserServlet.do?method=viewAdRecords" class="btn btn-secondary ms-2">返回列表</a>
            </div>
        </div>
    </c:if>

    <c:if test="${empty ad}">
        <div class="row mt-3">
            <div class="col">
                <p class="text-center text-danger">广告不存在或已被删除。</p>
                <div class="text-center">
                    <a href="advertiserServlet.do?method=viewAdRecords" class="btn btn-primary">返回列表</a>
                </div>
            </div>
        </div>
    </c:if>
</div>

<!-- 引入Bootstrap的JavaScript依赖 -->
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<!-- 引入Bootstrap Icons -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</body>
</html>
