<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--<%--%>
<%--String path = request.getContextPath();--%>
<%--String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";--%>
<%--%>--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="${sessionScope.bpath}">
		<title>订单信息管理</title>
		<link rel="stylesheet" href="${sessionScope.bpath}/views/assets/css/bootstrap.min.css"/>
		<link rel="stylesheet" href="${sessionScope.bpath}/views/assets/css/font-awesome.min.css"/>
		<link rel="stylesheet" href="${sessionScope.bpath}/views/assets/css/style.css"/>
		<script src="${sessionScope.bpath}/views/js/jquery-2.2.3.min.js"></script>
		<script src="${sessionScope.bpath}/views/assets/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="${sessionScope.bpath}/views/js/syssmp.js"></script>
		<script type="text/javascript">
			function updbt(id,oflag){
				$.ajax({
					url:'${sessionScope.bpath}UpdSvlt',
					type:'post',
					dataType:'json',
					data:{"tbname":"tb_admins_tb_orders","id":id,"oflag":oflag},
					success:function(data){
						alert(data.msg);
						rst();
					}
				});
			}
			
			//取消
			function rst(){
				window.location.href="${sessionScope.bpath}PagingSvlt?tbname=tb_users_tb_orders&flag=${pagenum}";
			}
			//查询
			function cktj(){
				<c:if test="${empty myinfo}">
					alert("请登录");
					return;
				</c:if>
				<c:if test="${not empty myinfo}">
				var sqls="select tb_orders.id,tb_orders.onos,tb_orders.tb_users_id,tb_orders.tbanames,tb_orders.tbaphones,tb_orders.tbaaddrs,tb_orders.otimes,tb_orders.oflags from tb_orders where 1=1  ";
				var onos=$("#ckonos").val();
				if(onos!=""){
					sqls+=" and tb_orders.onos like '%"+onos+"%'";
				}
				var stime=$("#ckstime").val();
				if(stime!=""){
					sqls+=" and tb_orders.otimes>='"+stime+"'";
				}
				var etime=$("#cketime").val();
				if(etime!=""){
					sqls+=" and tb_orders.otimes<='"+etime+" 23:59:59'";
				}
				sqls+=" and tb_users_id=${myinfo.id} order by otimes desc";
				
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
				</c:if>
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
				data:{"ids":ids,"tbname":"tb_orders"},
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
							<div class="card-body card-block">
								<form action="" method="post" class="form-inline">
									<div class="form-group">
										<label for="ckonos" class="pr-1  form-control-label">订单编号</label>
										<input type="text" id="ckonos" required="" class="form-control"/>
									</div>
									<div class="form-group">
										<label for="ckstime" class="pr-1  form-control-label">时间：</label>
										<input type="text" id="ckstime" required="" class="form-control Wdate" onfocus="WdatePicker({readOnly:true,maxDate:'%y-%M-%d'})"/>
									</div>
									<div class="form-group">
										<label for="cketime" class="pr-1  form-control-label">-</label>
										<input type="text" id="cketime" required="" class="form-control Wdate" onfocus="WdatePicker({readOnly:true,maxDate:'%y-%M-%d'})"/>
									</div>
									<div class="form-group">
										<input type="button" class="btn btn-outline-secondary btn-sm" style="margin-left: 20px;" onclick="cktj();" value="查询"/>
									</div>
									<div class="form-group">
									</div>
								</form>
								<table class="table table-striped table-bordered">
									<thead>
										<tr>
											<th scope="col" style="width: 5%;"></th>
											<th scope="col">订单编号</th>
											<th scope="col">收货人</th>
											<th scope="col">联系电话</th>
											<th scope="col">收货地址</th>
											<th scope="col">下单时间</th>
											<th scope="col">订单状态</th>
											<th scope="col" style="width: 5%;"></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="a" items="${alist }">
										<tr>
											<td style="width: 5%;">
												<input name="delid" type="checkbox" value="${a.id }"/>
											</td>
											<td>${a.onos }</td>
											<td>${a.tbanames }</td>
											<td>${a.tbaphones }</td>
											<td>${a.tbaaddrs }</td>
											<td>${fn:substring(a.otimes,0,10)}</td>
											<td>${a.oflags }</td>
											<td style="width: 5%;">
												<c:if test="${a.oflags=='已发货' }">
													<input type="button" class="btn btn-outline-secondary btn-sm" onclick="updbt('${a.id}','已完成');" value="签收"/>
												</c:if>
											</td>
										</tr>
										<c:if test="${not empty a.tbdlist }">
											<tr>
												<td colspan="8">
													<table style="width: 100%;">
														<c:forEach var="b" items="${a.tbdlist }">
														<tr>
															<td width="80px" height="60"><img style='width:80px;height:60px;' alt='' src='${sessionScope.bpath}upfiles/${b.gpics }'/></td>
															<td>${b.gnames}</td>
															<td>${b.gvals }</td>
															<td>${b.dnums }</td>
															<td>${b.gvals*b.dnums }</td>
														</tr>		
														</c:forEach>
													</table>
												</td>
											</tr>
										</c:if>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<div class="card-footer">
								<span>当前第${pagenum }页，共${pagenums }页</span>
								<span style="float: right;margin-right: 50px;">
									<a href="${sessionScope.bpath}PagingSvlt?tbname=tb_admins_tb_orders&flag=1" class="btn btn-outline-secondary btn-sm" style="margin-right: 20xp;margin-left: 10px;">首页</a>
									<a href="${sessionScope.bpath}PagingSvlt?tbname=tb_admins_tb_orders&flag=${pagenum-1}" class="btn btn-outline-secondary btn-sm" style="margin-right: 20xp;margin-left: 10px;">上一页</a>
									<a href="${sessionScope.bpath}PagingSvlt?tbname=tb_admins_tb_orders&flag=${pagenum+1}" class="btn btn-outline-secondary btn-sm" style="margin-right: 20xp;margin-left: 10px;">下一页</a>
									<a href="${sessionScope.bpath}PagingSvlt?tbname=tb_admins_tb_orders&flag=${pagenums}" class="btn btn-outline-secondary btn-sm" style="margin-right: 20xp;margin-left: 10px;">尾页</a>
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
<script type="text/JavaScript" src="${sessionScope.bpath}views/js/My97DatePicker/WdatePicker.js"></script>
</html>
