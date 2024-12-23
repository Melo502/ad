<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>查看投放效果</title>
  <!-- 引入Bootstrap CSS -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
  <!-- 引入Bootstrap Icons（如果需要） -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
  <style>
    body {
      display: flex;
      min-height: 100vh;
      background-color: #ffffff; /* 白色背景 */
    }
    .content {
      flex: 1;
      padding: 20px;
    }
  </style>
</head>
<body>

<!-- 主内容区域 -->
<div class="content">
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
  <h1 class="text-center mt-4">广告点击记录</h1>

  <!-- 显示消息（成功或错误） -->
  <div id="message" class="mt-3">
    <c:if test="${not empty message}">
      <div class="alert alert-success">${message}</div>
    </c:if>
    <c:if test="${not empty error}">
      <div class="alert alert-danger">${error}</div>
    </c:if>
  </div>

  <!-- 筛选表单 -->
    <!-- 过滤表单 -->
    <div class="card mb-4">
      <div class="card-header">
        <h5 class="mb-0">过滤条件</h5>
      </div>
      <div class="card-body">
  <div class="row mt-4">
    <div class="col-md-12">
      <form method="get" action="advertiserServlet.do" class="form-inline">
        <!-- 隐藏域用于指定方法，确保与后端 Servlet 的处理方法一致 -->
        <input type="hidden" name="method" value="viewAdRecordsSelect">

        <div class="form-row align-items-center">
          <!-- 时间筛选 -->
          <div class="col-auto">
            <label for="startDate" class="sr-only">开始时间</label>
            <input type="date" class="form-control mb-2" id="startDate" name="startDate" placeholder="开始时间" value="${param.startDate}">
          </div>
          <div class="col-auto">
            <label for="endDate" class="sr-only">结束时间</label>
            <input type="date" class="form-control mb-2" id="endDate" name="endDate" placeholder="结束时间" value="${param.endDate}">
          </div>

          <!-- 收益筛选 -->
          <div class="col-auto">
            <label for="minIncome" class="sr-only">最小收益</label>
            <input type="number" step="1" class="form-control mb-2" id="minIncome" name="minIncome" placeholder="最小收益" value="${param.minIncome}">
          </div>
          <div class="col-auto">
            <label for="maxIncome" class="sr-only">最大收益</label>
            <input type="number" step="1" class="form-control mb-2" id="maxIncome" name="maxIncome" placeholder="最大收益" value="${param.maxIncome}">
          </div>

          <!-- 目标网站选择 -->
          <div class="col-auto">
            <label for="website" class="sr-only">目标网站</label>
            <select id="website" name="website" class="form-control mb-2">
              <option value="" <c:if test="${empty param.website}">selected</c:if>>所有网站</option>
              <option value="${applicationScope.NEWS_SITE}"
                      <c:if test="${param.website == applicationScope.NEWS_SITE}">selected</c:if>>
                新闻
              </option>
              <option value="${applicationScope.BOOK_SITE}"
                      <c:if test="${param.website == applicationScope.BOOK_SITE}">selected</c:if>>
                书城
              </option>
            </select>
          </div>

          <!-- 按钮布局 -->
          <div class="mt-4 d-flex justify-content-between">
            <!-- 返回按钮放在左侧 -->
            <a href="${pageContext.request.contextPath}/advertiserServlet.do?method=viewAd" class="btn btn-secondary">
              <i class="bi bi-arrow-left"></i>
              返回
            </a>
            <!-- 查询和重置按钮放在右侧 -->
            <div>
              <button type="submit" class="btn btn-primary me-2">查询</button>
              <a href="advertiserServlet.do?method=viewAdRecords" class="btn btn-secondary">重置</a>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>

  <!-- 广告记录表格 -->
        <div class="card-header">
          <h5 class="mb-0">广告记录</h5>
        </div>
        <div class="card-body">
  <div class="row mt-4">
    <div class="col-md-12">
      <table class="table table-bordered table-hover">
        <thead class="thead-dark">
        <tr>
          <th scope="col">记录ID</th>
          <th scope="col">广告ID</th>
          <th scope="col">广告标题</th>
          <th scope="col">收益</th>
          <th scope="col">点击时间</th>
          <th scope="col">投放网站</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="record" items="${adRecords}">
          <tr>
            <td>${record.id}</td>
            <td>${record.adId}</td>
            <td>
              <c:choose>
                <c:when test="${not empty record.adId}">
                  <a href="advertiserServlet.do?method=viewAdDetails&id=${record.adId}">
                    查看广告详情
                  </a>
                </c:when>
                <c:otherwise>
                  未知广告
                </c:otherwise>
              </c:choose>
            </td>
            <td>$${record.income}</td>
            <td>${record.clickTime}</td>
            <td><a href="${record.website}" target="_blank">${record.website}</a></td>
          </tr>
        </c:forEach>
        <c:if test="${empty adRecords}">
          <tr>
            <td colspan="6" class="text-center">暂无广告投放记录</td>
          </tr>
        </c:if>
        </tbody>
      </table>
    </div>
  </div>
        </div>
  </div>
</div>
</div>
</div>
<!-- 引入Bootstrap JS -->
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</body>
</html>
