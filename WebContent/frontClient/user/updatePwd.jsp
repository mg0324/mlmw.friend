<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>找回密码-重置密码</title>
<jsp:include page="../../public/common.jsp"></jsp:include>
<link href="<%=basePath%>css/user/user_findPwd.css" type="text/css"
	rel="stylesheet" />
<script type="text/javascript" src="<%=basePath%>js/jquery.validate.min.js"></script>
</head>
<body>
	<div id="findPwd">
		<div class="friend_title_logo">
			<span> <a href="${pageContext.request.contextPath}"><img
					src="${pageContext.request.contextPath}/image/friend_community_logo_blue.png" /></a>
			</span>
		</div>
		<div id="find_pwd_area">
			<div class="form_title">
				<a href="${pageContext.request.contextPath}">朋友社区</a> &gt; 找回密码 - 重置密码
			</div>
			<form class="form-horizontal" role="form" id="updatePwd_form" 
				action="${pageContext.request.contextPath}/user.do?action=findPwdResetPwd" method="post">
				<input type="hidden" id="res_check_pwdSafe_info" value="${res_info }"/>
				<input type="hidden" name="userId" value="${backUser.userId }"/>
				<div class="form-group">
					<label for="inputPwd1" class="col-sm-2 control-label">新密码</label>
					<div class="col-sm-4">
						<input type="password" class="form-control" autofocus="autofocus" id="inputPwd1" name="password">
					</div>
				</div>
				<div class="form-group">
					<label for="inputPwd2" class="col-sm-2 control-label">确认密码</label>
					<div class="col-sm-4">
						<input type="password" class="form-control" id="inputPwd2" name="password2">
					</div>
				</div>
				
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" class="btn btn-primary">下一步</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<jsp:include page="../../public/ownner.jsp"></jsp:include>
	<script type="text/javascript">
		$(function() {
			var res_check_pwdSafe_info = $("#res_check_pwdSafe_info").val();
			if(res_check_pwdSafe_info!=""){
				alert(res_check_pwdSafe_info);
			}
			
			$("#updatePwd_form").validate({
  				rules:{
  					password:{
  						required:true,
  						minlength:6,
  						maxlength:16
  					},
  					password2:{
  						required:true,
  						minlength:6,
  						maxlength:16,
  						equalTo:"#inputPwd1"
  					},
  					
  				},
  				messages:{
  					password:{
  						required:"您的密码不能够为空",
  						minlength:"您的密码长度不能够少于6个字符",
  						maxlength:"您的密码长度不能够多于16个字符"
  					},
  					password2:{
  						required:"您的确认密码不能够为空",
  						minlength:"您的确认密码长度不能够少于6个字符",
  						maxlength:"您的确认密码长度不能够多于16个字符",
  						equalTo:"您的两次密码输入不一致"
  					},
  				}
  				
  			});
		});
		
  		
  		
		function refresh(obj) {
			obj.src = getRootPath() + "/validateCode?action=getCode&rand="
					+ Math.random();
		}
	</script>
</body>
</html>