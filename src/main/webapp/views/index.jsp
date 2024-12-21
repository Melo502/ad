<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%--<%--%>
<%--String path = request.getContextPath();--%>
<%--String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";--%>
<%--session.setAttribute("bpath", basePath);%>--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="${sessionScope.bpath}">
		<title>网上商城后台登录</title>
		<link rel="stylesheet" type="text/css" href="${sessionScope.bpath}/views/css/login.css"/>
		<script type="text/javascript" src="${sessionScope.bpath}/views/js/jquery-2.2.3.min.js"></script>
		<script type="text/javascript">
			function lgtj(){
				var uname=$("#uname").val();
				var upassword=$("#upassword").val();
				if(uname==""||upassword==""){
					alert("请输入完整的登录信息");
					return;
				}
				$.ajax({
					url:'${sessionScope.bpath}LoginSvlt',
					type:'post',
					dataType:'json',
					data:{"uname":uname,"upassword":upassword,"utype":"tb_admins"},
					success:function(data){
						if(data.msg==1){
							window.location.href="${sessionScope.bpath}views/main.jsp";
						}else{
							alert(data.msg);
						}
					}
				});
			}
		</script>
	</head>
	<body id="bd" style="background-image:url(${sessionScope.bpath}/upfiles/background.svg);background-color: ghostwhite">
		<!-- login -->
		<div class="top center">
			<div class="logo center">
				<div class="logo center" style="line-height:100px;font-size: 45px;">
					网上商城
				</div>
			</div>
		</div>
		<form  method="post" action="" class="form center">
			<div class="login">
				<div class="login_center">
					<div class="login_top">
						<div class="left fl">用户登录</div>
						<div class="right fr"></div>
						<div class="clear"></div>
						<div class="xian center"></div>
					</div>
					<div class="login_main center">
						<div class="username">用户名:&nbsp;<input id="uname" class="shurukuang" type="text" name="username" placeholder="请输入你的用户名"/></div>
						<div class="username">密&nbsp;&nbsp;&nbsp;&nbsp;码:&nbsp;<input id="upassword" class="shurukuang" type="password" name="upassword" placeholder="请输入你的密码"/></div>
					</div>
					<div class="login_submit">
						<input class="submit" type="button" name="submit" value="立即登录" onclick="lgtj();"/>
					</div>
				</div>
			</div>
		</form>

	</body>
</html>
