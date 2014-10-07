<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<jsp:include page="../../public/common.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=basePath%>js/jquery.validate.min.js"></script>
<style type="text/css">
#newManagerTable tr {
	height: 25px;
	line-height: 25px;
}
body{
	background-color: white;
}
.error_info {
	color: red;
}
</style>

<div>
	<div
		style="height: 30px; line-height: 30px; font-size: 15px; color: black;">用户管理
		- 子管理员操作</div>
		<!--  
	<span style="display: block; padding-left: 20px; margin-bottom: 10px;">
		下级管理员操作中，主要是对当前已经有的管理员的角色进行授权和取消授权。<br />
		如果有需要的话，可以创建下级管理员（该操作只有超级管理员才有权限）。
	</span>
	-->
	<c:forEach items="${user.roleList }" var="role">
		<input type="hidden" value="${role.roleGrade }" class="userRoles" />
	</c:forEach>
	
	<div class="mgang_toolbar">
		<a class="btn btn-primary btn-sm" id="btn_newManager">创建下级管理员</a>
	</div>
	

	<div
		style="display: inline-block; float: left; margin-left:10px; padding: 10px;">
		<table class="table table-hover table-condensed table-bordered" style="text-align: center;">
			<tr>
				<td align="center">管理员用户名</td>
				<td align="center">拥有的角色</td>
				<td align="center">操作</td>
			</tr>
			<c:forEach items="${admins }" var="admin">
				<tr>
					<td>${admin.userName }
						<input type="hidden" value="" id="admin${admin.userId }"/>
					</td>
					<td>
						<c:forEach items="${admin.roleList }" var="role">
							<span class="adminRoleGrades" adminId="${admin.userId}" roleId="${role.roleId }" roleGrade="${role.roleGrade }">${role.roleName }</span>
						</c:forEach>
					</td>
					<td>
						<a href="javascript:void(0);" onclick="changeRole('${admin.userId}','${admin.userName }');">编辑</a>
					</td>
				</tr>
			</c:forEach>

		</table>
		<span style="display: inline-block; float: right;">现在系统共有${fn:length(admins)}位管理员</span>
	</div>
	<div id="changeRoleDiv" style="display: none; font-size:15px;padding:20px;background-color: white;">
		<form action="${pageContext.request.contextPath}/admin.do?action=changeRole" method="post">
			<input type="hidden" value="" id="userId" name="userId" />
			<table class="table table-condensed">
				<tr>
					<td>管理员用户名:</td>
					<td><span id="adminName"></span></td>
				</tr>
				<tr>
					<td>角色改变:</td>
					<td>
						<% int i=0; %>
						<c:forEach items="${roles }" var="role">
						<% i++; %>
							<input type="checkbox" id="role${role.roleId }" name="roleId" value="${role.roleId }"/>${role.roleName }
						<% if(i%2==0){
							%>
								<br/>
							<%
						}
						%>
						</c:forEach>
					</td>
					
				</tr>
				<tr>
					<td><button type="submit" class="btn btn-sm btn-primary">改变角色</button></td>
					<td><button type="reset" id="hiddenBtn"  class="btn btn-sm btn-primary">取消</button></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="newManagerDialog">
	<form id="newManagerForm" onsubmit="return goSubmit();"
		style="display: inline-block; float: left;"
		action="${pageContext.request.contextPath}/admin.do?action=createNewManager"
		method="post">
		
		<input type="hidden" id="loginAdminGrade" value="${user.adminGrade }" />
		<input type="hidden" id="res_check_user_info" /> <input type="hidden"
			value="${res_info }" id="res_info" />
		<table id="newManagerTable" class="table table-hover table-condensed table-bordered">
			<tr>
				<td align="right">管理员用户名:</td>
				<td><input type="text" name="userName" onkeyup="checkUserName(this)"
					onblur="checkUserName(this)" required="required">
					<span id="res_user_name_hava" class="error_info"></span></td>
			</tr>
			<tr>
				<td align="right">管理员密码:</td>
				<td><input type="password" id="inputPassword1" name="password"
					required="required" /></td>
			</tr>
			<tr>
				<td align="right">管理员确认密码:</td>
				<td><input type="password" name="password2" required="required" /></td>
			</tr>
			<tr>
				<td align="right">授予的角色:</td>
				<td><select name="roleId" required="required">
						<c:forEach items="${roles }" var="role">
							<c:if test="${role.roleGrade >= 3 }">
								<option value="${role.roleId }"
									<c:if test="${role.roleId eq 102 }"> selected="selected"</c:if>>${role.roleName }</option>
							</c:if>
						</c:forEach>
				</select></td>
			</tr>
			
			<tr>
				<td>　　<button type="submit" class="btn btn-sm btn-primary">完成创建</button></td>
				<td><button id="cancleBtn" type="reset" class="btn btn-sm btn-primary">重置</button></td>
			</tr>
		</table>
	</form>
	</div>
