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
		<title>商品信息管理</title>
		<link rel="stylesheet" href="${sessionScope.bpath}/views/assets/css/bootstrap.min.css"/>
		<link rel="stylesheet" href="${sessionScope.bpath}/views/assets/css/font-awesome.min.css"/>
		<link rel="stylesheet" href="${sessionScope.bpath}/views/assets/css/style.css"/>
		<script src="${sessionScope.bpath}/views/js/jquery-2.2.3.min.js"></script>
		<script src="${sessionScope.bpath}/views/assets/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="${sessionScope.bpath}/views/js/ajaxfileupload.js"></script>
		<script type="text/javascript" src="${sessionScope.bpath}/views/js/syssmp.js"></script>
		<script type="text/javascript">
			function addbt(){
				$("#flagi").val("add");
				$("#ggnames").val('');
				$("#ggpics").val('');
				$("#ggvals").val('');
				$("#gtb_sgtypes_id").val('0');
				$("#ggmarks").val('');
				$("#ggflags").val('是');
			}
			function updbt(id){
				$.ajax({
					url:'${sessionScope.bpath}GetDataSvlt',
					type:'post',
					dataType:'json',
					data:{"tbname":"tb_admins_tb_goods","id":id},
					success:function(data){
						var ob=data.ob;
						$("#flagi").val("upd");
						$("#idi").val(id);
						$("#ggnames").val(ob.gnames);
						$("#ggvals").val(ob.gvals);
						$("#gtb_sgtypes_id").val(ob.tb_sgtypes_id);
						$("#ggmarks").val(ob.gmarks);
						$("#ggflags").val(ob.gflags);
					}
				});
			}
			//保存
			function gltj(){
				var gnames=$("#ggnames").val();
				var gpics=$("#ggpics").val();
				var gvals=$("#ggvals").val();
				var tb_sgtypes_id=$("#gtb_sgtypes_id").val();
				var gmarks=$("#ggmarks").val();
				var gflags=$("#ggflags").val();
				var id=$("#idi").val();
				var flag=$("#flagi").val();
				if(gnames==""||gvals==""||tb_sgtypes_id=="0"||gmarks==""||gflags==""){
					alert('商品信息不完整。');
					return;
				}
				if(!ckfffds(gvals)){
					alert("商品单价输入有误");
					return;
				}
				if("add"==flag){
					if(gpics==""){
						alert("选择商品图片");
						return;
					}
					$.ajaxFileUpload({
						url:"${sessionScope.bpath}AddTb_goodsSvlt",
						secureuri:false, 
						type:'post', 
						fileElementId:'ggpics', 
						data:{"gnames":gnames,"gvals":gvals,"tb_sgtypes_id":tb_sgtypes_id,"gmarks":gmarks,"gflags":gflags},
						dataType:'json',
						success:function(data){
							alert(data.msg);
						}
					});
				}
				if("upd"==flag){
					$.ajaxFileUpload({
						url:"${sessionScope.bpath}UpdTb_goodsSvlt",
						secureuri:false,  
						type:'post',
						fileElementId:'ggpics',
						data:{"gnames":gnames,"gvals":gvals,"tb_sgtypes_id":tb_sgtypes_id,"gmarks":gmarks,"gflags":gflags,"id":id},
						dataType:'json',
						success:function(data){
							alert(data.msg);
						}
					});
				}
			}
			//取消
			function rst(){
				window.location.href="${sessionScope.bpath}PagingSvlt?tbname=tb_admins_tb_goods&flag=${pagenum}";
			}
			//查询
			function cktj(){
				var sqls="select tb_goods.id,tb_goods.gnames,tb_goods.gpics,tb_goods.gvals,tb_goods.tb_sgtypes_id,tb_sgtypes.sgtnames,tb_sgtypes.tb_fgtypes_id,tb_fgtypes.fgtname,tb_goods.gmarks,tb_goods.gflags from tb_goods,tb_sgtypes,tb_fgtypes where 1=1  and tb_goods.tb_sgtypes_id=tb_sgtypes.id  and tb_sgtypes.tb_fgtypes_id=tb_fgtypes.id  ";
				var gnames=$("#ckgnames").val();
				if(gnames!=""){
					sqls+=" and tb_goods.gnames like '%"+gnames+"%'";
				}
				var tb_sgtypes_id=$("#cktb_sgtypes_id").val();
				if(tb_sgtypes_id!="0"){
					sqls+=" and tb_goods.tb_sgtypes_id='"+tb_sgtypes_id+"'";
				}
				var gflags=$("#ckgflags").val();
				if(gflags!=""){
					sqls+=" and tb_goods.gflags='"+gflags+"'";
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
				data:{"ids":ids,"tbname":"tb_goods"},
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
								<strong class="card-title">商品信息管理</strong>
							</div>
							<div class="card-body card-block">
								<form action="" method="post" class="form-inline">
									<div class="form-group">
										<label for="ckgnames" class="pr-1  form-control-label">商品名称</label>
										<input type="text" id="ckgnames" required="" class="form-control"/>
									</div>
									<div class="form-group">
										<label for="cktb_sgtypes_id" class="pr-1  form-control-label">所属分类</label>
										<select name="select" id="cktb_sgtypes_id" class="form-control">
											<option value="0">所有分类</option>
											<c:forEach var="a" items="${tb_sgtypeslist }">
												<option value="${a.id}">${a.sgtnames}</option>
											</c:forEach>
										</select>
									</div>
									<div class="form-group">
										<label for="ckgflags" class="pr-1  form-control-label">是否推荐</label>
										<select id="ckgflags" class="form-control">
											<option value="">-所有-</option>
											<option value="是">是</option>
											<option value="否">否</option>
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
											<th scope="col">商品名称</th>
											<th scope="col">商品图片</th>
											<th scope="col">商品单价</th>
											<th scope="col">分类名称</th>
											<th scope="col">所属主类</th>
											<th scope="col">是否推荐</th>
											<th scope="col" style="width: 5%;"></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="a" items="${alist }">
										<tr>
											<td style="width: 5%;">
												<input name="delid" type="checkbox" value="${a.id }"/>
											</td>
											<td>${a.gnames }</td>
											<td width="80px" height="60" bgcolor="#FFFFFF"><img style='width:80px;height:60px;' alt='' src='${sessionScope.bpath}upfiles/${a.gpics }'/></td>
											<td>${a.gvals }</td>
											<td>${a.sgtnames }</td>
											<td>${a.fgtname }</td>
											<td>${a.gflags }</td>
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
									<a href="${sessionScope.bpath}PagingSvlt?tbname=tb_admins_tb_goods&flag=1" class="btn btn-outline-secondary btn-sm" style="margin-right: 20px;margin-left: 10px;">首页</a>
									<a href="${sessionScope.bpath}PagingSvlt?tbname=tb_admins_tb_goods&flag=${pagenum-1}" class="btn btn-outline-secondary btn-sm" style="margin-right: 20px;margin-left: 10px;">上一页</a>
									<a href="${sessionScope.bpath}PagingSvlt?tbname=tb_admins_tb_goods&flag=${pagenum+1}" class="btn btn-outline-secondary btn-sm" style="margin-right: 20px;margin-left: 10px;">下一页</a>
									<a href="${sessionScope.bpath}PagingSvlt?tbname=tb_admins_tb_goods&flag=${pagenums}" class="btn btn-outline-secondary btn-sm" style="margin-right: 20px;margin-left: 10px;">尾页</a>
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
										<div class="col col-md-4"><label for="ggnames" class=" form-control-label">商品名称：</label></div>
										<div class="col-12 col-md-8"><input type="text" id="ggnames" name="gnames" class="form-control"/></div>
									</div>
									<div class="row form-group">
										<div class="col col-md-4"><label for="ggpics" class=" form-control-label">商品图片：</label></div>
										<div class="col-12 col-md-8"><input type="file" id="ggpics" name="gpics" class="form-control-file"/></div>
									</div>
									<div class="row form-group">
										<div class="col col-md-4"><label for="ggvals" class=" form-control-label">商品单价：</label></div>
										<div class="col-12 col-md-8"><input type="text" id="ggvals" name="gvals" class="form-control"/></div>
									</div>
									<div class="row form-group">
										<div class="col col-md-4"><label for="gtb_sgtypes_id" class=" form-control-label">所属分类：</label></div>
										<div class="col-12 col-md-8">
											<select name="tb_sgtypes_id" id="gtb_sgtypes_id" class="form-control">
												<option value="0">选择所属分类</option>
												<c:forEach var="a" items="${tb_sgtypeslist }">
													<option value="${a.id}">${a.sgtnames}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="row form-group">
										<div class="col col-md-4"><label for="ggmarks" class=" form-control-label">商品简介：</label></div>
										<div class="col-12 col-md-8"><textarea name="gmarks" id="ggmarks" rows="4" placeholder="输入内容" class="form-control"></textarea></div>
									</div>
									<div class="row form-group">
										<div class="col col-md-4"><label for="ggflags" class=" form-control-label">是否推荐：</label></div>
										<div class="col-12 col-md-8"><select id="ggflags" name="gflags" class="form-control"><option>是</option><option>否</option></select></div>
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
