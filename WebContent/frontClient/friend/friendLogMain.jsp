<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!-- 
	author : chenqiang
	2014-7-22
 -->
 <%@page import="com.friend.vo.Module" %>
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
    <title>好友日志日志</title>
	<jsp:include page="../../public/common.jsp"></jsp:include>
	<link href="<%=basePath%>css/log/log_main.css" type="text/css" rel="stylesheet"/>
	<style type="text/css">
		
	</style>
	
  </head>
  
  <body>
	<jsp:include page="../../public/nav.jsp"></jsp:include>
	<jsp:include page="../../public/menu.jsp"></jsp:include>
	
	<div id="main_log_content">
		<div id="log_list_title">
			好友${friend.userNickName } | 日志
		</div>
		<div id="log_list_content">
			
			<div id="log_list">
				<div class="div_title">
					日志列表
				</div>
				<div id="list_div">
					<ul class="log_ul">
					<c:if test="${empty page.list }">
						好友${friend.userNickName }还没有发表过日志
					</c:if>
					<c:forEach items="${page.list }" var="log">
						<li>
							<c:if test="${log.topFlag eq 1 }">
								<red>[顶]</red>
							</c:if>
							<c:if test="${log.author.userId != friend.userId }">
								<red>[转]</red>
							</c:if>
							<a href="${pageContext.request.contextPath}/log.do?action=showLogInfo&from=friend&friendId=${friend.userId }&logId=${log.logId }">${log.logTitle }</a>
							
							<span class="pull-right"><fmt:formatDate value="${log.publishTime }" type="date"/></span>
							<span class="pull-right">(${log.viewCount }/${log.commentCount })&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
							<span class="pull-right">作者:${log.author.userNickName }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
						</li>
					</c:forEach>
					</ul>
				</div>
				<div align="center" style="border-top:1px solid #bdbdbd;">
					<jsp:include page="../../public/pager.jsp">
						<jsp:param value="${pageContext.request.contextPath}/log.do?action=showLogList&from=friend&userId=${friend.userId }&logSortId=${logSort.logSortId }" name="path"/>
					</jsp:include>	
				</div>
			</div>
			
			<div id="log_sort_list">
				<div class="div_title">
					日志分类列表
				</div>
				<ul>
					<c:if test="${empty logSorts }">
						好友${friend.userNickName }还没有创建日志分类
					</c:if>
					<c:forEach items="${logSorts }" var="logSort">
						<li>
						<c:if test="${logSort.sortName != '我的日志' }">
							<a href="${pageContext.request.contextPath}/log.do?action=showLogList&from=friend&userId=${friend.userId }&logSortId=${logSort.logSortId}">${logSort.sortName }</a>(${logSort.logCount })
						</c:if>
						<c:if test="${logSort.sortName == '我的日志' }">
							<a href="${pageContext.request.contextPath}/log.do?action=showTransferLogList&from=friend&userId=${friend.userId }&logSortId=${logSort.logSortId}">${logSort.sortName }</a>(${logSort.logCount })
						</c:if>
						</li>
					</c:forEach>
				</ul>
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
