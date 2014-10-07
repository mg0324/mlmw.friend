<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!-- 
	author : chenqiang
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

<jsp:include page="../../public/common.jsp"></jsp:include>
<link href="<%=basePath%>css/col_menu.css" type="text/css"
	rel="stylesheet" />
<link href="<%=basePath%>css/user/user_selfInfo.css" type="text/css"
	rel="stylesheet" />
<link href="<%=basePath%>css/bss/topic.css" type="text/css"
	rel="stylesheet" />
<style type="text/css">
body{
	background-color: white;
}
</style>

<div style="height: 30px; line-height: 30px; font-size: 15px;">
	论坛管理 - 
	<a
	href="${pageContext.request.contextPath}/module.do?action=findAllModule&toWhere=back">板块管理</a> 
	- 帖子管理
	

</div>

<div style="min-height: 380px;">
<table class="table table-hover table-condensed table-bordered">
	<tr align="center">
		<th style="text-align: center; height: 30px" width="10%">帖子编号</th>
		<th style="text-align: center; height: 30px" width="25%">贴子标题</th>
		<th style="text-align: center; height: 30px" width="10%">发帖人</th>
		<th style="text-align: center; height: 30px" width="8%">回帖数</th>
		<th style="text-align: center; height: 30px" width="30%">发表时间</th>
		<th style="text-align: center; height: 30px" width="25%">操作</th>
	</tr>
	<c:if test="${empty listTopics }">
		<tr>
		<td colspan="6"><red>该板块暂无帖子。</red>
		</td>
		</tr>
	</c:if>
	<c:forEach items="${listTopics }" var="topic">
		<tr align="center" style="padding: 3px;">
			<td style="text-align: center; height: 30px">${topic.topicId }</td>
			<td align="left" style="text-align: center; height: 30px"><b>
					<a
					href="${pageContext.request.contextPath}/topicComment.do?action=lookTopic&topicId=${topic.topicId}&moduleId=${module.moduleId}">${topic.topicTitle}</a>
			</b></td>
			<td style="text-align: center; height: 30px">${topic.user.userNickName}</td>
			<td>${topic.commentNum}</td>
			<td style="text-align: center; height: 30px"><fmt:formatDate
					value="${topic.publishTime}" type="both" /></td>
			<td style="text-align: center; height: 30px">
			<a
					href="${pageContext.request.contextPath}/topicComment.do?action=lookTopic&topicId=${topic.topicId}&moduleId=${module.moduleId}">审核帖子</a>
			<a onclick="return confirmDelete();"
				href="${pageContext.request.contextPath}/topic.do?action=deleteTopic&topicId=${topic.topicId}&moduleId=${module.moduleId}&toWhere=back">删除</a></td>
		</tr>
	</c:forEach>
</table>
</div>
<div align="center">
	<jsp:include page="../../public/pager.jsp">
		<jsp:param
			value="${pageContext.request.contextPath}/topic.do?action=showTopicInModule&moduleId=${module.moduleId }"
			name="path" />
	</jsp:include>
</div>
<script type="text/javascript">
	$(function(){
		
	});
	function confirmDelete() {
		if (confirm("您真的要删除吗?")) {
			return true;
		} else {
			return false;
		}
	}
</script>