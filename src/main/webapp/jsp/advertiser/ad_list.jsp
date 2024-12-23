<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <title>广告管理</title>
  <!-- 引入Bootstrap CSS -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
  <!-- 引入Bootstrap Icons（用于图标） -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
  <style>
    body {
      display: flex;
      min-height: 100vh;
      background-color: #ffffff; /* 修改背景色为白色 */
    }
    .sidebar {
      min-width: 250px;
      max-width: 250px;
      background-color: #ffffff; /* 修改侧边栏背景色为白色 */
      color: #343a40; /* 修改侧边栏文本颜色为深色 */
      padding: 20px 0;
      border-right: 1px solid #dee2e6; /* 添加侧边栏右边框以区分 */
    }
    .sidebar h3 {
      color: #343a40; /* 修改标题颜色为深色 */
      margin-bottom: 30px;
    }
    .sidebar .nav-link {
      color: #343a40; /* 修改导航链接颜色为深色 */
      padding: 15px 20px;
    }
    .sidebar .nav-link:hover {
      background-color: #f8f9fa; /* 修改悬停背景色为浅灰色 */
      color: #343a40;
    }
    .main-content {
      flex: 1;
      padding: 20px;
    }
    .table thead {
      background-color: #f8f9fa; /* 修改表头背景色为浅灰色 */
      color: #343a40; /* 修改表头文本颜色为深色 */
    }
    .badge-status-active {
      background-color: #28a745; /* 绿色 */
      color: white;
    }
    .badge-status-inactive {
      background-color: #dc3545; /* 红色 */
      color: white;
    }
    .badge-type {
      background-color: #17a2b8; /* 青色 */
      color: white;
    }
    .btn-group .btn {
      margin-right: 5px;
    }
    footer {
      text-align: center;
      padding: 15px 0;
      background-color: #f8f9fa; /* 修改页脚背景色为浅灰色 */
      color: #343a40; /* 修改页脚文本颜色为深色 */
      position: fixed;
      width: 100%;
      bottom: 0;
      border-top: 1px solid #dee2e6; /* 添加页脚上边框 */
    }
    /* 响应式调整 */
    @media (max-width: 768px) {
      body {
        flex-direction: column;
      }
      .sidebar {
        max-width: 100%;
        min-width: 100%;
        border-right: none; /* 移除侧边栏右边框 */
        border-bottom: 1px solid #dee2e6; /* 添加侧边栏下边框 */
      }
      footer {
        position: static;
      }
    }
  </style>
</head>
<body>
<!-- 侧边导航栏 -->
<div class="sidebar">
  <h3 class="text-center">广告主管理</h3>
  <ul class="nav flex-column">
    <li class="nav-item">
      <a class="nav-link" href="advertiserServlet.do?method=createAd">
        <i class="bi bi-plus-circle"></i> 新建广告
      </a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="advertiserServlet.do?method=showAdPlacementPage">
        <i class="bi bi-broadcast"></i> 投放广告
      </a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="advertiserServlet.do?method=viewAdRecords">
        <i class="bi bi-list"></i> 点击记录
      </a>
    </li>
    <li class="nav-item">
      <a class="nav-link active" href="advertiserServlet.do?method=adAnalysis">
        <i class="bi bi-bar-chart-line"></i> 投放效果
      </a>
    </li>
  </ul>
</div>

<!-- 主内容区域 -->
<div class="main-content">
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
  <div class="container-fluid">
    <!-- 页面标题 -->
    <div class="row mt-4">
      <div class="col">
        <h1 class="text-center">广告主 你好！你的广告都在这里了 ^_^</h1>
      </div>
    </div>

    <!-- 错误消息显示 -->
    <c:if test="${not empty error}">
      <div class="row">
        <div class="col">
          <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <i class="bi bi-exclamation-triangle-fill me-2"></i>
              ${error}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="关闭"></button>
          </div>
        </div>
      </div>
    </c:if>

    <!-- 搜索框 -->
    <div class="row">
      <div class="col-12 mb-3">
        <form action="advertiserServlet.do" method="get" class="d-flex">
          <!-- 隐藏域用于指定方法为 searchAds -->
          <input type="hidden" name="method" value="searchAds">

          <!-- 广告标题搜索输入框，保留用户输入的值 -->
          <input type="text" name="searchQuery" class="form-control me-2" placeholder="请输入广告标题搜索" value="${param.searchQuery}" />

          <!-- 搜索按钮 -->
          <button type="submit" class="btn btn-primary me-2">
            <i class="bi bi-search"></i>
          </button>

          <!-- 重置按钮，使用 <a> 标签实现 -->
          <a href="advertiserServlet.do?method=viewAd" class="btn btn-secondary">
            <i class="bi bi-arrow-counterclockwise"></i>
          </a>
        </form>
      </div>
    </div>


    <!-- 广告列表表格 -->
    <div class="row">
      <div class="col">
        <div class="table-responsive">
          <table class="table table-striped table-bordered table-hover">
            <thead>
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
                <td>
                  <c:choose>
                    <c:when test="${ad.status == '活跃'}">
                      <span class="badge badge-status-active">活跃</span>
                    </c:when>
                    <c:otherwise>
                      <span class="badge badge-status-inactive">停用</span>
                    </c:otherwise>
                  </c:choose>
                </td>
                <td>
                  <span class="badge badge-type">${ad.typeKeyWords}</span>
                </td>
                <td>
                  <!-- 按钮组开始 -->
                  <div class="btn-group" role="group" aria-label="操作按钮">
                    <!-- 查看详情按钮 -->
                    <a href="advertiserServlet.do?method=viewAdDetail&id=${ad.id}" class="btn btn-info btn-sm" title="查看详情">
                      <i class="bi bi-eye"></i>
                    </a>
                    <!-- 修改按钮 -->
                    <a href="advertiserServlet.do?method=editAd&id=${ad.id}" class="btn btn-warning btn-sm" title="修改广告">
                      <i class="bi bi-pencil-square"></i>
                    </a>
                    <!-- 删除按钮 -->
                    <form action="advertiserServlet.do" method="post" style="margin: 0;">
                      <input type="hidden" name="method" value="deleteAd">
                      <input type="hidden" name="id" value="${ad.id}">
                      <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('确定要删除这条广告吗？');" title="删除广告">
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
    </div>
  </div>
</div>

<!-- 页脚 -->
<footer>
  <p>&copy; 2024 广告管理系统. 版权所有.</p>
</footer>

<!-- 引入Bootstrap JS -->
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</body>
</html>
