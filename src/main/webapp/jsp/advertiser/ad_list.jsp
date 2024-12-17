<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>广告管理</title>
  <!-- 引入Bootstrap CSS -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
  <style>
    img {
      max-width: 100px;
      height: auto;
    }
  </style>
</head>
<body>
<div class="container">
  <!-- 页面标题 -->
  <div class="row mt-4">
    <div class="col">
      <h1 class="text-center">广告列表</h1>
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

  <!-- 广告列表表格 -->
  <div class="row">
    <div class="col">
      <table class="table table-striped table-bordered table-hover mt-3">
        <thead class="thead-dark">
        <tr>
          <th scope="col">广告标题</th>
          <th scope="col">广告描述</th>
          <th scope="col">广告状态</th>
          <th scope="col">广告类型</th>
          <th scope="col">操作</th> <!-- 新增“操作”列 -->
        </tr>
        </thead>
        <tbody>
        <!-- 使用JSTL标签遍历广告列表并显示 -->
        <c:forEach var="ad" items="${adList}">
          <tr>
            <td>${ad.title}</td>
            <td>${ad.description}</td>
            <td>${ad.status}</td>
            <td>${ad.typeKeyWords}</td>
            <td>
              <!-- 按钮组开始 -->
              <div class="btn-group" role="group" aria-label="操作按钮">
                <!-- 查看详情按钮 -->
                <a href="advertiserServlet.do?method=viewAdDetail&id=${ad.id}" class="btn btn-info btn-sm">
                  <i class="bi bi-eye"></i>
                </a>
                <!-- 修改按钮 -->
                <a href="advertiserServlet.do?method=editAd&id=${ad.id}" class="btn btn-warning btn-sm">
                  <i class="bi bi-pencil-square"></i>
                </a>
                <!-- 删除按钮 -->
                <form action="advertiserServlet.do" method="post" style="margin: 0;">
                  <input type="hidden" name="method" value="deleteAd">
                  <input type="hidden" name="id" value="${ad.id}">
                  <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('确定要删除这条广告吗？');">
                    <i class="bi bi-trash"></i>
                  </button>
                </form>
              </div>
              <!-- 按钮组结束 -->
            </td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>
  </div>

  <!-- 创建广告按钮 -->
  <div class="row mt-4">
    <div class="col text-center">
      <a href="advertiserServlet.do?method=createAd" class="btn btn-primary btn-lg">创建广告</a>
    </div>
  </div>

  <!-- 投放广告按钮 -->
  <div class="row mt-4">
    <div class="col text-center">
      <a href="advertiserServlet.do?method=showAdPlacementPage" class="btn btn-success btn-lg">投放广告</a>
    </div>
  </div>

</div>


<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<!-- 引入Bootstrap Icons（用于图标） -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</body>
</html>
