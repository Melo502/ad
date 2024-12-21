<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--<%--%>
<%--String path = request.getContextPath();--%>
<%--String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";--%>
<%--%>--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<base href="${sessionScope.bpath}">
	<title>网上商城</title>
	<link rel="stylesheet" type="text/css" href="${sessionScope.bpath}/views/css/style.css">
	<script type="text/javascript" src="${sessionScope.bpath}/views/js/jquery-2.2.3.min.js"></script>
	<script type="text/javascript">
		function changeFrameHeight(){
			var ifm= document.getElementById("mfrm");
			var ifmw=ifm.contentWindow;
			ifm.height=ifmw.document.documentElement.scrollHeight || ifmw.document.body.scrollHeight;
			$("#mfrm").css("min-height","700px");
		}
		window.onresize=function(){
			changeFrameHeight();
		}
	</script>
	</head>
	<body>
	<!-- start header -->
	<header>
		<div class="top center">
			<div class="left fl">
				<ul>
					<li><a href="javascript:void(0);" onclick="return false;">网上商城</a></li>
					<div class="clear"></div>
				</ul>
			</div>
			<div class="right fr">
				<div class="gouwuche fr"><a href="${bpath}LogoutSvlt">安全退出</a></div>
				<div class="fr">
				</div>
				<div class="clear"></div>
			</div>
			<div class="clear"></div>
		</div>
	</header>
	<!--end header -->
	<div class="grzxbj">
		<div class="selfinfo center">
		<div class="lfnav fl" style="min-height: 700px;">
			<c:if test="${utype=='tb_admins' }">
			<div class="ddzx">用户管理</div>
			<div class="subddzx">
				<ul>
					<li><a href="${bpath}InitSvlt?flgs=1&tbname=tb_admins_tb_admins" target="mfrm">管理员信息管理</a></li>
					<li><a href="${bpath}InitSvlt?flgs=1&tbname=tb_admins_tb_users" target="mfrm">客户信息管理</a></li>
				</ul>
			</div>
			<div class="ddzx">商品管理</div>
			<div class="subddzx">
				<ul>
					<li><a href="${bpath}InitSvlt?flgs=1&tbname=tb_admins_tb_fgtypes" target="mfrm">商品主类管理</a></li>
					<li><a href="${bpath}InitSvlt?flgs=1&tbname=tb_admins_tb_sgtypes" target="mfrm">商品分类管理</a></li>
					<li><a href="${bpath}InitSvlt?flgs=1&tbname=tb_admins_tb_goods" target="mfrm">商品信息管理</a></li>
				</ul>
			</div>
			<div class="ddzx">订单管理</div>
			<div class="subddzx">
				<ul>
					<li><a href="${bpath}InitSvlt?flgs=1&tbname=tb_admins_tb_orders" target="mfrm">订单信息管理</a></li>
				</ul>
			</div>
			</c:if>			
		</div>
		<div class="rtcont fr" style="min-height: 700px;">
			<iframe id="mfrm" name="mfrm" onload="changeFrameHeight();"  style="width: 100%;border: 0;margin: 0px;padding: 0px;" scrolling="no"  src=""></iframe>
		</div>
		<div class="clear"></div>
		</div>
	</div>
	</body>
</html>
