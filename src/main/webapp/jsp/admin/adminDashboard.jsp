<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>广告管理系统</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/public.css" />
</head>
<body>
<!--头部-->
<header class="publicHeader">
    <h1>广告管理系统</h1>
    <div class="publicHeaderR">
        <p><span>下午好！</span><span style="color: #fff21b"> ${admin.username }</span> , 欢迎你！</p>
        <a href="${pageContext.request.contextPath }/logout.do?method=adminLogout">退出</a>
    </div>
</header>
<!--时间-->
<section class="publicTime">
    <span id="time">2015年1月1日 11:11  星期一</span>
    <a href="#">温馨提示：为了能正常浏览，请使用高版本浏览器！（IE10+）</a>
</section>
<!--主体内容-->
<section class="publicMian ">
    <div class="left">
        <h2 class="leftH2"><span class="span1"></span>功能列表 <span></span></h2>
        <nav>
            <ul class="list">
                <li ><a href="${pageContext.request.contextPath }/adminServlet.do?method=viewAd">广告信息</a></li>
                <li><a href="${pageContext.request.contextPath }/adminServlet.do?method=viewAdRecords">点击记录</a></li>
                <li><a href="${pageContext.request.contextPath }/adminServlet.do?method=adAnalysis">收益信息</a></li>
                <li><a href="${pageContext.request.contextPath }/adminServlet.do?method=listUser">用户管理</a></li>
                <li><a href="${pageContext.request.contextPath }/logout.do?method=adminLogout">退出系统</a></li>
            </ul>
        </nav>
    </div>
    <div class="right">
        <img class="wColck" src="${pageContext.request.contextPath }/images/lyy.jpg" alt=""/>
    </div>
</section>
<footer class="footer">
    广告管理系统
</footer>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/time.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/common.js"></script>
</body>
</html>
