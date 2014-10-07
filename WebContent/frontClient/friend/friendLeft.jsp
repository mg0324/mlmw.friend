<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<div id="main_content_left">
		<div id="main_content_headArea">
			<span class="mgang_test_head">
				<img alt="" src="${pageContext.request.contextPath}/${friend.head}" width="120" height="120">
			</span>
			<span class="main_left_user_nickname">
			<c:if test="${friend.userNickName!=null }">
				&nbsp;&nbsp;昵称：${friend.userNickName }
			</c:if>
			</span>
		</div>
		<ul id="main_content_left_function_list">
			<li><a href="${pageContext.request.contextPath}/friend.do?action=toFriendMainUI&from=friendMain&userId=${friend.userId}"><img src="${pageContext.request.contextPath}/image/talk_logo.gif"/>Ta的说说</a></li>
			<li><a href="${pageContext.request.contextPath}/log.do?action=toLogMain&from=friendMain&userId=${friend.userId}"><img src="${pageContext.request.contextPath}/image/log_logo.gif"/>Ta的日志</a></li>
			<li><a href="${pageContext.request.contextPath}/album.do?action=browseAlbum&from=friendMain&userId=${friend.userId}"><img src="${pageContext.request.contextPath}/image/album_logo.gif"/>Ta的相册</a></li>
			<li><a href="${pageContext.request.contextPath}/friend.do?action=toFriendInfo&userId=${friend.userId}" ><img src="${pageContext.request.contextPath}/image/person_logo.gif"/>好友资料</a></li>
		</ul>
	</div>