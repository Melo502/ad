<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>添加用户 - 广告管理系统</title>
    <!-- 引入Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <!-- 引入Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2>添加用户</h2>
    <form action="${pageContext.request.contextPath}/adminServlet.do?method=addUser" method="post" class="mt-4">
        <div class="mb-3">
            <label for="username" class="form-label">用户名 <span class="text-danger">*</span></label>
            <input type="text" class="form-control" id="username" name="username" required>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">密码 <span class="text-danger">*</span></label>
            <input type="password" class="form-control" id="password" name="password" required>
        </div>
        <div class="mb-3">
            <label for="userType" class="form-label">用户类型 <span class="text-danger">*</span></label>
            <select class="form-select" id="userType" name="userType" required>
                <option value="">请选择用户类型</option>
                <option value="广告主">广告主</option>
                <option value="网站长">网站长</option>
                <option value="管理员">管理员</option>
            </select>
        </div>
        <button type="submit" class="btn btn-success"><i class="bi bi-check-circle me-2"></i> 添加</button>
        <a href="${pageContext.request.contextPath}/adminServlet.do?method=listUser" class="btn btn-secondary ms-2"><i class="bi bi-arrow-left me-2"></i> 返回</a>
    </form>
</div>

<!-- 引入Bootstrap JS和依赖 -->
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</body>
</html>
