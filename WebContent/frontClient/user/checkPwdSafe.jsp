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
<title>找回密码-密保验证</title>
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
				<a href="${pageContext.request.contextPath}">朋友社区</a> &gt; 找回密码 - 密保验证
			</div>
			<form class="form-horizontal" role="form" id="findPwd_form"
				action="${pageContext.request.contextPath}/user.do?action=findPwdBySafe" method="post">
				<input type="hidden" id="res_check_pwdSafe_info" value="${res_info }"/>
				<input type="hidden" name="userId" value="${backUser.userId }"/>
				<div class="form-group">
					<label for="inputPwdQuestion" class="col-sm-2 control-label">您的密保问题</label>
					<div class="col-sm-4">
						<input type="text" class="form-control" id="inputPwdQuestion" readonly="readonly" value="${backUser.pwdQuestion }">
					</div>
				</div>
				<div class="form-group">
					<label for="inputPwdAnswer" class="col-sm-2 control-label">您的密保答案</label>
					<div class="col-sm-4">
						<input type="text" name="pwdAnswer"
							class="form-control" autofocus="autofocus" id="inputPwdAnswer">
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
			
			$("#findPwd_form").validate({
  				rules:{
  					userName:{
  						required:true,
  						minlength:6,
  						maxlength:16
  						
  					},
  					inCode:{
  						required:true
  					}
  					
  				},
  				messages:{
  					userName:{
  						required:"您的用户名不能够为空",
  						minlength:"您的用户名不能够少于6个字符",
  						maxlength:"您的用户名不能够多于16个字符"
  						
  					},
  					inCode:{
  						required:"您的昵称不能够为空"
  					}
  				}
  				
  			});
		});
		//验证用户名是否在数据库中已经存在
  		function checkUserName(t){
  			var inUserName = $(t).val();
  			$("#res_user_name_hava").text("")
  			if(inUserName != "" && inUserName.length >= 6){
  				$.ajax({
  					type:'post',
  					url: getRootPath() + "/user.do?action=checkUserName&inName="+inUserName,
  					dataType:"text",
  					success:function(res){
  						if(res == "T"){
  							$("#res_user_name_hava").text("该用户名已存在!");
  							//alert("该用户名已存在!");
  							//用户已存在
  							$("#res_check_user_info").attr('value',"该用户名已存在!");
  						}else{
  							$("#res_user_name_hava").text("恭喜，该用户名可以使用!")
  							//alert("恭喜，该用户名可以使用!");
  							$("#res_check_user_info").attr('value',"");
  						}
  					}
  				});
  			}
  		}
  		
  		function goSubmit(){
  			var isExistInfo = $("#res_check_user_info").val();
  			if(isExistInfo.length > 0){
  				alert(isExistInfo);
  				return false;
  			}
  			return true;
  		}
		function refresh(obj) {
			obj.src = getRootPath() + "/validateCode?action=getCode&rand="
					+ Math.random();
		}
	</script>
</body>
</html>