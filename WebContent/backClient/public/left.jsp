<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!-- 
	author :songqidong
	2014-8-3
 -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>朋友社区后台管理系统</title>
<style type="text/css">
<!--
body {
	padding: 0px;
	margin: 0px;
	background-image: url(../image/left.gif);
}
-->
</style>
<link href="<%=basePath%>/backClient/css/css.css" rel="stylesheet"
	type="text/css" />
<jsp:include page="../../public/common.jsp"></jsp:include>
</head>
<script type="text/javascript">
	function tupian(idt) {
		var nametu = "xiaotu" + idt;
		var tp = document.getElementById(nametu);
		tp.src = "../image/ico05.gif";//图片ico04为白色的正方形

		for (var i = 1; i < 30; i++) {

			var nametu2 = "xiaotu" + i;
			if (i != idt * 1) {
				var tp2 = document.getElementById('xiaotu' + i);
				if (tp2 != undefined) {
					tp2.src = "../image/ico06.gif";
				}//图片ico06为蓝色的正方形
			}
		}
	}

	function list(idstr) {
		var name1 = "subtree" + idstr;
		var name2 = "img" + idstr;
		var objectobj = document.all(name1);
		var imgobj = document.all(name2);

		//alert(imgobj);

		if (objectobj.style.display == "none") {
			for (i = 1; i < 10; i++) {
				var name3 = "img" + i;
				var name = "subtree" + i;
				var o = document.all(name);
				if (o != undefined) {
					o.style.display = "none";
					var image = document.all(name3);
					//alert(image);
					image.src = "../image/ico04.gif";
				}
			}
			objectobj.style.display = "";
			imgobj.src = "../image/ico03.gif";
		} else {
			objectobj.style.display = "none";
			imgobj.src = "../image/ico04.gif";
		}
	}
</SCRIPT>

