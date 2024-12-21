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
		<script type="text/javascript">
			//取消
			function rst(){
				window.location.href="${sessionScope.bpath}PagingSvlt?tbname=tb_admins_tb_users&flag=${pagenum}";
			}
			//查询
			function cktj(){
				var sqls="select tb_users.id,tb_users.uphones,tb_users.upwds,tb_users.unames,tb_users.uaddrs from tb_users where 1=1  ";
				var uphones=$("#ckuphones").val();
				if(uphones!=""){
					sqls+=" and tb_users.uphones like '%"+uphones+"%'";
				}
				$.ajax({
					url:'${sessionScope.bpath}CkSvlt',
					type:'post',
					data:{"sql":sqls},
					dataType:'json',
					success:function(data){
						if(data.msg==1){
							rst();
						}else{
							alert(data.msg);
						}
					}
				});
			}
			function delsbt(){
				var ids="";
				$("input:checkbox").each(
					function(){
						if($(this).prop("checked")){
						var nm=$(this).prop("name");
						if(nm.indexOf("delid")==0){
							ids+=$(this).val()+",";
						}
					}
				}
			);
			if(ids==""){
				alert("请选择所要删除的数据.");
				return;
			};
			$.ajax({
				url:'${sessionScope.bpath}DelSvlt',
				type:'post',
				dataType:'json',
				data:{"ids":ids,"tbname":"tb_users"},
				success:function(data){
					alert(data.msg);
					rst();
				}
			});
		}
			
		function updbt(id,isLogin){
				$.ajax({
					url:'${sessionScope.bpath}UpdSvlt',
					type:'post',
					dataType:'json',
					data:{"tbname":"tb_admins_tb_users","id":id,"isLogin":isLogin},
					success:function(data){
						alert(data.msg);
						rst();
					}
				});
			}
	</script>
</head>
<body>
	<div id="right-panel" class="right-panel">
		<div class="content" style="margin-top: 0px;">
			<div class="animated fadeIn">
				<div class="row">
					<div class="col-lg-12">
						<div class="card">
							<div class="card-header">
								<strong class="card-title">客户信息管理</strong>
							</div>
							<div class="card-body card-block">
								<form action="" method="post" class="form-inline">
									<div class="form-group">
										<label for="ckuphones" class="pr-1  form-control-label">电话</label>
										<input type="text" id="ckuphones" required="" class="form-control"/>
									</div>
									<div class="form-group">
										<input type="button" class="btn btn-outline-secondary btn-sm" style="margin-left: 20px;" onclick="cktj();" value="查询"/>
										<div class="form-group">
											<input type="button" class="btn btn-outline-secondary btn-sm" style="margin-left: 20px;" onclick='delsbt();' value="删除"/>
										</div>
									</div>
								</form>
								<table class="table table-striped table-bordered">
									<thead>
										<tr>
											<th scope="col" style="width: 5%;"></th>
											<th scope="col">电话</th>
											<th scope="col">密码</th>
											<th scope="col">姓名</th>
											<th scope="col">地址</th>
											<th scope="col">账号管理</th> 
										</tr>
									</thead>
									<tbody>
										<c:forEach var="a" items="${alist }">
										<tr>
											<td style="width: 5%;">
												<input name="delid" type="checkbox" value="${a.id }"/>
											</td>
											<td>${a.uphones }</td>
											<td>${a.upwds }</td>
											<td>${a.unames }</td>
											<td>${a.uaddrs }</td>
											<td style="width: 5%;">
												<c:if test="${a.isLogin=='1' }">
													<input type="button" class="btn btn-outline-secondary btn-sm" onclick="updbt('${a.id}','0');" value="禁用"/>
												</c:if>
												<c:if test="${a.isLogin=='0' }">
													<input type="button" class="btn btn-outline-secondary btn-sm" onclick="updbt('${a.id}','1');" value="启用"/>
												</c:if>
											</td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<div class="card-footer">
								<span>当前第${pagenum }页，共${pagenums }页</span>
								<span style="float: right;margin-right: 50px;">
									<a href="${sessionScope.bpath}PagingSvlt?tbname=tb_admins_tb_users&flag=1" class="btn btn-outline-secondary btn-sm" style="margin-right: 20px;margin-left: 10px;">首页</a>
									<a href="${sessionScope.bpath}PagingSvlt?tbname=tb_admins_tb_users&flag=${pagenum-1}" class="btn btn-outline-secondary btn-sm" style="margin-right: 20px;margin-left: 10px;">上一页</a>
									<a href="${sessionScope.bpath}PagingSvlt?tbname=tb_admins_tb_users&flag=${pagenum+1}" class="btn btn-outline-secondary btn-sm" style="margin-right: 20px;margin-left: 10px;">下一页</a>
									<a href="${sessionScope.bpath}PagingSvlt?tbname=tb_admins_tb_users&flag=${pagenums}" class="btn btn-outline-secondary btn-sm" style="margin-right: 20px;margin-left: 10px;">尾页</a>
								</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
		</div><!-- /#right-panel -->
</body>
</html>
