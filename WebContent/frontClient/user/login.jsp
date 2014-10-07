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
<title>朋友社区首页</title>

<jsp:include page="../../public/common.jsp"></jsp:include>
<link href="<%=basePath%>css/user/user_login.css" type="text/css" rel="stylesheet"/>
	
<style type="text/css">
	
</style>
</head>
<body>
	<div class="bg">
		<div class="content">
			<div class="left">
				<a href="${pageContext.request.contextPath}"><img src="${pageContext.request.contextPath}/image/friend_community_logo_blue.png"/></a>
				<div class="desLocation">
					<h4><b>朋友一生一起走，那些日子可再有！</b></h4>
					<p>
						有些人，对你无关紧要!<br/>
						有些人，你永远难记住!<br/>
						有些人，你想忘都忘不掉!<br/>
						而朋友，是你一生要走的路……
					</p>
					<br/>　
					<img src="${pageContext.request.contextPath}/image/friends_1.gif" width="250px"></img>　
					<img src="${pageContext.request.contextPath}/image/friends_2.gif" width="250px"></img>
				</div>
			</div>
			<div class="right">
				<div class="loginFormTheme container">
					<form class="form-signin" role="form" id="login_form"  action="${pageContext.request.contextPath}/user.do?action=login" method="post">
						<input type="text" class="form-control" name="userName" placeholder="用户名" value="${res_user.userName }" required autofocus><br/> 
						<input type="password" class="form-control" name="password" placeholder="密码"  value="${res_user.password }"  required> <br/>
						<input type="hidden" value="${res_info }"/>
						<img id="" title="验证码不区分大小写，点击更换"
							onclick="refresh(this)" src="${pageContext.request.contextPath}/validateCode?action=getCode"> 
						<input type="text" id="inputCheckCode" placeholder="验证码" class="form-control"
							required="required" name="inCode">
						<br/>
						<button class="btn btn-primary" type="submit">&nbsp;&nbsp;登陆&nbsp;&nbsp;</button>
						<span class="input_error">${login_info }</span>
						
					</form>
					<br/>
					<a href="${pageContext.request.contextPath}/user.do?action=regUserUI">用户注册</a>　　　
					<a href="${pageContext.request.contextPath}/user.do?action=findPwdUI">忘记密码</a>
				
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../../public/ownner.jsp"></jsp:include>
	<script type="text/javascript">
  		$(function(){
  			var res_info = $("#res_info").val();
  			if(res_info.length>0){
  				alert(res_info);
  			}
  		});
  		function refresh(obj) {
  	        obj.src = getRootPath() + "/validateCode?action=getCode&rand="+Math.random();
  	    }
  </script>
</body>
</html>
