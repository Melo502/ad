<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <title>用户列表 - 广告管理系统</title>
  <!-- 引入Bootstrap CSS -->
  <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
  <!-- 引入Bootstrap Icons -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
  <div class="d-flex justify-content-between align-items-center mb-4">
    <h2>用户列表</h2>
    <a href="${pageContext.request.contextPath}/adminServlet.do?method=showAddUser" class="btn btn-primary">
      <i class="bi bi-plus-circle me-2"></i> 添加用户
    </a>
  </div>
  <table class="table table-striped table-bordered">
    <thead class="table">
    <tr>
      <th>用户ID</th>
      <th>用户名</th>
      <th>用户类型</th>
      <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${userList}">
      <tr>
        <td>${user.id}</td>
        <td>${user.username}</td>
        <td>${user.userType}</td>
        <td>
          <a href="${pageContext.request.contextPath}/adminServlet.do?method=showEditUser&id=${user.id}" class="btn btn-sm btn-warning me-2">
            <i class="bi bi-pencil-square"></i> 编辑
          </a>
          <a href="${pageContext.request.contextPath}/adminServlet.do?method=deleteUser&id=${user.id}" class="btn btn-sm btn-danger"
             onclick="return confirm('确定要删除该用户吗？');">
            <i class="bi bi-trash-fill"></i> 删除
          </a>
        </td>
      </tr>
    </c:forEach>
    <c:if test="${empty userList}">
      <tr>
        <td colspan="4" class="text-center">暂无用户信息。</td>
      </tr>
    </c:if>
    </tbody>
  </table>
  <a href="${pageContext.request.contextPath}/jsp/admin/adminDashboard.jsp" class="btn btn-secondary btn-lg">
    <i class="bi bi-arrow-left-circle-fill me-2"></i>返回控制面板
  </a>
</div>

<!-- 引入Bootstrap JS和依赖 -->
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</body>
</html>
