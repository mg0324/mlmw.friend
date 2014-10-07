<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<jsp:include page="../../public/common.jsp"></jsp:include>
<link href="<%=basePath%>css/col_menu.css" type="text/css"
	rel="stylesheet" />
<link href="<%=basePath%>css/user/user_selfInfo.css" type="text/css"
	rel="stylesheet" />
<link href="<%=basePath%>backClient/css/role/role.css" type="text/css"
	rel="stylesheet">
<script type="text/javascript" src="<%=basePath%>js/jquery.validate.min.js"></script>
<style type="text/css">
body{
	background-color: white;
}
</style>

<div>
	<div id="div-head">
		用户管理 - 角色管理 - 添加角色
	</div>

	<div>
		<form action="role.do?action=addRole" onsubmit="return goRoleIdSubmit();" id="addRole_form"
		 method="post">
		 	<input type="hidden" id="res_check_rid_info" />
				
			<table class="table">
				<tr>
					<td width="25%" align="center">角色编号</td>
					<td><input type="number" name="roleId" onblur="checkRid(this)" onkeyup="checkRid(this)">
					<span id="res_rid_hava" style="color:red;"></span></td>
				</tr>
				<tr>
					<td align="center">角色名</td>
					<td><input type="text" name="roleName"></td>
				</tr>
				<tr>
					<td align="center">角色等级</td>
					<td><select name="roleGrade">
							<option value="1" selected="selected">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
					</select></td>
				</tr>
				<tr>
					<td align="center">权限</td>
					<td>
						<%
							int i = 0;
						%>
						<table>
							<c:forEach items="${permissionList }" var="permission">
								<%
									if (i % 4 == 0) {
								%>
								<tr>
									<%
										}
									%>
									<td>&nbsp;&nbsp; <input class="checkbox-p" type="checkbox"
										id="chcekbox${permission.permissionId }" name="rolePermission"
										value="${permission.permissionId }">
										${permission.permissionName }
									</td>
									<%
										i++;
											if (i % 4 == 0) {
									%>
								</tr>
								<%
									}
								%>
							</c:forEach>
						</table>
					</td>
				</tr>
				<tr>
					<td align="center" colspan="2"><input type="submit"
						class="btn btn-primary btn-sm" value="添加"> <a
						type="button" class="btn btn-primary btn-sm"
						href="${pageContext.request.contextPath}/role.do?action=toRoleUI">返回</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		$("#addRole_form").validate({
			rules:{
				roleId:{
					required:true,
					number:true,
					minlength:3,
					maxlength:6
				},
				roleName:{
					required:true,
					maxlength:16
				},
				roleGrade:{
					required:true
				}
			},
			messages:{
				roleId:{
					required:"角色编号不能够为空",
					number:"必须是数字",
					minlength:"最少3位",
					maxlength:"最多6位"	
				},
				roleName:{
					required:"角色名不能够为空",
					maxlength:"最长16个字符"
				},
				roleGrade:{
					required:"角色级别不为空"
				}
			}
			
		});
	});
	function checkRid(t){
			var rid = $(t).val();
			$("#res_rid_hava").text("");
			if(rid != ""){
				$.ajax({
					url: getRootPath() + "/ajaxServlet",
					data:{"action":"checkRid","inRid":$(t).val()},
					type:'post',
					dataType:"json",
					error:function(res){
						alert("ajax失败");
					},
					success:function(res){
						var msg = res.msg;
						if(msg == "T"){
							$("#res_rid_hava").text("该角色编号已存在!");
							//alert("该用户名已存在!");
							//用户已存在
							$("#res_check_rid_info").attr('value',"该角色编号已存在!");
						}else{
							$("#res_rid_hava").text("恭喜，该角色编号可以使用!")
							//alert("恭喜，该用户名可以使用!");
							$("#res_check_rid_info").attr('value',"");
						}
					}
				});
			}
		}
		
		function goRoleIdSubmit(){
			var isExistInfo = $("#res_check_rid_info").val();
			if(isExistInfo.length > 0){
				alert(isExistInfo);
				return false;
			}
			return true;
		}
</script>

