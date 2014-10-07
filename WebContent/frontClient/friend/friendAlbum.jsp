<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>${friend.userNickName }的主页</title>
<link href="<%=basePath%>css/user/user_selfInfo.css" type="text/css"
	rel="stylesheet" />
<link href="<%=basePath%>css/user/user_selfMain.css" type="text/css"
	rel="stylesheet" />
<link href="<%=basePath%>css/friend/friend_album.css" type="text/css"
	rel="stylesheet" />
<jsp:include page="../../public/common.jsp"></jsp:include>

</head>
<body>
	<jsp:include page="../../public/nav.jsp"></jsp:include>
	<jsp:include page="../../public/menu.jsp"></jsp:include>
	<div id="main_content" style="padding-top: 20px;">
		
			<div id="album_main_content">
				<div id="left">
					<jsp:include page="friendLeft.jsp"></jsp:include>
				</div>
				
			
				<div id="album_main_right">
					<ul id="album_main">
						<c:if test="${not empty requestScope.albumList}">
							<c:forEach items="${requestScope.albumList }" var="album">
								<li>
									<a href="${pageContext.request.contextPath}/photo.do?action=browseAlbum&from=friendMain&userId=${friend.userId }&albumId=${album.albumId}">
										<img src="${pageContext.request.contextPath}/image/album_cover.jpg" width="140px" height="140px" /> 
									</a>
									<span class="album_li_info"> 
										<a href="${pageContext.request.contextPath}/photo.do?action=browseAlbum&from=friendMain&userId=${friend.userId }&albumId=${album.albumId}">${album.albumName }
										</a><br>
										${fn:length(album.photoList)}张
									</span>
								</li>
							</c:forEach>
						</c:if>
						<c:if test="${empty requestScope.albumList }">
							<li>Ta没有相册</li>
						</c:if>
					</ul>
				</div>
			</div>
			
			<div align="center">
				<jsp:include page="../../public/pager.jsp">
					<jsp:param value="album.do?action=browseAlbum&from=friendMain&userId=${friend.userId }" name="path"/>
				</jsp:include>	
			</div>
			
			<jsp:include page="../../public/ownner.jsp"></jsp:include>
		</div>
</body>
</html>