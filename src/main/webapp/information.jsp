<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--<%--%>
<%--String path = request.getContextPath();--%>
<%--String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";--%>
<%--session.setAttribute("bpath", basePath);--%>
<%--%>--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html >
<head>
  <base href="${sessionScope.bpath}">
  <link rel="stylesheet" type="text/css" href="${sessionScope.bpath}/res/static/css/main.css">
  <link rel="stylesheet" type="text/css" href="${sessionScope.bpath}/res/layui/css/layui.css">
  <script type="text/javascript" src="${sessionScope.bpath}/views/js/jquery-2.2.3.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/information.js"></script>


</head>
<body id="body-list-cont">
<%@include file="/top.jsp" %>
<div class="content content-nav-base commodity-content">
  <div class="commod-cont-wrap">
    <div class="commod-cont w1200 layui-clear">
      <div class="left-nav">
        <div class="title">个人中心</div>
        <div class="list-box">
          <dl>
            <dt>个人信息</dt>
            <dd><a href="${sessionScope.bpath}InitSvlt?tbname=tb_users_tb_users" target="mfrm">我的信息</a></dd>
            <dd><a href="${sessionScope.bpath}InitSvlt?flgs=1&tbname=tb_users_tb_addrs" target="mfrm">收货地址</a></dd>
            <dt>购物信息</dt>
            <dd><a href="${sessionScope.bpath}InitSvlt?flgs=1&tbname=tb_users_tb_cars" target="mfrm">购物车</a></dd>
            <dd><a href="${sessionScope.bpath}InitSvlt?flgs=1&tbname=tb_users_tb_orders" target="mfrm">我的订单</a></dd>
          </dl>
        </div>
      </div>
      <div class="right-cont-wrap">
        <div class="right-cont">
          <div class="cont-list layui-clear" id="list-cont">
            <iframe id="mfrm" name="mfrm" onload="changeFrameHeight();"  style="width: 100%;border: 0;margin: 0px;padding: 0px;" scrolling="no"  src="${sessionScope.bpath}InitSvlt?tbname=tb_users_tb_users"></iframe>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<%@include file="/footer.jsp" %>
</body>
</html>
