<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!-- 
	author : chenqiang
	2014-7-22
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
<title>朋友社区bbs板块列表</title>
<jsp:include page="../../public/common.jsp"></jsp:include>
<link href="<%=basePath%>css/user/user_selfInfo.css" type="text/css"
	rel="stylesheet" />
<link href="<%=basePath%>css/bbs/module.css" type="text/css"
	rel="stylesheet" />
<script type="text/javascript" src="<%=basePath%>js/jquery.validate.min.js"></script>
</head>
<body>
	<jsp:include page="../../public/nav.jsp"></jsp:include>
	<jsp:include page="../../public/menu.jsp"></jsp:include>
	<div id="bbs_main_content">
	<!--  
		<div>
			朋友社区bbs logo 条 <a id="btn_create_module" class="btn btn-primary btn-sm">创建板块</a>
		</div>
	-->
		<div id="bbs_module_div">
			<span class="module_title">朋友社区论坛板块</span>
			<div class="module_content">
				<ul id="module_ul">
					<c:if test="${empty listModules }">
						<li>暂无板块</li>
					</c:if>
					<c:forEach items="${listModules }" var="module">
						<li><a title="点此进入板块" href="${pageContext.request.contextPath}/topic.do?action=showTopicInModule&moduleId=${module.moduleId}">
							<img alt="" src="${pageContext.request.contextPath}/image/module_html.jpg">
							<span class="module_name">
								${module.moduleName }　<br/>
								<gray>${module.topicCount }</gray>
							</span>
							</a>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div align="center">
			<jsp:include page="../../public/pager.jsp">
				<jsp:param value="${pageContext.request.contextPath}/module.do?action=findAllModule" name="path"/>
			</jsp:include>	
			</div>	
	</div>				
	<!-- easyui的dialog -->
	<div id="create_module_dialog">
		<form class="form-horizontal" role="form" onsubmit="return goSubmit();"
			action="module.do?action=createModule&userId=${user.userId }"
			id="moduleTitle"
			method="post"><br>
			<input type="hidden" id="check_module_name" value=""/>
			<div class="form-group">
				<label for="phone" class="col-sm-2 control-label">板块标题</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" placeholder="标题" id="moduleName" required="required"
						name="moduleName"  onblur="javascript:checkModuleName(this);" value=""/>
						<span id="res_module_name_hava" class="error"></span>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="submit" id="btn_update" class="btn btn-primary btn-sm"
						value="确定">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="reset" id="btn_cancel"
						class="btn btn-primary btn-sm" value="重置">
				</div>
			</div>
		</form>
	</div>
	<jsp:include page="../../public/ownner.jsp"></jsp:include>
	<script type="text/javascript">
		$(function() {
			var create_module_dialog = $('#create_module_dialog');
			create_module_dialog.hide();
			/*完善个人信息对话框*/
			$("#btn_create_module").click(function() {
				create_module_dialog.show().dialog({
					title : '请创建板块 ',
					width : 550,
					height : 250,
					modal : true,
					left : 400,
					top : 150
				});
			});
			$("#btn_update").click(function() {

			});
			$("#btn_cancle").click(function() {
				create-module-dialog.dialog('close');
			});
		});
		function checkModuleName(t){
  			var inModuleName = $(t).val();
  			$("#res_module_name_hava").text("");
  			if(inModuleName != ""){
  				$.ajax({
  					url: getRootPath() + "/ajaxServlet",
  					data:{"action":"checkModuleName","inName":$(t).val()},
  					type:'post',
  					dataType:"json",
  					success:function(res){
  						var msg = res.msg;
  						if(msg == "T"){
  							$("#res_module_name_hava").text("该板块已存在!");
  							$("#check_module_name").attr('value',"该板块已经存在，无法再次创建!");
  						}else{
  							$("#res_module_name_hava").text("恭喜!该板块可以创建");
  							$("#check_module_name").attr('value',"");
  						}
  					}
  				});
  			}
		}
		function goSubmit(){
 			var isExistInfo = $("#check_module_name").val();
 			if(isExistInfo.length > 0){
 				alert(isExistInfo);
 				return false;
 			}
 			return true;
 		}
	</script>

</body>
</html>
