<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <base href="${sessionScope.bpath}">
  <link rel="stylesheet" type="text/css" href="${sessionScope.bpath}/res/static/css/main.css">
  <link rel="stylesheet" type="text/css" href="${sessionScope.bpath}/res/layui/css/layui.css">
  <script type="text/javascript" src="${sessionScope.bpath}/views/js/jquery-2.2.3.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/reg.js"></script>


</head>
<body id="list-cont">
<%@include file="/top.jsp" %>

<div class="content content-nav-base  login-content">
  <div class="login-bg">
    <div class="login-cont w1200">
      <div class="form-box2">
        <legend>用户注册</legend>
        <div class="layui-form-item">
          <div class="layui-inline iphone">
            <div class="layui-input-inline">
              <i class="layui-icon layui-icon-cellphone iphone-icon"></i>
              <input type="text" name="uphone" id="uphone" lay-verify="required|phone" placeholder="请输入手机号" autocomplete="off" class="layui-input">
            </div>
          </div>
          <div class="layui-inline iphone">
            <div class="layui-input-inline">
              <input type="text" name="upwd" id="upwd"  placeholder="请输入密码" autocomplete="off" class="layui-input">
            </div>
          </div>
          <div class="layui-inline iphone">
            <div class="layui-input-inline">
              <input type="text" name="uname" id="uname"  placeholder="请输入姓名" autocomplete="off" class="layui-input">
            </div>
          </div>
          <div class="layui-inline iphone">
            <div class="layui-input-inline">
              <input type="text" name="uaddr" id="uaddr"  placeholder="请输入地址" autocomplete="off" class="layui-input">
            </div>
          </div>
        </div>
        <div class="layui-form-item login-btn">
          <div class="layui-input-block">
            <button class="layui-btn"  onclick="rgbt();return false">注册</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<%@include file="/footer.jsp" %>
</body>
</html>
