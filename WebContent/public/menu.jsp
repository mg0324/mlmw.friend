<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<meta charset="utf-8"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="<%=basePath%>css/col_menu.css" type="text/css" rel="stylesheet"/>

<div id="self_nav" class="my_menu">
	<ul>
		<li><a style="width:70px;" href="${pageContext.request.contextPath}/user.do?action=toSelfMainUI"><img src="${pageContext.request.contextPath}/${user.head }" width="30px" height="30px"/></a></li>
		<li><a href="${pageContext.request.contextPath}/user.do?action=toSelfMainUI">
			<c:if test="${not empty user.userNickName }">
				${user.userNickName}
			</c:if>		
		<span class="glyphicon glyphicon-chevron-down"></span></a>
			<ul>
				<li><a href="${pageContext.request.contextPath}/user.do?action=toSelfInfoUI">资料</a></li>
				<li><a href="${pageContext.request.contextPath}/talk.do?action=showTalk">说说</a></li>
				<li><a href="${pageContext.request.contextPath}/log.do?action=toLogMain&userId=${user.userId}">日志</a></li>
				<li><a href="${pageContext.request.contextPath}/friend.do?action=showFriendSort">好友</a></li>
				<li><a href="${pageContext.request.contextPath}/album.do?action=browseAlbum&userId=${user.userId}">相册</a></li>
				<li><a href="${pageContext.request.contextPath}/module.do?action=findAllModule">论坛</a></li>
			</ul>
		</li>
		
		
	</ul>
</div>
