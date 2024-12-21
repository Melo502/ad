<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>创建广告</title>
  <!-- 引入Bootstrap CSS -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
</head>
<body>
<div class="container">
  <!-- 页面标题 -->
  <div class="row mt-4">
    <div class="col">
      <h1 class="text-center">创建广告</h1>
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

  <!-- 创建广告表单 -->
  <div class="row">
    <div class="col-md-8 offset-md-2">
      <form action="advertiserServlet.do" method="post" enctype="multipart/form-data">
        <input type="hidden" name="method" value="doCreateAd">
        <!-- 广告标题 -->
        <div class="mb-3">
          <label for="title" class="form-label">广告标题</label>
          <input type="text" class="form-control" id="title" name="title" required>
        </div>

        <!-- 广告描述 -->
        <div class="mb-3">
          <label for="description" class="form-label">广告描述</label>
          <textarea class="form-control" id="description" name="description" rows="3" required></textarea>
        </div>

        <!-- 广告链接 -->
        <div class="mb-3">
          <label for="url" class="form-label">广告链接</label>
          <input type="url" class="form-control" id="url" name="url" required>
        </div>

        <!-- 广告类型 -->
        <div class="mb-3">
          <label for="typeId" class="form-label">广告类型</label>
          <select class="form-select" id="typeId" name="typeId" required>
            <option value="">请选择广告类型</option>
            <c:forEach var="type" items="${typeList}">
              <option value="${type.id}">${type.typeName}</option>
            </c:forEach>
          </select>
        </div>

        <!-- 广告类型关键字 -->
        <div class="mb-3">
          <label for="typeKeyWords" class="form-label">广告类型关键字</label>
          <input type="text" class="form-control" id="typeKeyWords" name="typeKeyWords" required>
          <small class="form-text text-muted">多个关键字请用逗号分隔。</small>
        </div>


        <!-- 广告图片 -->
        <div class="mb-3">
          <label for="image" class="form-label">广告图片</label>
          <input class="form-control" type="file" id="image" name="image" accept="image/*" required>
        </div>

        <div class="mb-3">
          <label for="price" class="form-label">广告单次点击的收益</label>
          <input  class="form-control" id="price" name="price" required>
        </div>

        <!-- 提交按钮 -->
        <div class="text-center">
          <button type="submit" class="btn btn-primary">创建广告</button>
          <a href="advertiserServlet.do?method=viewAd" class="btn btn-secondary">取消</a>
        </div>
      </form>
    </div>
  </div>
</div>

<!-- 引入Bootstrap的JavaScript依赖 -->
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</body>
</html>
