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
		<title>客户信息管理</title>
		<link rel="stylesheet" href="${sessionScope.bpath}/views/assets/css/bootstrap.min.css"/>
		<link rel="stylesheet" href="${sessionScope.bpath}/views/assets/css/font-awesome.min.css"/>
		<link rel="stylesheet" href="${sessionScope.bpath}/views/assets/css/style.css"/>
		<script src="${sessionScope.bpath}/views/js/jquery-2.2.3.min.js"></script>
		<script src="${sessionScope.bpath}/views/assets/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="${sessionScope.bpath}/views/js/syssmp.js"></script>
</head>
<body>
		<div role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" aria-hidden="true" id="gldiv" >
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title" id="myModalLabel">
							我的信息
						</h4>
					</div>
					<form id="form_data">
						<div class="modal-body">
							<div class="col-lg-12">
								<div class="card">
									<div class="card-body card-block">
									<div class="row form-group">
										<div class="col col-md-4"><label for="guphones" class=" form-control-label">电话：</label></div>
										<div class="col-12 col-md-8"><input disabled="disabled" value="${tb_users.uphones}" type="text" id="guphones" name="uphones" class="form-control"/></div>
									</div>
									<div class="row form-group">
										<div class="col col-md-4"><label for="gupwds" class=" form-control-label">密码：</label></div>
										<div class="col-12 col-md-8"><input disabled="disabled" value="${tb_users.upwds}" type="text" id="gupwds" name="upwds" class="form-control"/></div>
									</div>
									<div class="row form-group">
										<div class="col col-md-4"><label for="gunames" class=" form-control-label">姓名：</label></div>
										<div class="col-12 col-md-8"><input disabled="disabled" value="${tb_users.unames}" type="text" id="gunames" name="unames" class="form-control"/></div>
									</div>
									<div class="row form-group">
										<div class="col col-md-4"><label for="guaddrs" class=" form-control-label">地址：</label></div>
										<div class="col-12 col-md-8"><input disabled="disabled" value="${tb_users.uaddrs}" type="text" id="guaddrs" name="uaddrs" class="form-control"/></div>
									</div>
								</div>
							</div>
						</div>
						<input type="hidden" id="idi"/>
						<input type="hidden" id="flagi"/>
					</div>
					<!--
					<div class="modal-footer">
						<button type="button" class="btn btn-outline-secondary btn-sm" onclick="gltj()">保存</button>
						<button type="button" class="btn btn-outline-secondary btn-sm" onclick="rst()">退出</button>
					</div>
					-->
				</form>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
</body>
</html>
