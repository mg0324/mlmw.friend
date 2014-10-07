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
<title>写日志</title>
<jsp:include page="../../public/common.jsp"></jsp:include>
		<link href="<%=basePath%>css/bbs/add_topic.css" type="text/css" rel="stylesheet"/>
	  	
	  	<link href="<%=basePath%>datepicker/jquery-ui.min.css" type="text/css" rel="stylesheet"/>
	  	<script type="text/javascript" src="<%=basePath%>js/jquery.validate.min.js"></script>
	  	<script type="text/javascript" src="<%=basePath%>datepicker/datepicker.js"></script>
	</head>
	<body>
		<jsp:include page="../../public/nav.jsp"></jsp:include>
		<jsp:include page="../../public/menu.jsp"></jsp:include>
		<jsp:include page="../../public/fckeditor.jsp"></jsp:include>
		<div id="add_topic_div">
			<form role="form" id="write_log_form"
				action="${pageContext.request.contextPath}/log.do?action=updateLog" method="post">
			  <center><h3><b>修改日志</b></h3></center>
			  <input type="hidden" value="${log.logId }" name="logId"/>
			  <div class="form-group">
			     <span>日志标题:</span><input class="form-control" placeholder="标题" type="text" name="logTitle" value="${log.logTitle }"/>
			  </div>
			  <div class="form-group">
			      <textarea class="form-control" name="content" rows="10" required="required">${log.content }</textarea>
			  </div>
			   <div class="form-group">
			      <span>所属日志分类:</span>
			      <select name="logSortId" >
			      	
			      	<c:forEach items="${logSorts }" var="logSort">
			     		<option value="${logSort.logSortId }"
			     			<c:if test="${logsort.logSortId==logSort.logSortId }">
			     			selected="selected"
			     			</c:if>
			     		>${logSort.sortName }</option>
			      	</c:forEach>
					
			      </select>
			      <a href="javascript:void(0);" id="new_sort_btn">新建日志分类</a>
			  </div>
			  <div class="form-group">
			      <input type="button" class="btn btn-primary btn-sm" value="修改日志" onclick="checkForm();">
			      <a class="btn btn-default btn-sm"
			       href="${pageContext.request.contextPath}/log.do?action=toLogMain&userId=${user.userId}">返回日志分组列表</a>
			  </div>
			</form>	
		</div>
		<div id="new_sort_dialog">
	 		<form action="${pageContext.request.contextPath}/log.do?action=createLogSort" method="post">
		 		分组名&nbsp;&nbsp;<input id="div_text" required="required"  name="sortName"/>
		 		<input type="hidden" value="addLogUI" name="from"/>
		 		<input type="submit" class="btn btn-primary btn-sm" value="添加"/>
	 		</form>
	 	</div>
		<jsp:include page="../../public/ownner.jsp"></jsp:include>
		<script type="text/javascript">
		$(function() {
			var new_sort_dialog = $("#new_sort_dialog");
			new_sort_dialog.hide();
			
			var fckeditor=new FCKeditor('content');
	    	fckeditor.BasePath= getRootPath() + "/fckeditor/";//相对路径，相对于当前页面
	    	fckeditor.ToolbarSet="mgTool";
	    	fckeditor.Width="100%";
	    	fckeditor.Height="400";
	    	fckeditor.ReplaceTextarea();
			
	    	$("#write_log_form").validate({
				rules:{
					logTitle:{
						required:true
					}
				},
				messages:{
					logTitle:{
						required:"您的标题不能够为空"
					}
				}
			});
	    	
	    	$("#new_sort_btn").click(function(){
	    		new_sort_dialog.show().dialog({
					title: "新建日志分类",    
				    width: 340,    
				    height: 120, 
				    modal: true   	
				});
	    	});
		});
		
		function checkForm(){
			var oEditor = FCKeditorAPI.GetInstance('content');  
			editorValue = oEditor.GetHTML();
			if(editorValue != "" && editorValue != "&nbsp;" && editorValue != "<br />"){
				$("#write_log_form").submit();
			}else{
				alert("内容不能为空");
			}	
		}
	</script>
	</body>
</html>