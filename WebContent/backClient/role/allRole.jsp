<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>后台管理</title>
	<jsp:include page="../../public/common.jsp"></jsp:include>
	<link href="<%=basePath%>css/col_menu.css" type="text/css" rel="stylesheet"/>
	<link href="<%=basePath%>css/user/user_selfInfo.css" type="text/css" rel="stylesheet"/>	
	<link href="<%=basePath%>backClient/css/role/role.css" type="text/css" rel="stylesheet" >
  </head>
  <style type="text/css">
	body{
		background-color: white;
	}
</style>
  <body>
	<div>
		<div id="div-head">
			<div style="height: 30px;line-height: 30px;font-size: 15px;color:black;">用户管理 - 角色管理</div>
		</div>
		<div class="mgang_toolbar">
			<a type="button"  class="btn btn-primary btn-sm" href="role.do?action=toAddRole">添加角色</a>
		</div>
		<div style="min-height: 345px;">
			<input type="hidden" id="res_message" value="${res_message}"/>
			<table class="table table-hover table-condensed table-bordered">
				<tr align="center">
					<td>
						角色编号
					</td>
					<td>
						角色名
					</td>
					<td>
						角色等级
					</td>
					<td>
						操作
					</td>
				</tr>
				<c:forEach items="${roleList }" var="role">
					<tr align="center">
						<td>
							${role.roleId }
						</td>
						<td>
							<a href="role.do?action=toRoleInfo&roleId=${role.roleId }">${role.roleName }</a>
						</td>
						<td>
							${role.roleGrade }
						</td>
						<td>
							<a 
							 href="role.do?action=toRoleInfo&roleId=${role.roleId }">修改</a>|<a onclick="return confirmDelete();" href="role.do?action=deleteRole&roleId=${role.roleId }">删除</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	<div align="center">
		<jsp:include page="../../public/pager.jsp">
			<jsp:param value="${pageContext.request.contextPath}/role.do?action=toRoleUI&toWhere=back" name="path"/>
		</jsp:include>	
	</div>	
	<script type="text/javascript">
		$(function(){
			var res_message = $("#res_message").val();
			if(res_message!=""){
				alert(res_message);
			}
		});
		function confirmDelete() {
			if (confirm("您真的要删除吗?")) {
				return true;
			} else {
				return false;
			}
		}
	</script>
</body>
</html>
