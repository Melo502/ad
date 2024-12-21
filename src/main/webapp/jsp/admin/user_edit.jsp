<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>编辑用户 - 广告管理系统</title>
    <!-- 引入Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <!-- 引入Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2>编辑用户</h2>
    <form action="${pageContext.request.contextPath}/adminServlet.do?method=editUser" method="post" class="mt-4">
        <input type="hidden" name="id" value="${user.id}">
        <div class="mb-3">
            <label for="username" class="form-label">用户名 <span class="text-danger">*</span></label>
            <input type="text" class="form-control" id="username" name="username" value="${user.username}" required>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">密码 <span class="text-danger">*</span></label>
            <input type="password" class="form-control" id="password" name="password" value="${user.password}" required>
        </div>
        <div class="mb-3">
            <label for="userType" class="form-label">用户类型 <span class="text-danger">*</span></label>
            <select class="form-select" id="userType" name="userType" required>
                <option value="">请选择用户类型</option>
                <option value="广告主" <c:if test="${user.userType == '广告主'}">selected</c:if>>广告主</option>
                <option value="网站长" <c:if test="${user.userType == '网站长'}">selected</c:if>>网站长</option>
                <option value="管理员" <c:if test="${user.userType == '管理员'}">selected</c:if>>管理员</option>
            </select>
        </div>
        <button type="submit" class="btn btn-primary"><i class="bi bi-pencil-square me-2"></i> 更新</button>
        <a href="${pageContext.request.contextPath}/adminServlet.do?method=listUser" class="btn btn-secondary ms-2"><i class="bi bi-arrow-left me-2"></i> 返回</a>
    </form>
</div>

<!-- 引入Bootstrap JS和依赖 -->
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</body>
</html>
