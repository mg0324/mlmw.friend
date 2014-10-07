<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>个人信息</title>
<link href="<%=basePath%>css/user/user_selfInfo.css" type="text/css" rel="stylesheet" />
<jsp:include page="../../public/common.jsp"></jsp:include>
<link href="<%=basePath%>datepicker/jquery-ui.min.css" type="text/css" rel="stylesheet" />
<script class="resources library" src="<%=basePath%>js/area.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>datepicker/datepicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/md5.js"></script>



</head>
<body>
	<jsp:include page="../../public/nav.jsp"></jsp:include>
	<!-- 加入menu -->
	<jsp:include page="../../public/menu.jsp"></jsp:include>
	<div id="self_main_content">
		<div id="self_main_content_left">
			<div id="self_main_content_headArea">
				<span class="self_mgang_test_head"> <img alt=""
					src="${user.head }" width="160px" height="160px">
				</span>
				<ul id="main_content_left_function_list">
					<li><a href="javascript:void(0)"
						onclick="showPanel('self_info')">个人资料</a></li>
					<li><a href="javascript:void(0)"
						onclick="showPanel('upload_head')">修改头像</a></li>
					<li><a href="javascript:void(0)"
						onclick="showPanel('update_password')">修改密码</a></li>
					<li><a href="javascript:void(0)"
						onclick="showPanel('set_password_safe')">设置密保问题</a></li>
				</ul>
			</div>
		</div>
		<div id="self_main_content_center">

			<div class="panel panel-default" id="self_info">
				<div class="panel-heading">
					<h3 class="panel-title">个人资料</h3>
				</div>
				<div class="panel-body">
					<div class="self_info_title">
						<span class="btn_update_info"><a id="btn_fill_info"
							class="btn btn-primary btn-sm">完善个人资料</a></span>
					</div>
					<div>
						<span class="col_title">用户名:</span><span class="col_value">${user.userName }</span>
						<br /> <span class="col_title">昵称:</span><span class="col_value">${user.userNickName }</span>
						<br /> <span class="col_title">生日:</span> <span class="col_value">
							<fmt:formatDate value="${user.birthday}" pattern="yyyy-MM-dd" />
						</span> <br /> <span class="col_title">性别:</span><span class="col_value">${user.sex }</span>
						<br /> <span class="col_title">邮箱:</span> <span class="col_value">${user.email }</span>
						<br /> <span class="col_title">手机:</span> <span class="col_value">
							<c:if test="${empty user.phone }">
								<!-- red标签在common.css中定义的 -->
								<red>未设置</red>
							</c:if> ${user.phone }
						</span> <br /> <span class="col_title">个性签名:</span><span
							class="col_value"> <c:if test="${empty user.selfWrite }">
								<red>未设置</red>
							</c:if> ${user.selfWrite }
						</span><br /> <span class="col_title">爱好:</span> <span class="col_value">
							<c:if test="${empty user.hobbies }">
								<red>未设置</red>
							</c:if> ${user.hobbies }
						</span> <br /> <span class="col_title">出生地:</span> <span
							class="col_value"> 
							<c:if test="${empty user.homeTown }">
								<red>未设置</red>
							</c:if> ${user.homeTown }　　
							<a href="javascript:void(0);" id="a_update_home_town"> 修改 </a>
						</span> <br />

					</div>
				</div>
			</div>
			<div class="panel panel-default" id="upload_head">
				<div class="panel-heading">
					<h3 class="panel-title">上传头像</h3>
				</div>
				<div class="panel-body">
					<form enctype="multipart/form-data" method="post"
						id="upload_head_form"
						action="${pageContext.request.contextPath}/user.do?action=uploadHead">
						<img src="${user.head }" class="pull-left current_head_show" />
						<div class="to_bottom_other">
							<span style="color:gray;">
								当前系统只支持jpg,png,gif后缀的图像文件。<br/>
								只支持2M或2M以内的图片文件。
							</span>
						</div>
						<input type="hidden" value="${user.userId }" name="userId" /> <input
							type="hidden" value="${user.userName }" name="userName" /> <input
							type="file" class="pull-left" name="headFile" accept="image/*" />
						
						<button class="btn btn-primary btn-sm btn_upload_head"
							type="submit">上传头像</button>
					</form>
				</div>
			</div>
			<div class="panel panel-default" id="update_password">
				<div class="panel-heading">
					<h3 class="panel-title">修改密码</h3>
				</div>
				<div class="panel-body">
					<form role="form" class="form-horizontal" id="update_password_form"
						action="${pageContext.request.contextPath}/user.do?action=updatePassword"
						method="post" onsubmit="return checkPassword();">
						<input type="hidden" value="${user.userId }" name="userId" /> <input
							type="hidden" value="${user.password }" id="userPassword" />
						<div class="form-group">
							<label for="beforePassword" class="col-sm-2 control-label">原密码</label>
							<div class="col-sm-6">
								<input type="password" name="password" class="form-control"
									id="beforePassword" placeholder="原密码">
							</div>
						</div>
						<div class="form-group">
							<label for="newPassword" class="col-sm-2 control-label">新密码</label>
							<div class="col-sm-6">
								<input type="password" name="newPassword" class="form-control"
									id="newPassword" placeholder="新密码">
							</div>
						</div>
						<div class="form-group">
							<label for="newPassword2" class="col-sm-2 control-label">确认新密码</label>
							<div class="col-sm-6">
								<input type="password" name="newPassword2" class="form-control"
									id="newPassword2" placeholder="确认新密码">
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-3">
								<button type="submit" class="btn btn-primary btn-sm">修改密码</button>
							</div>
						</div>
					</form>
				</div>
			</div>

			<!-- 提示信息 -->
			<input id="alert_info" type="hidden" value="${alert_info }" />
			<div class="panel panel-default" id="set_password_safe">
				<div class="panel-heading">
					<h3 class="panel-title">设置密保问题</h3>
				</div>
				<div class="panel-body">
					<form class="form-horizontal" role="form" id="safe_password_form"
						action="${pageContext.request.contextPath}/user.do?action=setPwdSafe"
						method="post">
						<input type="hidden" value="${user.userId }" name="userId" />
						<div class="form-group">
							<label for="pwdQuestion" class="col-sm-2 control-label">密保问题</label>
							<div class="col-sm-6">
								<select class="form-control" name="pwdQuestion">
									<option value="你的出生地在哪？">你的出生地在哪？</option>
									<option value="你的生日是哪一天？">你的生日是哪一天？</option>
									<option value="你最喜欢什么运动？">你最喜欢什么运动？</option>
									<option value="你的偶像是谁？">你的偶像是谁？</option>
									<option value="你最喜欢动物是什么？">你最喜欢动物是什么？</option>
									<option value="你最喜欢的食物是什么？">你最喜欢的食物是什么？</option>
									<option value="你最喜欢的水果是什么？">你最喜欢的水果是什么？</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="pwdAnswer" class="col-sm-2 control-label">密保回答</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="pwdAnswer"
									id="pwdAnswer" required="required" placeholder="密保回答">
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" class="btn btn-default">提交密保</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div id="update_home_town_dialog">
		<form class="form-horizontal" role="form" onsubmit="return checkFrom()"
			action="${pageContext.request.contextPath}/user.do?action=updateHomeTown"
			 method="post">
			<input type="hidden" value="${user.userId }" name="userId" />
			<div class="form-group">
				<label for="inputHometown" class="col-sm-2 control-label">出生地</label>
				<div class="col-sm-8">
					<select class="form-control" id="province"
						style="width: 30%; float: left;" name="province"></select> 
					<select class="form-control" style="width: 30%; float: left;"
					    name="city" id="city"></select> 
					<select class="form-control" style="width: 30%; float: left;"
						name="town" id="town"></select>
					<script type="text/javascript">_init_area();</script>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit"
						class="btn btn-primary btn-sm">确认修改</button>
					<a href="javascript:void(0);" id="btn_home_town_cancel" class="btn btn-primary btn-sm">取消修改</a>
				</div>
			</div>
		</form>
	</div>
	
	<!-- easyui的dialog -->
	<div id="fill_self_info_dialog">
		<form class="form-horizontal" role="form"
			action="${pageContext.request.contextPath}/user.do?action=fillSelfInfo"
			id="fill_info_form" method="post">
			<input type="hidden" value="${user.userId }" name="userId" />
			<div class="form-group">
				<label for="userNickName" class="col-sm-2 control-label">昵称</label>
				<div class="col-sm-8">
					<input type="text" class="form-control"
						value="${user.userNickName }" id="userNickName" placeholder="昵称"
						name="userNickName">
				</div>
			</div>
			<div class="form-group">
				<label for="phone" class="col-sm-2 control-label">手机</label>
				<div class="col-sm-8">
					<input type="number" class="form-control" id="phone"
						value="${user.phone }" required="required" placeholder="手机"
						name="phone">
				</div>
			</div>
			<div class="form-group">
				<label for="inputBirthday" class="col-sm-2 control-label">生日</label>
				<div class="col-sm-6">
					<input type="text" class="form-control" id="inputBirthday"
						placeholder="生日" name="birthday" required="required"
						value=<fmt:formatDate value="${user.birthday}" pattern="yyyy-MM-dd"/>>
				</div>
			</div>
			<div class="form-group">
				<label for="selfWirte" class="col-sm-2 control-label">个性签名</label>
				<div class="col-sm-8">
					<input class="form-control" id="selfWirte"
						value="${user.selfWrite }" placeholder="个性签名" name="selfWrite" />

				</div>
			</div>
			<div class="form-group">
				<label for="hobbies" class="col-sm-2 control-label">爱好</label>
				<div class="col-sm-8">
					<input class="form-control" id="hobbies" value="${user.hobbies }"
						placeholder="爱好，请以逗号分隔" name="hobbies" />
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button id="btn_update" type="submit"
						class="btn btn-primary btn-sm">确认修改</button>
					<a href="javascript:void(0);" id="btn_info_cancel" class="btn btn-primary btn-sm">取消</a>
				</div>
			</div>
		</form>
	</div>
	
	<jsp:include page="../../public/ownner.jsp"></jsp:include>
	<script type="text/javascript">
		$(function(){
			$("div[class='panel panel-default']").hide();
			$("#self_info").show();
			var self_info_dialog = $('#fill_self_info_dialog');
			var update_home_town_dialog = $("#update_home_town_dialog");
			update_home_town_dialog.hide();
			self_info_dialog.hide();
		
			
			
			//给修改homeTown的超链接绑定处理函数
			$("#a_update_home_town").click(function(){
				update_home_town_dialog.show().dialog({
					title: '修改出身地',  
				    width: 600,  
				    height: 200,  
				    modal: true
				});
			});
			$("#btn_fill_info").click(function(){
				self_info_dialog.show().dialog({
					title: "完善个人信息",  
				    width:600,  
				    height:420,  
				    modal: true
				}); 
			});
			//弹出提示信息
			if($("#alert_info").val()!=""){
				alert($("#alert_info").val());
			}
			
			$("#btn_info_cancel").click(function(){
				self_info_dialog.dialog('close');
			});
			$("#btn_home_town_cancel").click(function(){
				update_home_town_dialog.dialog('close');
			});
			
			$("#inputBirthday").datepicker({
  				yearRange : "1900:2100",
  				dateFormat : "yy-mm-dd",
  				changeYear : true,
  				changeMonth : true,
  				autoSize : true
  			});
			
			$("#update_password_form").validate({
  				rules:{
  					password:{
  						required:true,
  						minlength:6,
  						maxlength:16
  					},
  					newPassword:{
  						required:true,
  						minlength:6,
  						maxlength:16
  					},
  					newPassword2:{
  						required:true,
  						minlength:6,
  						maxlength:16,
  						equalTo:"#newPassword"
  					}
  					
  				},
  				messages:{
  					password:{
  						required:"您的密码不能够为空",
  						minlength:"您的密码长度不能够少于6个字符",
  						maxlength:"您的密码长度不能够多于12个字符"
  					},
  					newPassword:{
  						required:"您的新密码不能够为空",
  						minlength:"您的新密码长度不能够少于6个字符",
  						maxlength:"您的新密码长度不能够多于12个字符"
  					},
  					newPassword2:{
  						required:"您的确认新密码不能够为空",
  						minlength:"您的确认新密码长度不能够少于6个字符",
  						maxlength:"您的确认新密码长度不能够多于12个字符",
  						equalTo:"您的两次新密码输入不一致"
  					}
  				}
  				
  			});
			
			$("#fill_info_form").validate({
  				rules:{
  					userNickName:{
  						required:true,
  						minlength:1,
  						maxlength:8	
  					},
  					birthday:{
  						required:true,
  						dateISO:true
  					},
  					phone:{
  						required:true,
  						phone:true,
  						maxlength:11
  					},
  					hobbies:{
  						required:true,
  						hobbies:true
  					}
  				},
  				messages:{
  					userNickName:{
  						required:"您的昵称不能够为空",
  						minlength:"您的昵称不能够少于1个字符",
  						maxlength:"您的昵称不能够多于8个字符"	
  					},
  					birthday:{
  						required:"您的生日日期不能为空",
  						dateISO:"您的生日日期格式不正确"
  					},
  					phone:{
  						required:"您的手机不能为空",
  						phone:"您的手机格式不正确",
  						maxlength:"您的手机号码不能多于11位"
  					},
  					hobbies:{
  						required:"您的爱好不能为空",
  						hobbies:"您的爱好格式不正确"
  					}
  				}
  				
  				
  			});
		});
		
		function showPanel(panel){
			$("div[class='panel panel-default']").hide();
			$("#"+panel).show();
		}
		
		function checkPassword(){
			//md5.js hex_md5加密函数
			if($("#userPassword").val() == hex_md5($("#beforePassword").val())){
				return true;
			}else{
				if($("#userPassword").val() != "")
					alert("原密码输入不正确!");  
			}
			return false;
		}
		
		//加入验证手机号码的验证规则
		$.validator.addMethod("phone",function(value,element,params){
		  	//alert(value);
			parttern=/(86)*0*1[358]\d{9}/;
			if(!parttern.test(value)){
				return false;
			}
			return true;
		  });
		//加入验证爱好的验证规则
		$.validator.addMethod("hobbies",function(value,element,params){
		  	//alert(value);
			parttern=/([a-zA-Z\u4e00-\u9fa5]+，[a-zA-Z\u4e00-\u9fa5]+)+$/;
			if(!parttern.test(value)){
				return false;
			}
			return true;
		  });
		
		function checkFrom(){
			var info = "请选择";
			var province = $("#province").val();
			var city = $("#city").val();
			var town = $("#town").val();
			if(province=="省"){
				info += " 省";
			}
			if(city=="市"){
				info += " 市";
			}
			if(town=="区"){
				info += " 区";
			}
			if(info.length > 3){
				alert(info);
				return false;
			}else{
				return true;
			}
		}
	</script>
</body>
</html>