</div>

<script type="text/javascript">
	
	$(function(){
		var res_info = $("#res_info").val();
		
		var loginAdminGrade = $("#loginAdminGrade").val();
		if (res_info != "") {
			alert(res_info);
		}
		
		var newManagerDialog = $("#newManagerDialog");
		newManagerDialog.hide();
		
		$("#btn_newManager").bind("click",function(){
			newManagerDialog.show().dialog({
				title:"创建下级管理员",
				width:560,
				height:300,
				modal:true
			});
		});
		
		$("#hiddenBtn").bind("click",function(){
			$("#changeRoleDiv").dialog("close");
		});
		$(".adminRoleGrades").each(function(index,dom){
			var roleGrade = $(this).attr("roleGrade");
			var roleId = $(this).attr("roleId");
			var adminId = $(this).attr("adminId");
			var roleIds = $("#admin"+adminId).val();
			roleIds += roleId+",";
			if(loginAdminGrade < 4){
				if(roleGrade == 4){
					//说明该管理员拥有超级管理员角色
					$(this).parent().parent().find("a").hide();
				}
			}	
			$("#admin"+adminId).val(roleIds);
		});
		
		$("#newManagerForm").validate({
			rules : {
				userName : {
					required : true,
					userNameNoChinese : true,
					minlength : 6,
					maxlength : 16
				},
				password : {
					required : true,
					minlength : 6,
					maxlength : 16
				},
				password2 : {
					required : true,
					minlength : 6,
					maxlength : 16,
					equalTo : "#inputPassword1"
				},
				roleId : {
					required : true
				}
			},
			messages : {
				userName : {
					required : "管理员用户名不能够为空",
					userNameNoChinese : "请使用英文加数字的用户名",
					minlength : "管理员用户名不能够少于6个字符",
					maxlength : "管理员用户名不能够多于16个字符"

				},
				password : {
					required : "管理员密码不能够为空",
					minlength : "管理员密码长度不能够少于6个字符",
					maxlength : "管理员密码长度不能够多于16个字符"
				},
				password2 : {
					required : "管理员确认密码不能够为空",
					minlength : "管理员确认密码长度不能够少于6个字符",
					maxlength : "管理员确认密码长度不能够多于16个字符",
					equalTo : "两次密码输入不一致"
				},
				roleId : {
					required : "必须给予角色"
				}
			}
		});
	});
	//加入验证用户名是英文和数字的的验证规则
	$.validator.addMethod("userNameNoChinese",
			function(value, element, params) {
				//alert(value);
				parttern = /([a-zA-Z0-9]+)+$/;
				if (!parttern.test(value)) {
					return false;
				}
				return true;
			});
	//验证用户名是否在数据库中已经存在
	function checkUserName(t) {
		var inUserName = $(t).val();
		$("#res_user_name_hava").text("")
		if (inUserName != "" && inUserName.length >= 6) {
			$.ajax({
				url : getRootPath() + "/ajaxServlet",
				data : {
					"action" : "checkUserName",
					"inName" : $(t).val()
				},
				type : 'post',
				dataType : "json",
				error : function(res) {
					alert("ajax失败");
				},
				success : function(res) {
					var msg = res.msg;
					if (msg == "T") {
						$("#res_user_name_hava").text("该用户名已存在!");
						//alert("该用户名已存在!");
						//用户已存在
						$("#res_check_user_info").attr('value', "该用户名已存在!");
					} else {
						$("#res_user_name_hava").text("恭喜，该用户名可以使用!")
						//alert("恭喜，该用户名可以使用!");
						$("#res_check_user_info").attr('value', "");
					}
				}
			});
		}
	}
	function goSubmit() {
		var isExistInfo = $("#res_check_user_info").val();
		if (isExistInfo.length > 0) {
			alert(isExistInfo);
			return false;
		}
		return true;
	}
	function changeRole(userId,userName){
		//alert(userId+userName);
		//alert($("#admin"+userId).val());
		$("#changeRoleDiv").show();
		$("input[type=checkbox]").attr("checked",false);
		$("#adminName").text(userName);
		$("#userId").val(userId);
		var roleIdsStr = $("#admin"+userId).val();
		var roleIds = roleIdsStr.substring(0,roleIdsStr.length-1).split(",");
		for(var i=0;i<roleIds.length;i++){
			var roleId = roleIds[i];
			$("#role"+roleId).attr("checked","checked");
		}
		$("#changeRoleDiv").show().dialog({
			title:"角色变更",
			width:500,
			height:300,
			modal:true
			
		});
	}
</script>
