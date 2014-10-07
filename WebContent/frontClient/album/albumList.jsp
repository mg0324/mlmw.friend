<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>相册</title>
<link href="<%=basePath%>css/user/user_selfInfo.css" type="text/css"
	rel="stylesheet" />
<link href="<%=basePath%>css/album/album_list.css" type="text/css"
	rel="stylesheet" />
<link href="<%=basePath%>css/album/upload_dialog.css"
	type="text/css" rel="stylesheet" />
<jsp:include page="../../public/common.jsp"></jsp:include>
<link href="<%=basePath%>uploadify/uploadify.css" rel="stylesheet"
	type="text/css" />
<script src="<%=basePath%>uploadify/jquery.uploadify.min.js"
	type="text/javascript"></script>

</head>
<body>
	<jsp:include page="../../public/nav.jsp"></jsp:include>
	<jsp:include page="../../public/menu.jsp"></jsp:include>
	<div id="album_main_content">
		<div id="album_address">
			<a href="#main">${user.userNickName }</a> - 相册
		</div>
		<div id="album_main_left">
			<a class="btn btn-default btn-sm" id="btn_create_album" href="#">创建相册</a>
			<ul id="album_main">
				<c:if test="${not empty requestScope.albumList}">
					<c:forEach items="${requestScope.albumList }" var="album">
						<li>
							<a href="${pageContext.request.contextPath}/photo.do?action=browseAlbum&albumId=${album.albumId}">
								<img src="${pageContext.request.contextPath}/image/album_cover.jpg"
									width="140px" height="140px" />
							</a>
							<span class="album_li_info">
								<a href="${pageContext.request.contextPath}/photo.do?action=browseAlbum&albumId=${album.albumId}">${album.albumName }</a><br />
								${fn:length(album.photoList)}张
							</span>
						</li>
					</c:forEach>
				</c:if>
				<c:if test="${empty requestScope.albumList }">
					<li>你没有相册</li>
				</c:if>
			</ul>
		</div>
	</div>
	<!-- 创建相册的dialog -->
	<div id="create_album_dialog">
		<form id="create_album_form" 
			action="#"
			method="post">
			<input type="hidden" id="userId" name="userId" value="${user.userId }" /> 相册名称:<input
				type="text" name="albumName" id="albumName" required='required' onblur ="javascript:AlbumName(this);">
				<span id ="res_album_name_hava"></span>
				<br />
			<br /> 相册描述:<input type="text" name="albumDesc" /><br />
			<br />
			<button class="btn btn-primary btn-sm" type="submit" id="create_album_btn">创建</button>
			<a class="btn btn-default btn-sm" id="create_album_form_cancel_btn">取消</a>
		</form>
	</div>
	<div align="center">
		<jsp:include page="../../public/pager.jsp">
			<jsp:param value="album.do?action=browseAlbum&userId=${user.userId }" name="path"/>
		</jsp:include>	
	</div>
	<jsp:include page="../../public/ownner.jsp"></jsp:include>
	<script type="text/javascript">
	
	function AlbumName(t){
		var albumName=$("#albumName").val();
		if(albumName==""){
			alert("请输入相册名称");
			return;
		}

		$.ajax({
				url: getRootPath() + "/ajaxServlet",
				data:{"action":"checkAlbumName","userId":$("#userId").val(),"albumName": $(t).val()},
				type:'post',
				dataType:"json",
				error:function(res){
					alert("ajax失败");
				},
				success:function(res){
					var msg = res.msg;
					if(msg == "T"){
						$("#res_album_name_hava").text("");
						$("#res_album_name_hava").text("该相册名已存在!");
						$("#create_album_btn").attr("onClick",go_cancle());
					}else{
						$("#res_album_name_hava").text("");
						$("#res_album_name_hava").text("该相册名可以使用!")
						$("#create_album_btn").attr("onClick",go_submit());
					}
				}
			});
		
		}
	
		function go_cancle(){
			alert("该相册名已存在!");
		}
		function go_submit(){
			$("#res_user_name_hava").text("该相册名可以使用!")
			$("#create_album_form").attr("action",getRootPath()+"/album.do?action=createAlbum")
		}
		$(function(){
			var create_album_dialog = $("#create_album_dialog");
			var	upload_photo_dialog = $("#upload_photo_dialog");
			upload_photo_dialog.hide();
			create_album_dialog.hide();
			
			$("#btn_create_album").click(function(){
				create_album_dialog.show().dialog({    
				    title: '创建相册',    
				    width: 400,    
				    height: 220, 
				    top : 130,
				    left :490,
				    modal: true   
				});    
			});
			$("#btn_upload_photo").click(function(){
				upload_photo_dialog.show().dialog({
					title:'上传照片',
					width:1300,
					height:475,
					left:10,
					top:70,
					modal:true
				});
			});
			
		 	$("#uploadify").uploadify({
	            //指定swf文件
	            'swf': getRootPath() +'/uploadify/uploadify.swf',
	            //后台处理的页面
	            'uploader': getRootPath() + '/photo.do?action=uploadPhoto&userId='+$("#userId").val()+'&albumId='+$("#to_album").val(),
	            //按钮显示的文字
	            'buttonText': '上传图片',
	            //显示的高度和宽度，默认 height 30；width 120
	            'height': 15,
	            'width': 80,
	            //上传文件的类型  默认为所有文件    'All Files'  ;  '*.*'
	            //在浏览窗口底部的文件类型下拉菜单中显示的文本
	            'fileTypeDesc': '图片文件',
	            //允许上传的文件后缀
	            'fileTypeExts': '*.gif; *.jpg; *.png',
	            //发送给后台的其他参数通过formData指定
	          
	            'postData': { 'action': 'uploadPhoto','albumId' : $("#to_album").val(),'userId' : $("#userId").val() },
	            //上传文件页面中，你想要用来作为文件队列的元素的id, 默认为false  自动生成,  不带#
	            'queueID': 'fileQueue',
	            //选择文件后自动上传
	            'auto': false,
	            //设置为true将允许多文件上传
	            'multi': true
	        });

			$("#create_album_form_cancel_btn").click(function(){
				create_album_dialog.hide().dialog("close");
			});
		});
	
	</script>
</body>
</html>