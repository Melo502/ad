<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>广告分析 - 网站长</title>
    <!-- 引入Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- 引入Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <!-- 引入ECharts库 -->
    <script src="https://cdn.jsdelivr.net/npm/echarts/dist/echarts.min.js"></script>
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
                            下午好！<strong>${webmaster.username}</strong>, 欢迎你！
                        </span>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/logout.do?method=webmasterLogout">退出</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- 主内容 -->
<div class="container mt-5">
    <h2>广告分析</h2>

    <!-- 筛选表单 -->
    <form class="row g-3 mb-4" method="get" action="${pageContext.request.contextPath}/webmasterServlet.do">
        <input type="hidden" name="action" value="ad_analysis">
        <div class="col-md-3">
            <label for="advertiserId" class="form-label">广告主ID</label>
            <input type="number" class="form-control" id="advertiserId" name="advertiserId" value="${advertiserId}" placeholder="输入广告主ID">
        </div>
        <div class="col-md-3">
            <label for="startDate" class="form-label">开始日期</label>
            <input type="date" class="form-control" id="startDate" name="startDate" value="${startDate}">
        </div>
        <div class="col-md-3">
            <label for="endDate" class="form-label">结束日期</label>
            <input type="date" class="form-control" id="endDate" name="endDate" value="${endDate}">
        </div>
        <div class="col-md-3">
            <label for="type" class="form-label">广告类型</label>
            <select class="form-select" id="type" name="type">
                <option value="">全部类型</option>
                <c:forEach var="adType" items="${adTypes}">
                    <option value="${adType}" <c:if test="${adType == type}">selected</c:if>>${adType}</option>
                </c:forEach>
            </select>
        </div>
        <div class="col-md-3 align-self-end">
            <button type="submit" class="btn btn-primary"><i class="bi bi-filter-circle me-2"></i> 筛选</button>
        </div>
    </form>

    <!-- 导航按钮 -->
    <a href="${pageContext.request.contextPath}/webmasterServlet.do?action=ad_list" class="btn btn-secondary mb-3">
        <i class="bi bi-list-ul me-2"></i> 广告列表
    </a>
    <a href="${pageContext.request.contextPath}/webmasterServlet.do?action=ad_records" class="btn btn-secondary mb-3 ms-2">
        <i class="bi bi-graph-up me-2"></i> 广告点击记录
    </a>

    <!-- 展示聚合数据 -->
    <c:if test="${not empty aggregation}">
        <div class="row mb-4">
            <div class="col-md-6">
                <div class="card text-white bg-success mb-3">
                    <div class="card-header">总点击量</div>
                    <div class="card-body">
                        <h5 class="card-title">${aggregation.totalClicks}</h5>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card text-white bg-info mb-3">
                    <div class="card-header">总收益</div>
                    <div class="card-body">
                        <h5 class="card-title">$${aggregation.totalIncome}</h5>
                    </div>
                </div>
            </div>
        </div>

        <!-- 图表展示 -->
        <h4>广告点击量和收益分布</h4>
        <div id="adStatusChart" style="height: 400px;"></div>

        <!-- 表格展示每个广告的详细聚合数据 -->
        <h4 class="mt-5">每个广告的点击量和收益</h4>
        <c:if test="${not empty adDetails}">
            <table class="table table-striped table-bordered">
                <thead class="table-dark">
                <tr>
                    <th>广告ID</th>
                    <th>广告标题</th>
                    <th>点击量</th>
                    <th>收益</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="adDetail" items="${adDetails}">
                    <tr>
                        <td>${adDetail.adId}</td>
                        <td>${adDetail.adTitle}</td>
                        <td>${adDetail.totalClicks}</td>
                        <td>$${adDetail.totalIncome}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty adDetails}">
            <div class="alert alert-info" role="alert">
                暂无详细广告分析数据。
            </div>
        </c:if>
    </c:if>
    <c:if test="${empty aggregation}">
        <div class="alert alert-info" role="alert">
            暂无广告分析数据。
        </div>
    </c:if>
</div>

<!-- JavaScript 生成图表 -->
<script type="text/javascript">
    document.addEventListener('DOMContentLoaded', function() {
        var chartDom = document.getElementById('adStatusChart');
        if (chartDom) {
            var myChart = echarts.init(chartDom);
            var option;

            option = {
                title: {
                    text: '广告点击量和收益分布',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data: ['点击量', '收益'],
                    top: '10%'
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '15%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    data: [
                        <c:forEach var="ad" items="${adDetails}" varStatus="status">
                        "${ad.adTitle}"<c:if test="${!status.last}">,</c:if>
                        </c:forEach>
                    ]
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name: '点击量',
                        type: 'bar',
                        data: [
                            <c:forEach var="ad" items="${adDetails}" varStatus="status">
                            ${ad.totalClicks}<c:if test="${!status.last}">,</c:if>
                            </c:forEach>
                        ],
                        itemStyle: {
                            color: '#0d6efd'
                        }
                    },
                    {
                        name: '收益',
                        type: 'bar',
                        data: [
                            <c:forEach var="ad" items="${adDetails}" varStatus="status">
                            ${ad.totalIncome}<c:if test="${!status.last}">,</c:if>
                            </c:forEach>
                        ],
                        itemStyle: {
                            color: '#198754'
                        }
                    }
                ]
            };

            option && myChart.setOption(option);
        }
    });
</script>

<!-- 引入Bootstrap JS和依赖 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
