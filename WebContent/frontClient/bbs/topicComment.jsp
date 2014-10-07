<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.friend.util.Pager" %>
<!-- 
	author : meigang
	2014-6-11
 -->
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<title>查看帖子</title>
<jsp:include page="../../public/common.jsp"></jsp:include>
<jsp:include page="../../public/fckeditor.jsp"></jsp:include>
<link href="<%=basePath%>css/bbs/topic_comment.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="<%=basePath%>js/jquery.validate.min.js"></script>
<style type="text/css">

</style>

</head>

<body>
	<jsp:include page="../../public/nav.jsp"></jsp:include>
	<jsp:include page="../../public/menu.jsp"></jsp:include>
	<div id="bbs_main_content">
		<div class="bbs_top_link">
			<a class="pull-right"
			href="${pageContext.request.contextPath}/topic.do?action=showTopicInModule&moduleId=${topic.module.moduleId}">返回帖子列表</a>
		</div>
		
		<div id="floor_ownner">
			<div class="headLine">
					&nbsp;&nbsp;&nbsp;楼主
			</div>
			<div class="ownner_topic_main">
				<div class="ownner_topic_left">
					<img class="topic_head_img" src="${pageContext.request.contextPath}/${topic.user.head}" width="120" height="120"/>
					<center><span>${topic.user.userNickName }</span></center>
				</div>
				<div class="ownner_topic_right">
					<div class="ownner_topic_title">${topic.topicTitle }</div>
					<div class="ownner_topic_content">
						${topic.content }
					</div>
					<div class="ownner_topic_attachment">
						<c:if test="${empty topic.attachmentList }">无附件</c:if>
						<c:forEach items="${topic.attachmentList }" var="att">
							<span class="attachment_one">附件:${att.attachmentName }
								<a
								href="${pageContext.request.contextPath}/attachment.do?action=downloadAttachment&attachmentId=${att.attachmentId}">
									 下载 </a>
								<br/>
								大小:
								<fmt:formatNumber value="${att.attachmentSize div 1024}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber> KB <br/>
								上传时间:<fmt:formatDate value="${att.uploadTime }" type="both"/><br/>
							</span>
						</c:forEach>
					</div>
					<div class="ownner_topic_other_info">
						<fmt:formatDate value="${topic.publishTime }" type="both"/>
					</div>
				</div>
			</div>
		</div>
		<c:if test="${empty topic.commentList }">
			<span style="width:95%;margin: 0 auto;display: block;
				text-align: center;color:red;">该帖子暂时没有评论...</span>
		</c:if>
		<ul id="topic_comment">
			<% int i = 1; %>
			<c:forEach items="${topic.commentList }" var="comment">
				<li>
					<div class="commentHeadLine">
						<%
							Pager pager=(Pager)request.getAttribute("page");
							int j=(pager.getCurrentPage()-1)*pager.getPageSize()+i;
							i++;
						%>
						&nbsp;&nbsp;&nbsp;
						<B><%=j%>楼</B>
					</div>
					<div class="topic_comment_headArea">
						<img class="topic_head_img" src="${pageContext.request.contextPath}/${comment.user.head}" width="120" height="120"/>
						<center>${comment.user.userNickName }</center>
					</div>
					<div class="topic_comment_content">
						${comment.content }
					</div>
					<div class="topic_comment_info">
						<fmt:formatDate value="${comment.publishTime }" type="both"/>  
					</div>
				</li>
			</c:forEach>
		</ul>
		
		
		<div align="center">
			<jsp:include page="../../public/pager.jsp">
				<jsp:param value="topicComment.do?action=lookComment&topicId=${topic.topicId }&moduleId=${topic.module.moduleId}" name="path"/>
			</jsp:include>	
		</div>
		
		
		<div id="add_topic_comment">
			<div class="topic_comment_headArea">
				<img class="topic_head_img" src="${pageContext.request.contextPath}/${user.head}" width="120" height="120"/>
				<center>${user.userNickName }</center>
			</div>
			<div class="comment_right">
				<form action="${pageContext.request.contextPath}/topicComment.do?action=addComment&userId=${user.userId}"
					method="post" id="comment_content">
					<input type="hidden" name="topicId" value="${topic.topicId }"/>
					<textarea rows="8" cols="60" name="content" id="c_content"></textarea><br/>
					<input type="button" class="btn btn-primary btn-sm" value="发表评论" onclick="checkForm()">
				
				</form>
			</div>

		</div>
	</div>
	
	<jsp:include page="../../public/ownner.jsp"></jsp:include>
	<script type="text/javascript">
		$(function() {
			var fckeditor=new FCKeditor('content');
	    	fckeditor.BasePath= getRootPath() + "/fckeditor/";//相对路径，相对于当前页面
	    	fckeditor.ToolbarSet="smile";
	    	fckeditor.Width="100%";
	    	fckeditor.ReplaceTextarea();
	    	document.all.content.value="";
		});
		function checkForm(){
			var oEditor = FCKeditorAPI.GetInstance('content');  
			editorValue = oEditor.GetHTML();
			if(editorValue != "" && editorValue != "&nbsp;" && editorValue != "<br />"){
				$("#comment_content").submit();
			}else{
				alert("内容不能为空");
			}	
		}
	</script>
</body>
</html>