<body>
	<input type="hidden" value="${user.adminGrade }" id="adminGrade"/>
	<table width="198" border="0" cellpadding="0" cellspacing="0"
		class="left-table01">
		<tr>
			<TD>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="207" height="55" background="../image/nav01.gif">
							<table width="90%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td width="25%" rowspan="2"><img src="../image/ico02.gif"
										width="35" height="35" /></td>
									<td width="75%" height="22" class="left-font01">您好，<span
										class="left-font02">${user.userName }</span></td>
								</tr>
								<tr>
									<td height="22" class="left-font01">[&nbsp;<a
										href="${pageContext.request.contextPath}/admin.do?action=logout"
										target="_top" class="left-font01">退出</a>&nbsp;]
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table> <!--  任务系统开始    -->
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="left-table03">
					<tr>
						<td height="29">
							<table width="85%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td width="8%"><img name="img8" id="img8"
										src="../image/ico06.gif" width="8" height="11" /></td>
									<td width="92%"><a href="${pageContext.request.contextPath}/admin.do?action=toBackUI"
							target="mainFrame"	class="left-font03" onClick="">后台首页</a></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				
				<TABLE id="userManager" style="display: none;" width="100%" border="0" cellpadding="0" cellspacing="0"
					class="left-table03">
					<tr>
						<td height="29">
							<table width="85%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td width="8%"><img name="img8" id="img8"
										src="../image/ico04.gif" width="8" height="11" /></td>
									<td width="92%"><a href="javascript:void(0);"
										class="left-font03" onClick="list('8');">用户管理</a></td>
								</tr>
							</table>
						</td>
					</tr>
				</TABLE>
				<table id="subtree8" style="DISPLAY: none" width="80%" border="0"
					align="center" cellpadding="0" cellspacing="0" class="left-table02">
					<tr>
						<td width="9%" height="20"><img id="xiaotu20"
							src="../image/ico06.gif" width="8" height="12" /></td>
						<td width="91%"><a
							href="${pageContext.request.contextPath}/admin.do?action=toHiddenUserUI"
							target="mainFrame" class="left-font03" onClick="tupian('20');">屏蔽用户</a></td>
					</tr>
					<tr>
						<td width="9%" height="21"><img id="xiaotu21"
							src="../image/ico06.gif" width="8" height="12" /></td>
						<td width="91%"><a
							href="${pageContext.request.contextPath}/role.do?action=toRoleUI"
							target="mainFrame" class="left-font03" onClick="tupian('21');">角色管理</a></td>
					</tr>
					<tr>
						<td width="9%" height="20"><img id="xiaotu25"
							src="../image/ico06.gif" width="8" height="12" /></td>
						<td width="91%"><a
							href="${pageContext.request.contextPath}/admin.do?action=toPermissionUI"
							target="mainFrame" class="left-font03" onClick="tupian('25');">权限管理</a></td>
					</tr>
					<tr>
					<tr>
						<td width="9%" height="20"><img id="xiaotu7"
							src="../image/ico06.gif" width="8" height="12" /></td>
						<td width="91%"><a
							href="${pageContext.request.contextPath}/admin.do?action=toAdminManageUI"
							target="mainFrame" class="left-font03" onClick="tupian('7');">子管理员操作</a></td>
					</tr>

				</table> <!--  任务系统结束    --> <!--  消息系统开始    -->
				<TABLE id="bbsManager" style="display: none;" width="100%" border="0" cellpadding="0" cellspacing="0"
					class="left-table03">
					<tr>
						<td height="29">
							<table width="85%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td width="8%"><img name="img7" id="img7"
										src="../image/ico04.gif" width="8" height="11" /></td>
									<td width="92%"><a href="javascript:void(0);"
										class="left-font03" onClick="list('7');">论坛管理</a></td>
								</tr>
							</table>
						</td>
					</tr>
				</TABLE>
				<table id="subtree7" style="DISPLAY: none" width="80%" border="0"
					align="center" cellpadding="0" cellspacing="0" class="left-table02">
					<tr>
						<td width="9%" height="20"><img id="xiaotu17"
							src="../image/ico06.gif" width="8" height="12" /></td>
						<td width="91%"><a
							href="${pageContext.request.contextPath}/module.do?action=findAllModule&toWhere=back"
							target="mainFrame" class="left-font03" onClick="tupian('17');">论坛管理</a></td>
					</tr>
				</table> <!--  消息系统结束    -->



				<TABLE id="systemManager" style="display: none;" width="100%" border="0" cellpadding="0" cellspacing="0"
					class="left-table03">
					<tr>
						<td height="29">
							<table width="85%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td width="8%"><img name="img1" id="img1"
										src="../image/ico04.gif" width="8" height="11" /></td>
									<td width="92%"><a href="javascript:void(0);"
										class="left-font03" onClick="list('1');">系统管理</a></td>
								</tr>
							</table>
						</td>
					</tr>
				</TABLE>
				<table id="subtree1" style="DISPLAY: none" width="80%" border="0"
					align="center" cellpadding="0" cellspacing="0" class="left-table02">
					<tr>
						<td width="9%" height="20"><img id="xiaotu1"
							src="../image/ico06.gif" width="8" height="12" /></td>
						<td width="91%"><a href="${pageContext.request.contextPath}/sysParam.do?action=toSysSettingUI" target="mainFrame"
							class="left-font03" onClick="tupian('1');">系统参数管理</a></td>
					</tr>
				</table>

			</TD>
		</tr>

	</table>
	<script type="text/javascript">
		$(function(){
			var adminGrade = $("#adminGrade").val();
			var userManager = $("#userManager");
			var bbsManager = $("#bbsManager");
			var systemManager = $("#systemManager");
			if(adminGrade == 4){
				userManager.show();
				bbsManager.show();
				systemManager.show();
			}else if(adminGrade == 3){
				userManager.show();
			}else if(adminGrade == 2){
				systemManager.show();
			}else if(adminGrade == 1){
				bbsManager.show();
			}
		});
	</script>
</body>
</html>