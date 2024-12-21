<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--<%--%>
<%--String path = request.getContextPath();--%>
<%--String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";--%>
<%--session.setAttribute("bpath", basePath);--%>
<%--%>--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <base href="${sessionScope.bpath}">
  <link rel="stylesheet" type="text/css" href="${sessionScope.bpath}res/static/css/main.css">
  <link rel="stylesheet" type="text/css" href="${sessionScope.bpath}res/layui/css/layui.css">
  <script type="text/javascript" src="${sessionScope.bpath}res/layui/layui.js"></script>
  <script type="text/javascript" src="${sessionScope.bpath}views/js/jquery-2.2.3.min.js"></script>
  <script type="text/javascript" src="${sessionScope.bpath}views/js/syssmp.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/cars.js"></script>


</head>
<body id="list-cont0">
<div class="content content-nav-base shopcart-content">
  <div class="banner-bg">
    <h3>收货地址：</h3>
    <p><select id="addr" name="addr">
      <option value="0">选择收货地址</option>
      <c:forEach var="a" items="${addrlist }">
        <option value="${a.id }">${a.tbanames }_${a.tbaphones }_${a.tbaaddrs }</option>
      </c:forEach>
    </select></p>
  </div>
  <div class="cart">
    <div class="cart-table-th">
      <div class="th th-chk">
        <div class="select-all">
          <div class="cart-checkbox">
            <input class="check-all check" id="allCheckked" type="checkbox" value="true">
          </div>
          <label>&nbsp;&nbsp;全选</label>
        </div>
      </div>
      <div class="th th-item">
        <div class="th-inner">
          商品
        </div>
      </div>
      <div class="th th-price">
        <div class="th-inner">
          单价
        </div>
      </div>
      <div class="th th-amount">
        <div class="th-inner">
          数量
        </div>
      </div>
      <div class="th th-sum">
        <div class="th-inner">
          小计
        </div>
      </div>
      <div class="th th-op">
        <div class="th-inner">
          操作
        </div>
      </div>
    </div>
    <div class="OrderList">
      <div class="order-content" id="list-cont">
        <c:forEach var="a" items="${alist}">
          <ul class="item-content layui-clear">
            <li class="th th-chk">
              <div class="select-all">
                <div class="cart-checkbox">
                  <input class="CheckBoxShop check" id="${a.id }" type="checkbox" num="all" name="select-all" value="${a.id }">
                </div>
              </div>
            </li>
            <li class="th th-item">
              <div class="item-cont">
                <a href="javascript:;"><img src="${sessionScope.bpath}upfiles/${a.gpics}" alt=""></a>
                <div class="text">
                  <div class="title">${a.gnames}</div>
                </div>
              </div>
            </li>
            <li class="th th-price">
              <span class="th-su">${a.gvals }</span>
            </li>
            <li class="th th-amount">
              <div class="box-btn layui-clear">
                <div class="less layui-btn">-</div>
                <input class="Quantity-input" type="text" name="" value="${a.cnums }" disabled="disabled">
                <div class="add layui-btn">+</div>
              </div>
            </li>
            <li class="th th-sum">
              <span class="sum">${a.gvals*a.cnums }</span>
            </li>
            <li class="th th-op">
              <span class="dele-btn">删除</span>
            </li>
          </ul>
        </c:forEach>
      </div>
    </div>

    <div class="FloatBarHolder layui-clear">
      <div class="th th-chk">
        <div class="select-all">
          <div class="cart-checkbox">
            <input class="check-all check" id="" type="checkbox"  value="true">
          </div>
          <label>&nbsp;&nbsp;已选<span class="Selected-pieces">0</span>件</label>
        </div>
      </div>
      <div class="th batch-deletion">
        <span class="batch-dele-btn">批量删除</span>
      </div>
      <div class="th Settlement">
        <button class="layui-btn" onclick="jsbt();">结算</button>
      </div>
      <div class="th total">
        <p>应付：<span class="pieces-total">${allmoney }</span></p>
      </div>
    </div>
  </div>
</div>
<script type="text/javascript">
  layui.config({
    base: '${sessionScope.bpath}res/static/js/util/' //你存放新模块的目录，注意，不是layui的模块目录
  }).use(['mm','jquery','element','car'],function(){
    var mm = layui.mm,$ = layui.$,
            element = layui.element,car = layui.car;
    car.init();
  });
</script>
</body>
</html>
