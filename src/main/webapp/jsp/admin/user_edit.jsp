<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>编辑用户</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script>
        function toggleWebsiteSelection() {
            var userType = document.getElementById("userType").value;
            var websiteDiv = document.getElementById("websiteDiv");
            if(userType === "网站长"){
                websiteDiv.style.display = "block";
            } else {
                websiteDiv.style.display = "none";
            }
        }

        window.onload = function(){
            toggleWebsiteSelection();
        }
    </script>
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
                            下午好！<strong>${admin.username}</strong>, 欢迎你！
                        </span>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/logout.do?method=adminLogout">退出</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="container mt-5">
    <h2>编辑用户</h2>
    <form action="${pageContext.request.contextPath}/adminServlet.do?method=editUser" method="post">
        <input type="hidden" name="id" value="${user.id}">
        <div class="mb-3">
            <label for="username" class="form-label">用户名</label>
            <input type="text" class="form-control" id="username" name="username" value="${user.username}" required>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">密码</label>
            <input type="password" class="form-control" id="password" name="password" value="${user.password}" required>
        </div>
        <div class="mb-3">
            <label for="userType" class="form-label">用户类型</label>
            <select class="form-select" id="userType" name="userType" onchange="toggleWebsiteSelection()" required>
                <option value="">请选择用户类型</option>
                <option value="管理员" <c:if test="${user.userType == '管理员'}">selected</c:if>>管理员</option>
                <option value="网站长" <c:if test="${user.userType == '网站长'}">selected</c:if>>网站长</option>
                <option value="广告主" <c:if test="${user.userType == '广告主'}">selected</c:if>>广告主</option>
            </select>
        </div>
        <div class="mb-3" id="websiteDiv" style="display: none;">
            <label for="websiteName" class="form-label">管理的网站</label>
            <select class="form-select" id="websiteName" name="websiteName">
                <option value="">请选择网站</option>
                <c:forEach var="site" items="${requestScope.websites}">
                    <option value="${site}" <c:if test="${user.webSiteName == site}">selected</c:if>>${site}</option>
                </c:forEach>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">更新用户</button>
        <a href="adminServlet.do?method=listUser" class="btn btn-secondary">取消</a>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
