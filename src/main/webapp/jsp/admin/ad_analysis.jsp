<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <title>广告分析</title>
  <!-- 引入Bootstrap CSS -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
  <!-- 引入Bootstrap Icons（用于图标） -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
  <!-- 引入ECharts -->
  <script src="https://cdn.jsdelivr.net/npm/echarts/dist/echarts.min.js"></script>
  <style>
    body {
      display: flex;
      min-height: 100vh;
      background-color: #ffffff; /* 白色背景 */
    }
    .main-content {
      flex: 1;
      padding: 20px;
    }
    .table thead {
      background-color: #f8f9fa; /* 浅灰色表头背景 */
      color: #343a40; /* 深色表头文本 */
    }
    footer {
      text-align: center;
      padding: 15px 0;
      background-color: #f8f9fa; /* 浅灰色背景 */
      color: #343a40; /* 深色文本 */
      position: fixed;
      width: 100%;
      bottom: 0;
      border-top: 1px solid #dee2e6; /* 页脚上边框 */
    }
    /* 响应式调整 */
    @media (max-width: 768px) {
      body {
        flex-direction: column;
      }
      footer {
        position: static;
      }
    }
  </style>
</head>
<body>

<!-- 主内容区域 -->
<div class="main-content">
  <div class="container-fluid">
    <!-- 页面标题 -->
    <div class="row mt-4">
      <div class="col">
        <h1 class="text-center">广告收益分析</h1>
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

    <!-- 过滤表单 -->
    <div class="card mb-4">
      <div class="card-header">
        <h5 class="mb-0">过滤条件</h5>
      </div>
      <div class="card-body">
        <form method="get" action="adminServlet.do">
          <input type="hidden" name="method" value="adAnalysis">
          <div class="row g-3">
            <!-- 广告类型选择 -->
            <div class="col-md-3">
              <label for="type" class="form-label">广告类型</label>
              <select class="form-select" id="type" name="type">
                <option value="">全部类型</option>
                <c:forEach var="typeOption" items="${adTypes}">
                  <option value="${typeOption}"
                          <c:if test="${typeOption == selectedType}">selected</c:if>>${typeOption}
                  </option>
                </c:forEach>
              </select>
            </div>
            <!-- 网站选择 -->
            <div class="col-md-3">
              <label for="website" class="form-label">网站</label>
              <select class="form-select" id="website" name="website">
                <option value="" <c:if test="${empty param.website}">selected</c:if>>所有网站</option>
                <option value="http://localhost:8080/advertise/jsp/advertiser/test.jsp"
                        <c:if test="${param.website == 'http://localhost:8080/advertise/jsp/advertiser/test.jsp'}">selected</c:if>>
                  新闻
                </option>
                <option value="http://localhost:8080/advertise/jsp/advertiser/test.jsp"
                        <c:if test="${param.website == 'http://localhost:8080/advertise/jsp/advertiser/test.jsp'}">selected</c:if>>
                  书城
                </option>
              </select>
            </div>

            <!-- 开始日期选择 -->
            <div class="col-md-3">
              <label for="startDate" class="form-label">开始日期</label>
              <input type="date" class="form-control" id="startDate" name="startDate" value="${startDate}">
            </div>
            <!-- 结束日期选择 -->
            <div class="col-md-3">
              <label for="endDate" class="form-label">结束日期</label>
              <input type="date" class="form-control" id="endDate" name="endDate" value="${endDate}">
            </div>
          </div>
          <!-- 按钮布局 -->
          <div class="mt-4 d-flex justify-content-between">
            <!-- 返回按钮放在左侧 -->
            <a href="${pageContext.request.contextPath}/jsp/admin/adminDashboard.jsp" class="btn btn-secondary">
              <i class="bi bi-arrow-left"></i>
              返回
            </a>
            <!-- 查询和重置按钮放在右侧 -->
            <div>
              <button type="submit" class="btn btn-primary me-2">查询</button>
              <a href="adminServlet.do?method=adAnalysis" class="btn btn-secondary">重置</a>
            </div>
          </div>
        </form>
      </div>
    </div>

    <!-- 总体点击量和收益 -->
    <div class="row mb-4">
      <!-- 点击量卡片 -->
      <div class="col-md-6 mb-3">
        <div class="card h-100">
          <div class="card-header">
            <h5 class="mb-0">总体点击量</h5>
          </div>
          <div class="card-body d-flex align-items-center justify-content-center">
            <h6>点击量: <span class="badge bg-success fs-4">${overallAggregation.totalClicks}</span></h6>
          </div>
        </div>
      </div>
      <!-- 收益卡片 -->
      <div class="col-md-6 mb-3">
        <div class="card h-100">
          <div class="card-header">
            <h5 class="mb-0">总体收益</h5>
          </div>
          <div class="card-body d-flex align-items-center justify-content-center">
            <h6>收益: <span class="badge bg-info fs-4">${overallAggregation.totalIncome}</span></h6>
          </div>
        </div>
      </div>
    </div>

    <!-- 按每个广告的点击量和收益 -->
    <div class="card">
      <div class="card-header">
        <h5 class="mb-0">每个广告的点击量和收益</h5>
      </div>
      <div class="card-body">
        <div class="row">
          <!-- 点击量图表 -->
          <div class="col-md-6 mb-3">
            <div class="card h-100">
              <div class="card-header">
                <h6 class="mb-0">点击量</h6>
              </div>
              <div class="card-body">
                <div id="clickChart" style="width: 100%; height: 300px;"></div>
              </div>
            </div>
          </div>
          <!-- 收益图表 -->
          <div class="col-md-6 mb-3">
            <div class="card h-100">
              <div class="card-header">
                <h6 class="mb-0">收益</h6>
              </div>
              <div class="card-body">
                <div id="incomeChart" style="width: 100%; height: 300px;"></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<!-- 传递数据到JavaScript -->
<script type="text/javascript">
  window.adAggregations = [
    <c:forEach var="agg" items="${adAggregations}" varStatus="status">
    {
      adId: ${agg.adId},
      adTitle: "${agg.adTitle}",
      clickCount: ${agg.totalClicks},
      totalIncome: ${agg.totalIncome}
    }<c:if test="${!status.last}">,</c:if>
    </c:forEach>
  ];

  window.overallAggregation = {
    totalClicks: ${overallAggregation.totalClicks},
    totalIncome: ${overallAggregation.totalIncome}
  };
</script>

<!-- 引入外部JavaScript文件 -->
<script src="${pageContext.request.contextPath}/js/analysisAd.js"></script>

<!-- 引入Bootstrap JS -->
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</body>
</html>
