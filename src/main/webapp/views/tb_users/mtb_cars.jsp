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
		<title>购物车管理</title>
		<link rel="stylesheet" href="${sessionScope.bpath}/views/assets/css/bootstrap.min.css"/>
		<link rel="stylesheet" href="${sessionScope.bpath}/views/assets/css/font-awesome.min.css"/>
		<link rel="stylesheet" href="${sessionScope.bpath}/views/assets/css/style.css"/>
		<script src="${sessionScope.bpath}/views/js/jquery-2.2.3.min.js"></script>
		<script src="${sessionScope.bpath}/views/assets/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="${sessionScope.bpath}/views/js/syssmp.js"></script>
		<script type="text/javascript">
			function addbt(){
				$("#flagi").val("add");
				$("#gtb_users_id").val('0');
				$("#gtb_goods_id").val('0');
				$("#gcnums").val('');
			}
			function updbt(id){
				$.ajax({
					url:'${sessionScope.bpath}GetDataSvlt',
					type:'post',
					dataType:'json',
					data:{"tbname":"tb_users_tb_cars","id":id},
					success:function(data){
						var ob=data.ob;
						$("#flagi").val("upd");
						$("#idi").val(id);
						$("#gtb_users_id").val(ob.tb_users_id);
						$("#gtb_goods_id").val(ob.tb_goods_id);
						$("#gcnums").val(ob.cnums);
					}
				});
			}
			//保存
			function gltj(){
				var tb_users_id=$("#gtb_users_id").val();
				var tb_goods_id=$("#gtb_goods_id").val();
				var cnums=$("#gcnums").val();
				var id=$("#idi").val();
				var flag=$("#flagi").val();
				if(tb_users_id=="0"||tb_goods_id=="0"||cnums==""){
					alert('购物车不完整。');
					return;
				}
				if(!ckznum(cnums)){
					alert("购买数量输入有误");
					return;
				}
				if("add"==flag){
					$.ajax({
						url:'${sessionScope.bpath}AddSvlt',
						type:'post',
						data:{"tbname":"tb_users_tb_cars","tb_users_id":tb_users_id,"tb_goods_id":tb_goods_id,"cnums":cnums},
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
						data:{"tbname":"tb_users_tb_cars","tb_users_id":tb_users_id,"tb_goods_id":tb_goods_id,"cnums":cnums,"id":id},
						dataType:'json',
						success:function(data){
							alert(data.msg);
						}
					});
				}
			}
			//取消
			function rst(){
				window.location.href="${sessionScope.bpath}PagingSvlt?tbname=tb_users_tb_cars&flag=${pagenum}";
			}
			//查询
			function cktj(){
				var sqls="select tb_cars.id,tb_cars.tb_users_id,tb_users.uphones,tb_users.upwds,tb_users.unames,tb_users.uaddrs,tb_cars.tb_goods_id,tb_goods.gnames,tb_goods.gpics,tb_goods.gvals,tb_goods.tb_sgtypes_id,tb_sgtypes.sgtnames,tb_sgtypes.tb_fgtypes_id,tb_fgtypes.fgtname,tb_goods.gflags,tb_cars.cnums from tb_cars,tb_users,tb_goods,tb_sgtypes,tb_fgtypes where 1=1  and tb_cars.tb_users_id=tb_users.id  and tb_cars.tb_goods_id=tb_goods.id  and tb_goods.tb_sgtypes_id=tb_sgtypes.id  and tb_sgtypes.tb_fgtypes_id=tb_fgtypes.id  ";
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
				data:{"ids":ids,"tbname":"tb_cars"},
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
								<strong class="card-title">购物车管理</strong>
							</div>
							<div class="card-body card-block">
								<form action="" method="post" class="form-inline">
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
											<th scope="col">电话</th>
											<th scope="col">地址</th>
											<th scope="col">商品名称</th>
											<th scope="col">商品图片</th>
											<th scope="col">商品单价</th>
											<th scope="col">所属分类</th>
											<th scope="col">是否上架</th>
											<th scope="col">购买数量</th>
											<th scope="col" style="width: 5%;"></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="a" items="${alist }">
										<tr>
											<td style="width: 5%;">
												<input name="delid" type="checkbox" value="${a.id }"/>
											</td>
											<td>${a.uphones }</td>
											<td>${a.uaddrs }</td>
											<td>${a.gnames }</td>
											<td>${a.gpics }</td>
											<td>${a.gvals }</td>
											<td>${a.tb_sgtypes_id }</td>
											<td>${a.gflags }</td>
											<td>${a.cnums }</td>
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
									<a href="${sessionScope.bpath}>PagingSvlt?tbname=tb_users_tb_cars&flag=1" class="btn btn-outline-secondary btn-sm" style="margin-right: 20px;margin-left: 10px;">首页</a>
									<a href="${sessionScope.bpath}PagingSvlt?tbname=tb_users_tb_cars&flag=${pagenum-1}" class="btn btn-outline-secondary btn-sm" style="margin-right: 20px;margin-left: 10px;">上一页</a>
									<a href="${sessionScope.bpath}PagingSvlt?tbname=tb_users_tb_cars&flag=${pagenum+1}" class="btn btn-outline-secondary btn-sm" style="margin-right: 20px;margin-left: 10px;">下一页</a>
									<a href="${sessionScope.bpath}PagingSvlt?tbname=tb_users_tb_cars&flag=${pagenums}" class="btn btn-outline-secondary btn-sm" style="margin-right: 20px;margin-left: 10px;">尾页</a>
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
										<div class="col col-md-4"><label for="gtb_users_id" class=" form-control-label">客户id：</label></div>
										<div class="col-12 col-md-8">
											<select name="tb_users_id" id="gtb_users_id" class="form-control">
												<option value="0">选择客户id</option>
												<c:forEach var="a" items="${tb_userslist }">
													<option value="${a.id}"></option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="row form-group">
										<div class="col col-md-4"><label for="gtb_goods_id" class=" form-control-label">商品id：</label></div>
										<div class="col-12 col-md-8">
											<select name="tb_goods_id" id="gtb_goods_id" class="form-control">
												<option value="0">选择商品id</option>
												<c:forEach var="a" items="${tb_goodslist }">
													<option value="${a.id}">${a.gnames}</option>
												</c:forEach>
											</select>
										</div>
									</div>
										<div class="row form-group">
											<div class="col col-md-4"><label for="gcnums" class=" form-control-label">购买数量：</label></div>
											<div class="col-12 col-md-8"><input type="text" id="gcnums" name="cnums" class="form-control"/></div>
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
