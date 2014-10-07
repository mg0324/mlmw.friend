<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

<jsp:include page="../../public/common.jsp"></jsp:include>

</head>
<body>
	<jsp:include page="../../public/nav.jsp"></jsp:include>
	<jsp:include page="../../public/menu.jsp"></jsp:include>
	<div id="main_content" style="padding-top: 20px;">

		<jsp:include page="friendLeft.jsp"></jsp:include>
		<div id="self_main_content_center">
			<div class="panel panel-default" id="self_info">
				<div class="panel-heading">
					<h3 class="panel-title">好友资料</h3>
				</div>
				<div class="panel-body">
					<div>
						<span class="col_title">用户名:</span><span class="col_value">${friend.userName }</span>
						<br /> <span class="col_title">昵称:</span><span class="col_value">${friend.userNickName }</span>
						<br /> <span class="col_title">生日:</span> <span class="col_value">
							<fmt:formatDate value="${friend.birthday}" pattern="yyyy-MM-dd" />
						</span> <br /> <span class="col_title">性别:</span><span class="col_value">${friend.sex }</span>
						<br /> <span class="col_title">邮箱:</span> <span class="col_value">${friend.email }</span>
						<br /> <span class="col_title">手机:</span> <span class="col_value">
							<c:if test="${empty friend.phone }">
								<!-- red标签在common.css中定义的 -->
								<red>未设置</red>
							</c:if> ${friend.phone }
						</span> <br /> <span class="col_title">个性签名:</span><span
							class="col_value"> <c:if test="${empty friend.selfWrite }">
								<red>未设置</red>
							</c:if> ${friend.selfWrite }
						</span><br /> <span class="col_title">爱好:</span> <span class="col_value">
							<c:if test="${empty friend.hobbies }">
								<red>未设置</red>
							</c:if> ${friend.hobbies }
						</span> <br /> <span class="col_title">出生地:</span> <span
							class="col_value"> 
							<c:if test="${empty friend.homeTown }">
								<red>未设置</red>
							</c:if> ${friend.homeTown }　　
						</span> <br />

					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../../public/ownner.jsp"></jsp:include>
</body>
</html>