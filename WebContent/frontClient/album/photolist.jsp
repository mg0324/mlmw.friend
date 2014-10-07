<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<title>照片</title>
<link href="<%=basePath%>css/user/user_selfInfo.css" type="text/css" rel="stylesheet"/>
<link href="<%=basePath%>css/album/photo_list.css" type="text/css" rel="stylesheet"/>
<link href="<%=basePath%>uploadify/uploadify.css" rel="stylesheet" type="text/css" />
<jsp:include page="../../public/common.jsp"></jsp:include>
<script src="<%=basePath%>uploadify/jquery.uploadify.min.js" type="text/javascript"></script>
</head>
<body>
	<jsp:include page="../../public/nav.jsp"></jsp:include>
	<jsp:include page="../../public/menu.jsp"></jsp:include>
	<div id="album_main_content">
		<div id="album_address">
			<a href="#main">${user.userNickName }</a> -	${album.albumName}
		</div>
		<div id="album_main_left">
			<a class="btn btn-primary btn-sm" href="#" id="btn_upload_photo">上传照片</a>
			<a class="btn btn-primary btn-sm" href="#" id="browse_photo">浏览照片</a>
			<p id="album_operation">
				<a href="#" onClick='warm()'>删除相册</a>&nbsp;
				<a href="${pageContext.request.contextPath}/album.do?action=browseAlbum&userId=${user.userId}">返回相册</a>
			</p>

			<hr>
			<ul id="album_main">
				<c:if test="${not empty requestScope.photoList}">
					<c:forEach items="${requestScope.photoList }" var="photo">
						<li>
							<a href="#" onClick="go()">
								<img src="${photo.photoPath}" width="140px" height="140px" />
							</a>
							<span class="album_li_info">
								<a href="#" onClick="delete_photo_warm('${photo.photoId }')">删除</a>
								<input type="hidden" value="${photo.photoId }" id="${photo.photoId }">
								&nbsp;&nbsp;<a href="#" onClick="show('${photo.photoId }')">移动</a><br/>
							</span>
						</li>
					</c:forEach>
				</c:if>
				<c:if test="${empty requestScope.photoList }">
					<li>你没有照片</li>
				</c:if>
			</ul>
		</div>
	</div>
	<!-- 上传照片的dialog -->
		<div id="upload_photo_dialog">
			<div id="upload_photo_head">
	          	 请选择你的照片
			</div>
			<div id="upload_photo_content">
				<div id="fileQueue"></div>
        		<input type="file" id="uploadify" />
        		<p>
            		<a href="javascript:$('#uploadify').uploadify('upload')" class="btn btn-primary btn-sm">上传</a>　
            		<a href="javascript:$('#uploadify').uploadify('cancel')" class="btn btn-default btn-sm">取消上传</a>
        		</p>
			</div>
				<a  href="photo.do?action=browseAlbum&albumId=${requestScope.album.albumId}" class="btn btn-default btn-sm" id="upload_photo_btn">完成</a>
		</div>
		<!-- 照片浏览 -->
		<c:if test="${not empty photoList }">
		<div id="browse_photo_dialog">
			<jsp:include page="browsePhoto.jsp"></jsp:include>	  		
		</div>
	</c:if>
	<c:if test="${empty photoList }">
		<div id="browse_photo_dialog">你没有照片</div>
	</c:if>
	<!-- 移动对话框-->
	<div id="move_photo_dialog">
			<c:if test="${albums.size() == 1}">
   				 你不能移动这张照片，你只有一个相册
       		</c:if>
       		<c:if test="${albums.size() > 1}">
         	   移动到：
			<select id="select_album">
				<c:forEach items="${albums}" var="album">			
					<option id="${album.albumId }" value="${album.albumId }">${album.albumName }</option>
				</c:forEach>
			</select>
			<input type="hidden" value="" id="select_photo"/>
		<a class="btn btn-primary btn-sm" id="move_photo" href="#" onclick="move()">确定</a>
		<a class="btn btn-primary btn-sm" id="cancle_move_photo" onclick="cancleMove()">取消</a>
		</c:if>
       
	</div>
	<div align="center">
		<jsp:include page="../../public/pager.jsp">
			<jsp:param value="photo.do?action=browseAlbum&albumId=${album.albumId }" name="path"/>
		</jsp:include>	
	</div>
	<input type="hidden" value='${user.userId}' id="userId"/>
	<input type="hidden" value='${requestScope.album.albumId}' id="albumId"/>
	<jsp:include page="../../public/ownner.jsp"></jsp:include>
	<script type="text/javascript">
	function go(){
		var browse_photo_dialog = $("#browse_photo_dialog");
		browse_photo_dialog.show().dialog({
			title:'浏览照片',
			width:800,
			height:590,
			left:200,
			top:70,
			modal:true
		});
	}
	function warm(){
		if(confirm("如果你删除相册，将删除相册内的所有照片")){
			window.location.href = "${pageContext.request.contextPath}/album.do?action=deleteAlbum&albumId=${requestScope.album.albumId}";
		}
	}
	//y为照片的Id
	function delete_photo_warm(y){
		if(confirm("你确定要删除这张照片吗？")){
			window.location.href = "${pageContext.request.contextPath}/photo.do?action=deletePhoto&albumId=${requestScope.album.albumId}&photoId="+y;
		}
	}
	 function show(x){
		 var move_photo_dialog = $("#move_photo_dialog");
		 move_photo_dialog.show().dialog({
				title:'移动照片',
				width:300,
				height:200,
				left:500,
				top:200,
				modal:true
			});
		 var a= $("#"+x).val();
		 document.getElementById("select_photo").value=a;
		 }
	 function move(){
		 var selectCount = document.getElementById("select_album").options;  
	        for(var i = 0 ; i<selectCount.length;i++){  
	            if(selectCount[i].selected){ 
	            	window.location.href="photo.do?action=movePhoto&albumId="+selectCount[i].value+"&photoId="+$("#select_photo").val()+"&userId="+$("#userId").val();  
	            }  
	        }  
		 
	 }
	 
	 function cancleMove(){
		 var move_photo_dialog = $("#move_photo_dialog");
		 move_photo_dialog.hide().dialog("close");
	 }
	 
	$(function(){
					
			var	upload_photo_dialog = $("#upload_photo_dialog");
			var browse_photo_dialog = $("#browse_photo_dialog");
			var move_photo_dialog = $("#move_photo_dialog");
			
			
			browse_photo_dialog.hide();
			upload_photo_dialog.hide();
			move_photo_dialog.hide();
			
			
			$("#btn_upload_photo").click(function(){
				upload_photo_dialog.show().dialog({
					title:'上传照片',
					width:470,
					height:475,
					left:400,
					top:70,
					modal:true
				});
			});
			$("#browse_photo").click(function(){
				browse_photo_dialog.show().dialog({
					title:'浏览照片',
					width:800,
					height:590,
					left:200,
					top:70,
					modal:true
				});
			});
			
			$("#upload_photo_btn").click(function(){
				upload_photo_dialog.show().dialog("close");
			});
			
			$("#uploadify").uploadify({
	            //指定swf文件
	            'swf':getRootPath()+'/uploadify/uploadify.swf',
	            //后台处理的页面
	            'uploader':getRootPath()+'/photo.do?action=uploadPhoto&albumId='+$("#albumId").val()+"&userId="+$("#userId").val(),
	            //按钮显示的文字
	            'buttonText': '选择照片',
	            //显示的高度和宽度，默认 height 30；width 120
	            'height': 30,
	            'width': 120,
	            //上传文件的类型  默认为所有文件    'All Files'  ;  '*.*'
	            //在浏览窗口底部的文件类型下拉菜单中显示的文本
	            'fileTypeDesc': '图片文件',
	            //允许上传的文件后缀
	            'fileTypeExts': '*.gif; *.jpg; *.png',
	            //发送给后台的其他参数通过formData指定
	            'formData': { 'action': 'uploadPhoto','albumId' :$("#albumId").val(),'userId':$("#userId").val()},
	            //上传文件页面中，你想要用来作为文件队列的元素的id, 默认为false  自动生成,  不带#
	            'queueID': 'fileQueue',
	            //选择文件后自动上传
	            'auto': true,
	            //设置为true将允许多文件上传
	            'multi': true
	        });	
		});
	</script>
</body>
</html>