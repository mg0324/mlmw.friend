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
<title>忘记密码</title>
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
				<a href="${pageContext.request.contextPath}">朋友社区</a> &gt; 重置密码
			</div>
			<form class="form-horizontal" role="form" id="findPwd_form" onsubmit="return goSubmit();"
				action="${pageContext.request.contextPath}/user.do?action=findPwd" method="post">
				<input type="hidden" id="res_check_user_info" value=""/>
				<input type="hidden" id="show_res_info" value="${res_info }"/>
				<div class="form-group">
					<label for="inputUserName" class="col-sm-2 control-label"><redStar>*</redStar>用户名</label>
					<div class="col-sm-4">
						<input type="text" class="form-control" id="inputUserName" required="required"
							placeholder="请输入用户名" name="userName" value="${res_user.userName }" onblur="javascript:checkUserName(this);">
						<span id="res_user_name_hava" class="error"></span>
					</div>
				</div>
				<div class="form-group">
					<label for="inputCheckCode" class="col-sm-2 control-label"><redStar>*</redStar>验证码</label>
					<div class="col-sm-4">
						<img id="validateCodeImg" title="点击更换" onclick="refresh(this)"
							src="${pageContext.request.contextPath}/validateCode?action=getCode">
						<input type="text" class="form-control width2"
							required="required" id="validateCode" placeholder="验证码" name="inCode">
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" class="btn btn-primary"> 下一步 </button>
						<a class="btn btn-default" href="${pageContext.request.contextPath}/user.do?action=loginUI">返回登陆</a>
					</div>
				</div>
			</form>
		</div>
	</div>
	<jsp:include page="../../public/ownner.jsp"></jsp:include>
	<script type="text/javascript">
		$(function(){
			
			//弹出提示信息
  			if($("#show_res_info").val().length > 0){
  				alert($("#show_res_info").val());
  				$("#validateCode").focus();
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
  			$("#res_check_user_info").text("");
  			if(inUserName != "" && inUserName.length >= 6){
  				$.ajax({
  					type:'post',
  					url: getRootPath() + "/ajaxServlet",
  					data:{'action':'checkUserName' , 'inName' : inUserName},
  					dataType:"json",
  					success:function(res){
  						var msg = res.msg;
  						if(msg == "T"){
  							$("#res_user_name_hava").text("恭喜!存在用户名");
  							//用户已存在
  							$("#res_check_user_info").attr('value',"");
  						}else{
  							$("#res_user_name_hava").text("请查证后填写正确的用户名");
  							$("#res_check_user_info").attr('value',"请查证后再输入您的用户名");
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