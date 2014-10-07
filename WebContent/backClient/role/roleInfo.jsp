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

<style type="text/css">
body{
	background-color: white;
}
</style>
<div>
	<div id="div-head">
		角色管理 - 角色详情[修改角色中的权限]
	</div>

	<div>
		<form action="role.do?action=updateRole" method="post">
			<table class="table">
				<tr>
					<td width="25%" align="center">角色编号</td>
					<td>${role.roleId } <input type="hidden" name="roleId"
						value="${role.roleId }">
					</td>
				</tr>
				<tr>
					<td align="center">角色名</td>
					<td><input type="text" name="roleName"
						value="${role.roleName }"></td>
				</tr>
				<tr>
					<td align="center">角色等级</td>
					<td><select name="roleGrade">
							<option value="1"
								<c:if test="${role.roleGrade==1 }">
									selected="selected"
								</c:if>>1</option>
							<option value="2"
								<c:if test="${role.roleGrade==2 }">
									selected="selected"
								</c:if>>2</option>
							<option value="3"
								<c:if test="${role.roleGrade==3 }">
									selected="selected"
								</c:if>>3</option>
							<option value="4"
								<c:if test="${role.roleGrade==4 }">
									selected="selected"
								</c:if>>4</option>
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
										id="checkbox${permission.permissionId }" name="rolePermission"
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
					
					<c:if test="${not empty rolePermissionList }">
						<c:forEach items="${rolePermissionList }" var="rolePermission">
							<input type="hidden" name="role_permission" class="hidden"
								value="${rolePermission.permissionId }">
						</c:forEach>
					</c:if>
				</tr>
				<tr>
					<td align="center" colspan="2"><input type="submit"
						class="btn btn-primary btn-sm" value="修改"> <a
						type="button" class="btn btn-primary btn-sm"
						href="${pageContext.request.contextPath}/role.do?action=toRoleUI">返回</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		$("input.hidden").each(function() {
			var id = $(this).val();
			$("#checkbox" + id).prop("checked","checked");
		});
	});
</script>

