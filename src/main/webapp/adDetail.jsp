<%--
  Created by IntelliJ IDEA.
  User: 86182
  Date: 2024/12/16
  Time: 18:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>广告详细信息</title>
    <link rel="stylesheet" type="text/css" href="styles/style4.css">
</head>
<body>

<div class="header">
    <h2>广告详细信息</h2>

</div>

<div class="ad-detail">
    <h3>${ad.title}</h3>
    <p><strong>广告类别:</strong> ${ad.adCategory}</p>
    <p><strong>广告描述:</strong> ${ad.description}</p>
    <p><strong>广告链接:</strong> <a href="${ad.adLink}" target="_blank">${ad.adLink}</a></p>
    <p><strong>投放开始时间:</strong> ${ad.startTime}</p>
    <p><strong>投放结束时间:</strong> ${ad.endTime}</p>
    <p><strong>广告预算:</strong> ${ad.budget}</p>
    <p><strong>每次点击费用 (CPC):</strong> ${ad.costPerClick}</p>
    <p><strong>每千次展示费用 (CPM):</strong> ${ad.costPerImpression}</p>

    <a href="editAd.jsp?id=${ad.id}">更改信息</a>

    <form action="launchAd.jsp" method="POST">
        <input type="hidden" name="adId" value="${ad.id}">
        <input type="submit" value="投放广告">
    </form>
</div>
<a href="advertiserDashboard.jsp">返回广告主面板</a>
</body>
</html>

