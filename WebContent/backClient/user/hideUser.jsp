<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<jsp:include page="../../public/common.jsp"></jsp:include>
<style type="text/css">
body{
	background-color: white;
}
</style>

<div>
	<div style="height: 30px;line-height: 30px;font-size: 15px;color:black;">用户管理 - 屏蔽用户</div>
	<div style="min-height: 350px;">
		<table class="table table-hover table-condensed table-bordered"
		style="padding: 1px;border-color: #ccd; width: 100%px; align: center; border:1px">
		<tr align="center">
			<th style="text-align: center; height: 30px" width="5%">编号</th>
			<th style="text-align: center; height: 30px" width="10%">用户名</th>
			<th style="text-align: center; height: 30px" width="10%">用户昵称</th>
			<th style="text-align: center; height: 30px" width="5%">性别</th>
			<th style="text-align: center; height: 30px" width="20%">用户家乡</th>
			<th style="text-align: center; height: 30px" width="20%">创建时间</th>
			<th style="text-align: center; height: 30px" width="10%">用户状态</th>
			<th style="text-align: center; height: 30px" width="10%">用户类型</th>
			<th style="text-align: center; height: 30px" width="30%">操作</th>
		</tr>
		<c:forEach items="${users}" var="u">
			<tr align="center" style="padding: 3px;">
				<td style="text-align: center; height: 20px">${u.userId }</td>
				<td align="left" style="text-align: center; height: 20px">${u.userName}</td>
				<td align="left" style="text-align: center; height: 20px">${u.userNickName}</td>
				<td style="text-align: center; height: 20px">${u.sex}</td>
				<td style="text-align: center; height: 20px"><c:if
						test="${u.homeTown eq null }">
									未设置
								</c:if> <c:if test="${u.homeTown!=null }">
										${u.homeTown }
									</c:if></td>
				<td style="text-align: center; height: 20px"><fmt:formatDate
						value="${u.createTime}" type="both" /></td>

				<td style="text-align: center; height: 20px"><c:if
						test="${u.status eq 1 }">
										可用
									</c:if> <c:if test="${u.status eq 0 }">
						<red>不可用</red>
					</c:if></td>
				<td style="text-align: center; height: 20px">
					<c:if test="${empty u.roleList }">普通用户</c:if>
					<% boolean isAdmin = false; %>
					<c:forEach items="${u.roleList }" var="role">
						<c:if test="${role.roleGrade>=3 }">
							<% isAdmin = true; %>
						</c:if>
					</c:forEach>
					<%
						if(isAdmin){
							%>
								管理员
							<%
						}
					%>
				</td>
				<td>
				<% if(!isAdmin){
					%>
					<c:if test="${u.status==0}">
						<a href="admin.do?action=activeUser&amp;userId=${u.userId}">取消屏蔽</a>&nbsp;&nbsp;
									</c:if> <c:if test="${u.status!=0}">
						<a onclick="return confirmHide();"
						 href="admin.do?action=hideUser&amp;userId=${u.userId}">屏蔽用户</a>&nbsp;&nbsp;
									</c:if>
					<%
				}%>
				<% if(isAdmin){
					%>
						暂无操作
					<%
				}%>
					
				
				</td>
			</tr>
		</c:forEach>
	</table>
	</div>
	
	<div align="center">
		<jsp:include page="../../public/pager.jsp">
			<jsp:param
				value="${pageContext.request.contextPath}/admin.do?action=toHiddenUserUI"
				name="path" />
		</jsp:include>
	</div>
</div>
<script type="text/javascript">
	function confirmHide() {
		if (confirm("您真的要屏蔽改用户吗?\n[屏蔽后该用户将不可登录系统]")) {
			return true;
		} else {
			return false;
		}
	}
</script>