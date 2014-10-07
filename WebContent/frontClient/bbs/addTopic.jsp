<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta charset="utf-8"/>
		<title>发帖</title>
		<jsp:include page="../../public/common.jsp"></jsp:include>
		<link href="<%=basePath%>css/bbs/add_topic.css" type="text/css" rel="stylesheet"/>
	  	<link href="<%=basePath%>css/user/user_register.css" type="text/css" rel="stylesheet"/>
	  	<link href="<%=basePath%>datepicker/jquery-ui.min.css" type="text/css" rel="stylesheet"/>
	  	<script type="text/javascript" src="<%=basePath%>js/jquery.validate.min.js"></script>
	  	<script type="text/javascript" src="<%=basePath%>datepicker/datepicker.js"></script>
	</head>
	<body>
		<jsp:include page="../../public/nav.jsp"></jsp:include>
		<jsp:include page="../../public/menu.jsp"></jsp:include>
		<jsp:include page="../../public/fckeditor.jsp"></jsp:include>
		<div id="add_topic_div">
			<form role="form" enctype="multipart/form-data" id="publishTopic_form"
				action="${pageContext.request.contextPath}/topic.do?action=publishTopicWithAtt&userId=${user.userId }" method="post">
			  <center><h3><b>发表帖子</b></h3></center>
			  <input type="hidden" id="res_message" value="${res_message }"/>
			  <div class="form-group">
			    <label for="inputTitle">标题</label>
			     <input type="text" class="form-control" value="${topic.topicTitle }"  placeholder="标题" id="inputTitle" name="topicTitle"/>
			  </div>
			  <div class="form-group">
			      <textarea class="form-control" id="inputContent" value="${topic.content }" name="content" rows="10"></textarea>
			  </div>
			   <div class="form-group">
			    <label for="inputAtts">附件</label>
			     <p><input type="file" accept=".zip" name="attachment"/>只支持zip后缀的压缩文件<br/>大小在10M以内<red>[选择上传]</red></p>
			  </div>
			 
		  	  <input type="hidden" value="${moduleId}" id="moduleId" name="moduleId"/>
			  <div class="form-group">
			      <input type="button" class="btn btn-primary btn-sm" value="发表帖子" onclick="checkForm();">
			      <a class="btn btn-default btn-sm"
			       href="${pageContext.request.contextPath}/topic.do?action=showTopicInModule&moduleId=${moduleId}">返回帖子列表</a>
			  </div>
			</form>	
		</div>
		<jsp:include page="../../public/ownner.jsp"></jsp:include>
		<script type="text/javascript">
		$(function() {
			var res_message = $("#res_message").val();
			if(res_message != ""){
				alert(res_message);
			}
			
			var fckeditor=new FCKeditor('content');
	    	fckeditor.BasePath= getRootPath() + "/fckeditor/";//相对路径，相对于当前页面
	    	fckeditor.ToolbarSet="mgTool";
	    	fckeditor.Width="100%";
	    	fckeditor.Height="400";
	    	fckeditor.ReplaceTextarea();
	    	document.all.content.value="";
			
		});
		$("#publishTopic_form").validate({
			rules:{
				topicTitle:{
					required:true,
					minlength:2,
					maxlength:16
				}
			},
			messages:{
				topicTitle:{
					required:"您的标题不能够为空",
					minlength:"您的标题长度不能够少于2个字符",
  					maxlength:"您的标题长度不能够多于16个字符"
				}
			}
		});
		function checkForm(){
			var oEditor = FCKeditorAPI.GetInstance('content');  
			editorValue = oEditor.GetHTML();
			if(editorValue != "" && editorValue != "&nbsp;" && editorValue != "<br />"){
				$("#publishTopic_form").submit();
			}else{
				alert("内容不能为空");
			}	
		}
	</script>
	</body>
</html>
