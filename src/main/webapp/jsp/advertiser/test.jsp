<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>广告展示</title>

</head>
<body>
<h1>欢迎来到广告展示页面！</h1>

<!-- 广告展示区域 -->
<div id="ad-container">
  <!-- 后端返回的广告脚本将插入到这里 -->
</div>

<h2>更多内容...</h2>

<script  src="http://localhost:8080/advertise/advertiserServlet.do?method=postAd"></script>

</body>
</html>
