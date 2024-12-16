<%--
  Created by IntelliJ IDEA.
  User: 86182
  Date: 2024/12/16
  Time: 18:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>新增广告</title>
    <link rel="stylesheet" type="text/css" href="styles/style5.css">
</head>
<body>

<div class="header">
    <h2>新增广告</h2>
</div>

<div class="ad-form">
    <form action="addAdServlet" method="POST">
        <label for="title">广告标题：</label>
        <input type="text" id="title" name="title" required><br>

        <label for="adCategory">广告类别：</label>
        <input type="text" id="adCategory" name="adCategory" required><br>

        <label for="description">广告描述：</label>
        <textarea id="description" name="description" required></textarea><br>


        <label for="adLink">广告链接：</label>
        <input type="url" id="adLink" name="adLink" required><br>

        <label for="startTime">开始时间：</label>
        <input type="datetime-local" id="startTime" name="startTime" required><br>

        <label for="endTime">结束时间：</label>
        <input type="datetime-local" id="endTime" name="endTime" required><br>

        <label for="budget">广告预算：</label>
        <input type="number" id="budget" name="budget" step="0.01" required><br>

        <label for="costPerClick">每次点击费用 (CPC)：</label>
        <input type="number" id="costPerClick" name="costPerClick" step="0.01" required><br>

        <label for="costPerImpression">每千次展示费用 (CPM)：</label>
        <input type="number" id="costPerImpression" name="costPerImpression" step="0.01" required><br>

        <input type="submit" value="提交广告">
    </form>
    <a href="advertiserDashboard.jsp" class="button">返回广告主面板</a>
</div>

</body>
</html>

