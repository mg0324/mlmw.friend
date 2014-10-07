<%@ page language="java" pageEncoding="utf-8"%>
<!-- 
	author : 周皓
	2014-7-22
 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<title>权限管理</title>
<jsp:include page="../../public/common.jsp"></jsp:include>
<link href="<%=basePath%>backClient/css/user/permission_manage.css"
	type="text/css" rel="stylesheet" />
<script type="text/javascript" src="<%=basePath%>js/jquery.validate.min.js"></script>
<style type="text/css">
body {
	background-color: white;
}
</style>
</head>
<body>

	<div style="height: 30px; line-height: 30px; font-size: 15px; color: black;">用户管理
		- 权限管理
	</div>
	<div>
		<div class="mgang_toolbar">
			<a id="add_p_btn" class="btn btn-primary btn-sm">添加权限</a>
		</div>
		<div style="min-height: 345px;">
			<table class="table table-hover table-condensed table-bordered"
				style="text-align: center;">
				<tr>
					<td width="5%">编号</td>
					<td width="20%">权限名称</td>
					<td width="65%">权限描述</td>
					<td width="10%">操作</td>
				</tr>
				<c:forEach items="${permissions}" var="permission">
					<tr>
						<td>${permission.permissionId}</td>
						<td>${permission.permissionName}</td>
						<td>${permission.permissionNote}</td>
						<td><a onclick="return showUpdateDialog(this)"
							pid="${permission.permissionId }"
							pname="${permission.permissionName}"
							pnote="${permission.permissionNote}" href="javascript:void(0);">修改</a>
							<a onclick="return delPermiss()"
							href="${pageContext.request.contextPath}/admin.do?action=deletePermission&permissionId=${permission.permissionId}">删除</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div id="update_dialog">
			<form method="post" id="update_form"
				action="${pageContext.request.contextPath}/admin.do?action=updatePermission">
				权限编号:<input type="number" id="pid" size="4" readonly="readonly"
					required name="permissionId" /><br /> <br /> 权限名称:<input
					type="text" id="pname" size="38" required name="permissionName" /><br />
				<br /> 权限描述:
				<textarea id="pnote" rows="8" cols="70" required
					name="permissionNote"></textarea>
				<br /> <br /> <input type="submit" class="btn btn-primary btn-sm"
					value="修改" />&nbsp;&nbsp; <input type="reset"
					class="btn btn-primary btn-sm" value="取消"
					onclick="hide_update_dialog()" />
					<input type="hidden" value="${res_message }" id="res_message"/>
			</form>
		</div>
		<div id="add_permission">
			<form id="add_form" onsubmit="return goPidSubmit();"
				action="${pageContext.request.contextPath}/admin.do?action=addPermission"
				method="post" style="font-size: 12px; color: gray;">
				<input type="hidden" id="res_check_pid_info" />
				
				权限编号:<input type="number" id="pid" size="4" required
					name="permissionId" autocomplete="on" onkeyup="checkPid(this)" onblur="checkPid(this)" /><br /> 
					<span id="res_pid_hava" style="color:red;"></span>
					<br /> 权限名称:<input type="text" autocomplete="on"
					id="pname" size="38" required name="permissionName" /><br /> <br />
				权限描述:
				<textarea id="pnote" rows="8" cols="70" required
					name="permissionNote"></textarea>
				<br /> <br /> 权限类型: <input type="radio" value="1"
					name="permissionType">用户 <input type="radio" value="2"
					name="permissionType" checked="checked">管理<br /> <br /> <br />
				<input type="submit" class="btn btn-primary btn-sm" value="确定" />&nbsp;&nbsp;
				<input type="reset" class="btn btn-primary btn-sm" value="取消"
					onclick="hide_add_div()" />
			</form>
		</div>
		<div align="center">
			<jsp:include page="../../public/pager.jsp">
				<jsp:param value="admin.do?action=toPermissionUI" name="path" />
			</jsp:include>
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			var add_permission_dialog = $("#add_permission");
			var update_dailog = $("#update_dialog");
			update_dailog.hide();
			add_permission_dialog.hide();
			$("#add_p_btn").click(function() {
				add_permission_dialog.show().dialog({
					title : "添加权限",
					width : 520,
					height : 450,
					modal : true
				});
			});
			$("#add_form").validate({
  				rules:{
  					permissionId:{
  						required:true,
  						number:true,
  						minlength:4,
  						maxlength:6
  					},
  					permissionName:{
  						required:true,
  						minlength:3,
  						maxlength:12	
  					},
  					permissionNote:{
  						required:true,
  						minlength:2,
  						maxlength:64
  					},
  					permissionType:{
  						required:true
  					}
  				},
  				messages:{
  					permissionId:{
  						required:"权限编号不能够为空",
  						number:"权限编号只能够是数字",
  						minlength:"最少4位",
  						maxlength:"最多6位"
  					},
  					permissionName:{
  						required:"权限名称不能够为空",
  						minlength:"最少3个字符",
  						maxlength:"最多12个字符"	
  					},
  					permissionNote:{
  						required:"权限描述不能够为空",
  						minlength:"最少2个字符",
  						maxlength:"最多64个字符"
  					},
  					permissionType:{
  						required:"权限类型不能够为空"
  					}
  				}
  				
  			});
			
			$("#update_form").validate({
  				rules:{
  					permissionName:{
  						required:true,
  						minlength:3,
  						maxlength:12	
  					},
  					permissionNote:{
  						required:true,
  						minlength:2,
  						maxlength:64
  					}
  				},
  				messages:{
  					permissionName:{
  						required:"权限名称不能够为空",
  						minlength:"最少3个字符",
  						maxlength:"最多12个字符"	
  					},
  					permissionNote:{
  						required:"权限描述不能够为空",
  						minlength:"最少2个字符",
  						maxlength:"最多64个字符"
  					}
  				}
  				
  			});
			
			
			
			var res_message = $("#res_message").val();
			if(res_message!=""){
				alert(res_message);
			}
		});
		function hide_add_div() {
			$("#add_permission").dialog('close');
		}

		function delPermiss() {
			if (confirm("您真的要删除吗?")) {
				return true;
			} else {
				return false;
			}
		}

		function submit_form() {
			var v = document.getElementByName("permissionType").value;
			alert(v);
			return false;
		}
		function showUpdateDialog(t) {
			if (confirm("您真的要修改该权限吗?")) {
				$("#pid").val($(t).attr("pid"));
				$("#pname").val($(t).attr("pname"));
				$("#pnote").val($(t).attr("pnote"));
				$("#update_dialog").show().dialog({
					title : "修改权限",
					width : 520,
					height : 400,
					modal : true
				});
			}
			return false;
		}
		function hide_update_dialog() {
			$("#update_dialog").dialog("close");
		}
		
  		function checkPid(t){
  			var pid = $(t).val();
  			$("#res_pid_hava").text("");
  			if(pid != ""){
  				$.ajax({
  					url: getRootPath() + "/ajaxServlet",
  					data:{"action":"checkPid","inPid":$(t).val()},
  					type:'post',
  					dataType:"json",
  					error:function(res){
  						alert("ajax失败");
  					},
  					success:function(res){
  						var msg = res.msg;
  						if(msg == "T"){
  							$("#res_pid_hava").text("该权限编号已存在!");
  							//alert("该用户名已存在!");
  							//用户已存在
  							$("#res_check_pid_info").attr('value',"该权限编号已存在!");
  						}else{
  							$("#res_pid_hava").text("恭喜，该权限编号可以使用!")
  							//alert("恭喜，该用户名可以使用!");
  							$("#res_check_pid_info").attr('value',"");
  						}
  					}
  				});
  			}
  		}
  		
  		function goPidSubmit(){
  			var isExistInfo = $("#res_check_pid_info").val();
  			if(isExistInfo.length > 0){
  				alert(isExistInfo);
  				return false;
  			}
  			return true;
  		}
	</script>
</body>
</html>
