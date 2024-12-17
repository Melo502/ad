<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"> <!-- 官方Bootstrap样式 -->
  <title>广告展示</title>
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> <!-- 引入jQuery -->
</head>
<body>
<h1>欢迎来到广告展示页面！</h1>

<!-- 广告展示区域 -->
<div id="ad-container">
  <!-- 后端返回的广告脚本将插入到这里 -->
</div>

<h2>更多内容...</h2>

<script src="${pageContext.request.contextPath}/js/loadAd.js"></script>

<!-- 引入Bootstrap的CDN -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script> <!-- 使用在线CDN引入Bootstrap -->

</body>
</html>
