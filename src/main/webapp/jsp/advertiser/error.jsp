<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>错误</title>
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
<h1>发生错误</h1>
<p style="color:red;">
    <%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "未知错误" %>
</p>
<a href="advertiserServlet.do?method=viewAd">返回首页</a>
</body>
</html>
