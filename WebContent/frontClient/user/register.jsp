<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>朋友社区注册页面</title>
    <jsp:include page="../../public/common.jsp"></jsp:include>
  	<link href="<%=basePath%>css/user/user_register.css" type="text/css" rel="stylesheet"/>
  	<link href="<%=basePath%>datepicker/jquery-ui.min.css" type="text/css" rel="stylesheet"/>
  	<script type="text/javascript" src="<%=basePath%>js/jquery.validate.min.js"></script>
  	<script type="text/javascript" src="<%=basePath%>datepicker/datepicker.js"></script>
  	
  	
  </head>
  
  <body>
  	<div class="container reg_content">
  		<div>
  			<span>
  				<a href="${pageContext.request.contextPath}"><img src="${pageContext.request.contextPath}/image/friend_community_logo_blue.png"/></a>
  			</span>
  			<span class="reg_rightDesc">
  				<i>朋友不容错过，赶快加入朋友社区吧!</i>
  			</span>
  		</div>
		<div class="reg_regDiv">
			<form action="${pageContext.request.contextPath}/user.do?action=regUser" method="post" id="reg_form" class="container form-horizontal"
				role="form" onsubmit="return goSubmit();">
				<span class="reg_linkIndex"> <a
					href="${ pageContext.request.contextPath }/user.do?action=loginUI"><span
						class="glyphicon glyphicon-arrow-left"></span> 已有账号 立即登录</a> </span> <br />
				<br />
				<h4>
					<b>用户注册</b>
				</h4>
				<input type="hidden" id="res_check_user_info"/>
				<input type="hidden" id="show_res_info" value="${register_info }"/>
				<div class="form-group">
					<label for="inputUsername" class="col-sm-3 control-label">用户名</label>
					<div class="col-sm-6">
						<input type="text" class="form-control inputWidth250" id="inputUsername"
							placeholder="用户名" required="required" name="userName" onkeyup="checkUserName(this)"
							 onblur="javascript:checkUserName(this)" value="${res_user.userName }">
						<span id="res_user_name_hava" class="error_info"></span>
					</div>
				</div>
				<div class="form-group">
					<label for="inputUserNickName" class="col-sm-3 control-label">昵称</label>
					<div class="col-sm-6">
						<input type="text" class="form-control inputWidth250" id="inputUserNickName"
							placeholder="昵称" required="required" name="userNickName" value="${res_user.userNickName }">
					</div>
				</div>
				<div class="form-group">
					<label for="inputSex" class="col-sm-3 control-label">性别</label>
					<div class="col-sm-2 reg_sexAlgin">
						<input type="radio" name="sex" id="inputSex1"
							required="required" value="男" checked="checked">男
						<input type="radio" name="sex" id="inputSex2"
						    required="required" value="女">女	
					</div>
				</div>
				
				<div class="form-group">
					<label for="inputPassword1" class="col-sm-3 control-label">密码</label>
					<div class="col-sm-6">
						<input type="password" class="form-control inputWidth250" id="inputPassword1"
							placeholder="密码" required="required" name="password"  value="${res_user.password }">
					</div>
				</div>
				<div class="form-group">
					<label for="inputPassword2" class="col-sm-3 control-label">确认密码</label>
					<div class="col-sm-6">
						<input type="password" class="form-control inputWidth250" id="inputPassword2"
							placeholder="确认密码" required="required" name="password2">
					</div>
				</div>
				<div class="form-group">
					<label for="inputBirthday" class="col-sm-3 control-label">生日</label>
					<div class="col-sm-6">
						<input type="text" class="form-control inputWidth250" id="inputBirthday"
							placeholder="生日" required="required" name="birthday"
							 value=<fmt:formatDate value="${res_user.birthday}" pattern="yyyy-MM-dd"/>>
					</div>
				</div>
			
				<div class="form-group">
					<label for="inputEmail" class="col-sm-3 control-label">邮箱</label>
					<div class="col-sm-6">
						<input type="text" id="inputEmail" class="form-control inputWidth250" name="email" placeholder="邮箱"
							 required="required" value="${res_user.email }">
					</div>
				</div>
				<div class="form-group">
					<label for="inputCheckCode" class="col-sm-3 control-label">验证码</label>
					<div class="col-sm-6">
						 <img title="验证码不区分大小写,点击更换" id="reg_validateCodeImg" onclick="refresh(this)" src="${pageContext.request.contextPath}/validateCode?action=getCode">
						
						<input type="text" id="inputCheckCode" class="form-control"
							 required="required" placeholder="验证码" name="inCode">
						<input type="hidden" id="currentValidateCode" value="${RANDOMVALIDATECODEKEY }"/>
						<span style="float:left;color:gray;font-size:12px;display: inline-block;margin-top: 5px;">验证码不区分大小写</span>
					   </div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-3 col-sm-3">
						<input type="submit" class="btn btn-primary btn-lg btn-block" value=" 加入朋友社区  "/>
					</div>
				</div>
				<div class="form-group">
					<label for="inputAgree" class="col-sm-3"></label>
					<div class="col-sm-3 reg_inputAgreeStyle">
						<input type="checkbox" id="inputAgree"
						 required="required">
						 <span>我接受 <a href="javascript:void(0);" id="a_show_friend_txt">朋友社区 用户条款和协议</a></span>
					</div>
				</div>
			</form>

		</div>
		<br/>
	</div>
   <div id="friend_protocol">
   		<div class="protocol">
   			<center><b>朋友社区用户协议</b></center><br/>
   			【用户行为规范】<br/>

 			您在使用本服务时不得利用本服务从事以下行为，包括但不限于：<br/>

			（1）发布、传送、传播、储存违反国家法律、危害国家安全统一、社会稳定、公序良俗、社会公德以及侮辱、诽谤、淫秽、暴力的内容；<br/>
			
			（2）发布、传送、传播、储存侵害他人名誉权、肖像权、知识产权、商业秘密等合法权利的内容；<br/>
			
			（3）虚构事实、隐瞒真相以误导、欺骗他人；<br/>
			
			（4）发表、传送、传播广告信息及垃圾信息；<br/>
			
			（5）从事其他违反法律法规、政策及公序良俗、社会公德等的行为。<br/>
   			 拓展商议...
   		</div>
		<div class="friend_protocol_btn">
			<a class="btn btn-primary btn-sm" id="a_sure">　　确认　　</a>
		</div>
   		
   </div>
  <script type="text/javascript">
  		$(function(){
  			var friend_protocol = $("#friend_protocol");
  			friend_protocol.hide();
  			$("#inputBirthday").datepicker({
  				yearRange : "1900:2100",
  				dateFormat : "yy-mm-dd",
  				changeYear : true,
  				changeMonth : true,
  				autoSize : true
  			});
  			
  			$("#a_show_friend_txt").click(function(){
  				friend_protocol.show().dialog({
  					title: '朋友社区协议',    
  				    width: 970,    
  				    height:330,
  				    left: (window.screen.width-970)/2,
  				    top : (window.screen.height-330)/2,
  				    modal: true   
  				});
  			});
  			
  			$("#a_sure").click(function(){
  				friend_protocol.dialog('close');
  				
  			});
  			
  			$("#reg_form").validate({
  				rules:{
  					userName:{
  						required:true,
  						userNameNoChinese:true,
  						minlength:6,
  						maxlength:16
  					},
  					userNickName:{
  						required:true,
  						minlength:1,
  						maxlength:5	
  					},
  					password:{
  						required:true,
  						minlength:6,
  						maxlength:16
  					},
  					password2:{
  						required:true,
  						minlength:6,
  						maxlength:16,
  						equalTo:"#inputPassword1"
  					},
  					email:{
  						required:true,
  						email:true
  					},
  					birthday:{
  						required:true,
  						dateISO:true
  					}
  					
  				},
  				messages:{
  					userName:{
  						required:"您的用户名不能够为空",
  						userNameNoChinese:"请使用英文加数字的用户名",
  						minlength:"您的用户名不能够少于6个字符",
  						maxlength:"您的用户名不能够多于16个字符"
  						
  					},
  					userNickName:{
  						required:"您的昵称不能够为空",
  						minlength:"您的昵称不能够少于1个字符",
  						maxlength:"您的昵称不能够多于5个字符"	
  					},
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
  					email:{
  						required:"您的邮箱不能为空",
  						email:"您的邮箱输入不正确"
  					},
  					birthday:{
  						required:"您的生日日期不能为空",
  						dateISO:"您的生日日期格式不正确"
  					}
  				}
  				
  			});
  			
  			//弹出提示信息
  			if($("#show_res_info").val().length > 0){
  				alert($("#show_res_info").val());
  			}
  			
  			
  		});
  		function refresh(obj) {
  	        obj.src = getRootPath()+"/validateCode?action=getCode&rand="+Math.random();
  	    }
  		//加入验证用户名是英文和数字的的验证规则
		$.validator.addMethod("userNameNoChinese",function(value,element,params){
		  	//alert(value);
			parttern=/([a-zA-Z0-9]+)+$/;
			if(!parttern.test(value)){
				return false;
			}
			return true;
		});
  		//验证用户名是否在数据库中已经存在
  		function checkUserName(t){
  			var inUserName = $(t).val();
  			$("#res_user_name_hava").text("")
  			if(inUserName != "" && inUserName.length >= 6){
  				$.ajax({
  					url: getRootPath() + "/ajaxServlet",
  					data:{"action":"checkUserName","inName":$(t).val()},
  					type:'post',
  					dataType:"json",
  					error:function(res){
  						alert("ajax失败");
  					},
  					success:function(res){
  						var msg = res.msg;
  						if(msg == "T"){
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
  </script>
  </body>
</html>
