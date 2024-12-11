<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>新浪网</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
</head>
<body>

<!-- 导航栏 -->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container">
        <a class="navbar-brand" href="#">新浪网</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="#">首页</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">新闻</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">体育</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">娱乐</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">科技</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">关于</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- 主内容区域 -->
<div class="container mt-4">
    <div class="row">
        <!-- 新闻内容区域 -->
        <div class="col-md-8">
            <div class="news-header">
                <h1>最新新闻</h1>
            </div>

            <c:forEach var="news" items="${newsList}">
                <div class="news-item mb-4 p-3 border rounded shadow-sm">
                    <div class="news-title h4">${news.title}</div>
                    <div class="news-content mt-3">${news.content}</div>
                    <div class="news-footer mt-3 text-muted text-end">${news.date}</div>
                </div>
            </c:forEach>
        </div>

        <!-- 右侧广告位 -->
        <div class="col-md-4">
            <iframe src="https://www.bilibili.com" title="广告位2" class="w-100" style="height: 100vh; border: none;"></iframe>
        </div>
    </div>
</div>

<!-- 页脚 -->
<footer class="bg-light text-center py-3 mt-4">
    <p>&copy; 2024 新浪网 - 版权所有</p>
</footer>

<!-- 引入Bootstrap的JavaScript文件 -->
<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>

</body>
</html>
