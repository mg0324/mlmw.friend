<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<title>照片</title>
<link href="<%=basePath%>css/user/user_selfInfo.css" type="text/css" rel="stylesheet"/>
<link href="<%=basePath%>css/album/photo_list.css" type="text/css" rel="stylesheet"/>
<link href="<%=basePath%>uploadify/uploadify.css" rel="stylesheet" type="text/css" />
<jsp:include page="../../public/common.jsp"></jsp:include>
<script src="<%=basePath%>uploadify/jquery.uploadify.min.js" type="text/javascript"></script>
</head>
<body>
	<jsp:include page="../../public/nav.jsp"></jsp:include>
	<jsp:include page="../../public/menu.jsp"></jsp:include>
	<div id="album_main_content">
		<div id="album_address">
			<a href="#main">${friend.userNickName }</a> -	${album.albumName}
		</div>
		<div id="album_main_left">
			<a class="btn btn-primary btn-sm" href="#" id="browse_photo">浏览照片</a>
			<p id="album_operation">
				<a href="${pageContext.request.contextPath}/album.do?action=browseAlbum&from=friendMain&userId=${friend.userId}">返回相册</a>
			</p>

			<hr>
			<ul id="album_main">
				<c:if test="${not empty requestScope.photoList}">
					<c:forEach items="${requestScope.photoList }" var="photo">
						<li>
							<a href="#" onClick="go()">
								<img src="${photo.photoPath}" width="140px" height="140px" />
							</a>
						</li>
					</c:forEach>
				</c:if>
				<c:if test="${empty requestScope.photoList }">
					<li>Ta没有照片</li>
				</c:if>
			</ul>
		</div>
	</div>
<!-- 照片浏览 -->
	<c:if test="${not empty photoList }">
		<div id="browse_photo_dialog">
			<jsp:include page="friendBrowsePhoto.jsp"></jsp:include>	  		
		</div>
	</c:if>
	<c:if test="${empty photoList }">
		<div id="browse_photo_dialog">你没有照片</div>
	</c:if>
	<div align="center">
		<jsp:include page="../../public/pager.jsp">
			<jsp:param value="photo.do?action=browseAlbum&from=friendMain&userId=${friend.userId }&albumId=${album.albumId }" name="path"/>
		</jsp:include>	
	</div>
	<jsp:include page="../../public/ownner.jsp"></jsp:include>
	<script type="text/javascript">
	function go(){
		var browse_photo_dialog = $("#browse_photo_dialog");
		browse_photo_dialog.show().dialog({
			title:'浏览照片',
			width:800,
			height:590,
			left:200,
			top:70,
			modal:true
		});
	}
	 
	$(function(){
		var browse_photo_dialog = $("#browse_photo_dialog");
			
		browse_photo_dialog.hide();

		$("#browse_photo").click(function(){
			browse_photo_dialog.show().dialog({
				title:'浏览照片',
				width:800,
				height:590,
				left:200,
				top:70,
				modal:true
			});
		});
	});
	</script>
</body>
</html>