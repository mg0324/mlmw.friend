<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!-- 
	author : chenqiang
	2014-7-22
 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>帖子列表</title>
	<jsp:include page="../../public/common.jsp"></jsp:include>
	<link href="<%=basePath%>css/bbs/topic.css" type="text/css" rel="stylesheet" />
	<style type="text/css">
		
	</style>
	
  </head>
  
  <body>
	<jsp:include page="../../public/nav.jsp"></jsp:include>
	<jsp:include page="../../public/menu.jsp"></jsp:include>
	<div id="bbs_main_content">
		<div id="bbs_logo_row">
			bbsLogo
			<a class="btn btn-primary btn-sm pull-right" 
			href="${pageContext.request.contextPath}/topic.do?action=toAddTopicUI&moduleId=${module.moduleId}">发新帖</a>
		</div>
		<div id="bbs_topic_div">
			<span class="topic_list_title">
				　${module.moduleName } 板块 帖子列表
				<span class="pull-right"><a href="${pageContext.request.contextPath}/module.do?action=findAllModule">返回板块列表</a></span>
			</span>
			<div id="topic_list_div">
				<table class="table table-hover table-condensed" >
					<tr>
			 			<th>帖子标题</th>
			 			<th>发帖人</th>
			 			<th>帖子发表时间</th>
			 			<th>回帖数量</th>
			 		</tr>
			 		<c:if test="${empty listTopics }">
			 			<tr>
			 				<td colspan="4"><red>该板块下暂无帖子...</red>
			 					<a class="btn btn-primary btn-sm" 
									href="${pageContext.request.contextPath}/topic.do?action=toAddTopicUI&moduleId=${module.moduleId}">发新帖</a>
			 				</td>
			 			</tr>
			 		</c:if>
				 	<c:forEach items="${listTopics }" var="topic">
				 		<tr>
				 			<td><a title="点此查看贴子具体内容" href="${pageContext.request.contextPath}/topicComment.do?action=lookComment&moduleId=${module.moduleId}&topicId=${topic.topicId}">${topic.topicTitle }</a></td>
				 			<td>${topic.user.userName}</td>
				 			<td><fmt:formatDate value="${topic.publishTime }" type="both"/>  </td>
				 			<td>${topic.commentNum} </td>
				 		</tr>
				 	</c:forEach>
				</table>
			</div>
			<div align="center">
			<jsp:include page="../../public/pager.jsp">
				<jsp:param value="topic.do?action=showTopicInModule&moduleId=${module.moduleId }" name="path"/>
			</jsp:include>	
		</div>
		</div>
	</div>
		
	<jsp:include page="../../public/ownner.jsp"></jsp:include>
	<script type="text/javascript">
		$(function(){
			
		});
	</script>
</body>
</html>
