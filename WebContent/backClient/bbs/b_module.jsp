<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!-- 
	author : chenqiang
	2014-7-22
 -->
<%@page import="com.friend.vo.Module"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<jsp:include page="../../public/common.jsp"></jsp:include>
<link href="<%=basePath%>css/col_menu.css" type="text/css"
	rel="stylesheet" />
<link href="<%=basePath%>css/user/user_selfInfo.css" type="text/css"
	rel="stylesheet" />
<link href="<%=basePath%>css/bbs/b_module.css" type="text/css"
	rel="stylesheet" />
<style type="text/css">
body {
	background-color: white;
}
</style>


<div class="self_info_title"
	style="height: 30px; line-height: 30px; font-size: 15px;">
	论坛管理 - 板块管理 
</div>
<div class="mgang_toolbar">
	<a id="btn_create_module"
		class="btn btn-primary btn-sm" style="margin-right: 20px;">创建板块</a>
</div>
<div style="min-height: 345px;">
	<table class="table table-hover table-condensed table-bordered"
		style="text-align: center;">
		<tr align="center">
			<th style="text-align: center;" width="10%">板块编号</th>
			<th style="text-align: center;" width="20%">板块标题</th>
			<th style="text-align: center;" width="8%">主贴数</th>
			<th style="text-align: center;" width="18%">创建人</th>
			<th style="text-align: center;" width="25%">创建时间</th>
			<th style="text-align: center;" width="19%">操作</th>
		</tr>
		<c:forEach items="${listModules }" var="m">
			<tr>
				<td>${m.moduleId }</td>
				<td><b> <a
						href="${pageContext.request.contextPath}/topic.do?action=showTopicInModule&moduleId=${m.moduleId }&toWhere=back">${m.moduleName }</a>
				</b></td>
				<td>${m.topicCount}</td>
				<td>${m.user.userName }</td>
				<td><fmt:formatDate value="${m.createTime }" type="both" /></td>
				<td style="text-align: center; height: 30px"><a
					href="${pageContext.request.contextPath}/topic.do?action=showTopicInModule&moduleId=${m.moduleId }&toWhere=back">进入板块</a>
					<a onclick="return confirmDelete();"
					href="${pageContext.request.contextPath}/module.do?action=deleteModule&id=${m.moduleId }&toWhere=back">删除</a></td>


			</tr>
		</c:forEach>
	</table>
</div>
<div align="center">
	<jsp:include page="../../public/pager.jsp">
		<jsp:param
			value="${pageContext.request.contextPath}/module.do?action=findAllModule&toWhere=back"
			name="path" />
	</jsp:include>
</div>
<!-- easyui的dialog -->
<div id="create_module_dialog">
	<form onsubmit="return goSubmit();"
		action="module.do?action=createModule&userId=${user.userId }&toWhere=back"
		id="moduleTitle" method="post">
		<input type="hidden" id="check_module_name" value="" />
		板块名称：<input type="text" placeholder="板块名称" id="moduleName" required="required"
			name="moduleName" onblur="javascript:checkModuleName(this);" value="" /><br/> <br/> 
		<span id="res_module_name_hava" class="error"></span> <br/><br/><input
			type="submit" id="btn_update" class="btn btn-primary btn-sm" value="确定"> <input type="reset"
			id="btn_cancel" class="btn btn-primary btn-sm" value="重置">
	</form>
</div>
<script type="text/javascript">
	$(function() {
		var create_module_dialog = $('#create_module_dialog');
		create_module_dialog.hide();
		/*完善个人信息对话框*/
		$("#btn_create_module").click(function() {
			create_module_dialog.show().dialog({
				title : '创建板块 ',
				width : 300,
				height : 200,
				modal : true,
			});
		});
		$("#btn_update").click(function() {

		});
		$("#btn_cancle").click(function() {
			create_module_dialog.dialog('close');
		});
	});
	function checkModuleName(t) {
		var inModuleName = $(t).val();
		$("#res_module_name_hava").text("");
		if (inModuleName != "") {
			$.ajax({
				url : getRootPath() + "/ajaxServlet",
				data : {
					"action" : "checkModuleName",
					"inName" : $(t).val()
				},
				type : 'post',
				dataType : "json",
				success : function(res) {
					var msg = res.msg;
					if (msg == "T") {
						$("#res_module_name_hava").text("该板块已存在!");
						$("#check_module_name")
								.attr('value', "该板块已经存在，无法再次创建!");
					} else {
						$("#res_module_name_hava").text("恭喜!该板块可以创建");
						$("#check_module_name").attr('value', "");
					}
				}
			});
		}
	}
	function goSubmit() {
		var isExistInfo = $("#check_module_name").val();
		if (isExistInfo.length > 0) {
			alert(isExistInfo);
			return false;
		}
		return true;
	}
	function confirmDelete() {
		if (confirm("您真的要删除吗?")) {
			return true;
		} else {
			return false;
		}
	}
</script>


