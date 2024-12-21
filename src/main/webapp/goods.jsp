<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <base href="${sessionScope.bpath}">
  <link rel="stylesheet" type="text/css" href="${sessionScope.bpath}/res/static/css/main.css">
  <link rel="stylesheet" type="text/css" href="${sessionScope.bpath}/res/layui/css/layui.css">
  <script type="text/javascript" src="${sessionScope.bpath}/res/layui/layui.js"></script>
  <script type="text/javascript" src="${sessionScope.bpath}/views/js/jquery-2.2.3.min.js"></script>
  <script type="text/javascript" src="${sessionScope.bpath}/views/js/syssmp.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/goods.js"></script>

</head>
<body id="list-cont">
<%@include file="/top.jsp" %>
<div class="content content-nav-base datails-content" style="margin-top: 20px;">
  <div class="data-cont-wrap w1200">
    <div class="product-intro layui-clear">
      <div class="preview-wrap">
        <a href="javascript:;"><img style="width: 400px;height: 400px;" src="${sessionScope.bpath}upfiles/${tbg.gpics}"></a>
      </div>
      <div class="itemInfo-wrap">
        <div class="itemInfo">
          <div class="title">
            <h4>${tbg.gnames } </h4>
            <span></span>
          </div>
          <div class="summary">
            <p class="activity"><span>单价</span><strong class="price"><i>￥</i>${tbg.gvals}</strong></p>
            <p>${tbg.gmarks }</p>
          </div>
          <div class="choose-attrs">
            <div class="number layui-clear"><span class="title">数&nbsp;&nbsp;&nbsp;&nbsp;量</span><div class="number-cont"><span class="cut btn">-</span><input id="cnums" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" maxlength="4" type="text" name="" value="1"><span class="add btn">+</span></div></div>
          </div>
          <div class="choose-btns">
            <button class="layui-btn  layui-btn-danger car-btn" onclick="addgwc('${tbg.id}');"><i class="layui-icon layui-icon-cart-simple"></i>加入购物车</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<%@include file="/footer.jsp" %>
<script type="text/javascript">
  layui.config({
    base: '${sessionScope.bpath}res/static/js/util/' //你存放新模块的目录，注意，不是layui的模块目录
  }).use(['mm','jquery'],function(){
    var mm = layui.mm,$ = layui.$;
    var cur = $('.number-cont input').val();
    $('.number-cont .btn').on('click',function(){
      if($(this).hasClass('add')){
        cur++;

      }else{
        if(cur > 1){
          cur--;
        }
      }
      $('.number-cont input').val(cur);
    });

  });
</script>
</body>
</html>
