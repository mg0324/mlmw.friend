<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<title>朋友社区后台登陆界面</title>

<jsp:include page="../public/common.jsp"></jsp:include>
<link href="<%=basePath%>backClient/css/admin/login.css" type="text/css"
	rel="stylesheet" />
<style type="text/css">
.bownner {
	width: 100%;
	height: 50px;
	text-align: center;
	line-height: 30px;
	float: left;
	color:gray;
}
</style>
</head>
<body>
	<div style="background-color: #7CC5F8; width:100%;height: 550px;">
		<div id="btop" style="">
			<div></div>
		</div>
		
		<div class="loginFormTheme"
			style="display: block; float: right; margin-right: 100px;">
			<form role="form" id="login_form"
				style="margin-top: 50px; padding: 50px; padding-bottom: 0px;"
				action="${pageContext.request.contextPath}/admin.do?action=login"
				method="post">
				<input type="text" class="form-control" name="userName"
					placeholder="用户名" value="${res_user.userName }" required
					autofocus> <br/>
				<input type="password"
					class="form-control" name="password" placeholder="密码"
					value="${res_user.password }" required> <br /> <span
					class="validate_code"> <img id="" title="验证码不区分大小写，点击更换"
					onclick="refresh(this)"
					src="${pageContext.request.contextPath}/validateCode?action=getCode">
					<input type="text" id="inputCheckCode" placeholder="验证码"
					class="form-control" required="required" name="inCode">
				</span><br/><br/>
				<button class="btn btn-default" type="submit"
					style="width: 93px; height: 40px; ">登录</button>
				<span class="input_error">${login_info }</span>
			</form>
		</div>
		<div id="bADDiv">

		</div>
	</div>


	<div class="bownner">
		Copyright © 2014.7 - 2014.9 DreamWorkHome <a href="#">梦来梦往工作室 </a>版权所有<br />
		朋友社区 | Friend Community
	</div>
	<script type="text/javascript">
		function refresh(obj) {
			obj.src = getRootPath() + "/validateCode?action=getCode&rand="
					+ Math.random();
		}
	</script>

</body>
</html>
