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
		<title>商品分类管理</title>
		<link rel="stylesheet" href="${sessionScope.bpath}/views/assets/css/bootstrap.min.css"/>
		<link rel="stylesheet" href="${sessionScope.bpath}/views/assets/css/font-awesome.min.css"/>
		<link rel="stylesheet" href="${sessionScope.bpath}/views/assets/css/style.css"/>
		<script src="${sessionScope.bpath}/views/js/jquery-2.2.3.min.js"></script>
		<script src="${sessionScope.bpath}/views/assets/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="${sessionScope.bpath}/views/js/syssmp.js"></script>
		<script type="text/javascript">
			function addbt(){
				$("#flagi").val("add");
				$("#gsgtnames").val('');
				$("#gtb_fgtypes_id").val('0');
			}
			function updbt(id){
				$.ajax({
					url:'${sessionScope.bpath}GetDataSvlt',
					type:'post',
					dataType:'json',
					data:{"tbname":"tb_admins_tb_sgtypes","id":id},
					success:function(data){
						var ob=data.ob;
						$("#flagi").val("upd");
						$("#idi").val(id);
						$("#gsgtnames").val(ob.sgtnames);
						$("#gtb_fgtypes_id").val(ob.tb_fgtypes_id);
					}
				});
			}
			//保存
			function gltj(){
				var sgtnames=$("#gsgtnames").val();
				var tb_fgtypes_id=$("#gtb_fgtypes_id").val();
				var id=$("#idi").val();
				var flag=$("#flagi").val();
				if(sgtnames==""||tb_fgtypes_id=="0"){
					alert('商品分类不完整。');
					return;
				}
				if("add"==flag){
					$.ajax({
						url:'${sessionScope.bpath}AddSvlt',
						type:'post',
						data:{"tbname":"tb_admins_tb_sgtypes","sgtnames":sgtnames,"tb_fgtypes_id":tb_fgtypes_id},
						dataType:'json',
						success:function(data){
							alert(data.msg);
						}
					});
				}
				if("upd"==flag){
					$.ajax({
						url:'${sessionScope.bpath}UpdSvlt',
						type:'post',
						data:{"tbname":"tb_admins_tb_sgtypes","sgtnames":sgtnames,"tb_fgtypes_id":tb_fgtypes_id,"id":id},
						dataType:'json',
						success:function(data){
							alert(data.msg);
						}
					});
				}
			}
			//取消
			function rst(){
				window.location.href="${sessionScope.bpath}PagingSvlt?tbname=tb_admins_tb_sgtypes&flag=${pagenum}";
			}
			//查询
			function cktj(){
				var sqls="select tb_sgtypes.id,tb_sgtypes.sgtnames,tb_sgtypes.tb_fgtypes_id,tb_fgtypes.fgtname from tb_sgtypes,tb_fgtypes where 1=1  and tb_sgtypes.tb_fgtypes_id=tb_fgtypes.id  ";
				var sgtnames=$("#cksgtnames").val();
				if(sgtnames!=""){
					sqls+=" and tb_sgtypes.sgtnames like '%"+sgtnames+"%'";
				}
				var tb_fgtypes_id=$("#cktb_fgtypes_id").val();
				if(tb_fgtypes_id!="0"){
					sqls+=" and tb_sgtypes.tb_fgtypes_id='"+tb_fgtypes_id+"'";
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
				data:{"ids":ids,"tbname":"tb_sgtypes"},
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
								<strong class="card-title">商品分类管理</strong>
							</div>
							<div class="card-body card-block">
								<form action="" method="post" class="form-inline">
									<div class="form-group">
										<label for="cksgtnames" class="pr-1  form-control-label">分类名称</label>
										<input type="text" id="cksgtnames" required="" class="form-control"/>
									</div>
									<div class="form-group">
										<label for="cktb_fgtypes_id" class="pr-1  form-control-label">所属主类</label>
										<select name="select" id="cktb_fgtypes_id" class="form-control">
											<option value="0">所有所属主类</option>
											<c:forEach var="a" items="${tb_fgtypeslist }">
												<option value="${a.id}">${a.fgtname}</option>
											</c:forEach>
										</select>
									</div>
									<div class="form-group">
										<input type="button" class="btn btn-outline-secondary btn-sm" style="margin-left: 20px;" onclick="cktj();" value="查询"/>
									</div>
									<div class="form-group">
										<input type="button" class="btn btn-outline-secondary btn-sm" onclick="addbt();" data-toggle="modal" data-target="#gldiv" style="margin-left: 20px;" value="添加"/>
									</div>
									<div class="form-group">
										<input type="button" class="btn btn-outline-secondary btn-sm" style="margin-left: 20px;" onclick='delsbt();' value="删除"/>
									</div>
								</form>
								<table class="table table-striped table-bordered">
									<thead>
										<tr>
											<th scope="col" style="width: 5%;"></th>
											<th scope="col">分类名称</th>
											<th scope="col">类型名称</th>
											<th scope="col" style="width: 5%;"></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="a" items="${alist }">
										<tr>
											<td style="width: 5%;">
												<input name="delid" type="checkbox" value="${a.id }"/>
											</td>
											<td>${a.sgtnames }</td>
											<td>${a.fgtname }</td>
											<td style="width: 5%;">
												<input type="button" class="btn btn-outline-secondary btn-sm" data-toggle="modal" data-target="#gldiv" onclick="updbt('${a.id}');" value="修改"/>
											</td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<div class="card-footer">
								<span>当前第${pagenum }页，共${pagenums }页</span>
								<span style="float: right;margin-right: 50px;">
									<a href="${sessionScope.bpath}PagingSvlt?tbname=tb_admins_tb_sgtypes&flag=1" class="btn btn-outline-secondary btn-sm" style="margin-right: 20px;margin-left: 10px;">首页</a>
									<a href="${sessionScope.bpath}PagingSvlt?tbname=tb_admins_tb_sgtypes&flag=${pagenum-1}" class="btn btn-outline-secondary btn-sm" style="margin-right: 20px;margin-left: 10px;">上一页</a>
									<a href="${sessionScope.bpath}PagingSvlt?tbname=tb_admins_tb_sgtypes&flag=${pagenum+1}" class="btn btn-outline-secondary btn-sm" style="margin-right: 20px;margin-left: 10px;">下一页</a>
									<a href="${sessionScope.bpath}PagingSvlt?tbname=tb_admins_tb_sgtypes&flag=${pagenums}" class="btn btn-outline-secondary btn-sm" style="margin-right: 20px;margin-left: 10px;">尾页</a>
								</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
		</div><!-- /#right-panel -->
		<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" aria-hidden="true" id="gldiv" >
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title" id="myModalLabel">
							信息编辑
						</h4>
					</div>
					<form id="form_data">
						<div class="modal-body">
							<div class="col-lg-12">
								<div class="card">
									<div class="card-body card-block">
									<div class="row form-group">
										<div class="col col-md-4"><label for="gsgtnames" class=" form-control-label">分类名称：</label></div>
										<div class="col-12 col-md-8"><input type="text" id="gsgtnames" name="sgtnames" class="form-control"/></div>
									</div>
									<div class="row form-group">
										<div class="col col-md-4"><label for="gtb_fgtypes_id" class=" form-control-label">所属主类：</label></div>
										<div class="col-12 col-md-8">
											<select name="tb_fgtypes_id" id="gtb_fgtypes_id" class="form-control">
												<option value="0">选择所属主类</option>
												<c:forEach var="a" items="${tb_fgtypeslist }">
													<option value="${a.id}">${a.fgtname}</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>
							</div>
						</div>
						<input type="hidden" id="idi"/>
						<input type="hidden" id="flagi"/>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-outline-secondary btn-sm" onclick="gltj()">保存</button>
						<button type="button" class="btn btn-outline-secondary btn-sm" onclick="rst()">退出</button>
					</div>
				</form>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
</body>
</html>
