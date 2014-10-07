<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK"%>
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
<title>说说</title>
<link href="<%=basePath%>css/user/user_selfInfo.css" type="text/css"
	rel="stylesheet" />
<link href="<%=basePath%>css/talk/talk.css" type="text/css"
	rel="stylesheet" />

<jsp:include page="../../public/common.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="../../public/nav.jsp"></jsp:include>
	<jsp:include page="../../public/menu.jsp"></jsp:include>
	<jsp:include page="../../public/fckeditor.jsp"></jsp:include>
	<div id="self_main_content">
		<div id="talk_left">
			<img src="${pageContext.request.contextPath}/${user.head}" width="120" height="120">
		</div>
		<div id="talk_rigth">
			<div>我的说说</div>
			<div id="main_talk_div" style="width: 80%">
				<form action="talk.do?action=addTalk" method="post" id="publishTalkForm">
					<div class="com_form">
						<textarea class="input" id="talkText" name="content"></textarea>
						<p>
							<input type="button" class="btn btn-primary btn-sm" value="发表说说" onclick="checkform();">
						</p>
					</div>
				</form>
			</div>
			<c:if test="${talkList!=null }">
				<c:forEach items="${talkList }" var="talk">
					<div style="width: 80%">
						<div id="show_talk${talk.talkId }" class="talk">
							<table>
								<tr>
									<td rowspan="2" width="35px"> 
										<img src="${user.head }" width="30px" height="30px">
									</td>
									<td>
										<strong> &nbsp;&nbsp;${user.userNickName } </strong>
									</td>
								</tr>
								<tr>
									<td>
										<font color="gray">
											&nbsp;&nbsp;时间： 
											<fmt:formatDate value="${talk.publishTime }" type="both" />
											&nbsp;&nbsp;浏览次数：${talk.viewCount }
										</font>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<div style="width: 80%">
											<p>
												<font size="3">${talk.content }</font>
											</p>
										</div>
									</td>
								</tr>
							</table>
						</div>
						
						<div class="gongneng">
							<table>
								<tr>
									<td>
										<c:if test="${talk.praiseFlag }">
											<a href="talk.do?action=deleteTalkPraise&amp;talkId=${talk.talkId }">取消赞(${talk.praiseCount })</a>&nbsp;&nbsp;
										</c:if>
										<c:if test="${talk.praiseFlag!=true }">
											<a href="talk.do?action=addTalkPraise&amp;talkId=${talk.talkId }">赞(${talk.praiseCount })</a>&nbsp;&nbsp;
										</c:if>
									</td>
									<td>
										<a href="javascript:void(0);" onclick="comment(${talk.talkId})">评论(${talk.commentCount })</a>&nbsp;&nbsp;
									</td>
									<td>
										<a href="talk.do?action=deleteTalk&amp;talkId=${talk.talkId }" onclick="return confirm('确定要删除么？');">删除</a>&nbsp;&nbsp;
									</td>
								</tr>
							</table>
						</div>
						<br>
						<div class="talk_comment" id="talk_comment${talk.talkId }">
							<form action="talk.do?action=addComment" method="post" id="talk_comment_form${talk.talkId }">
								<table style="width: 80%">
									<tr>
										<td>
											<font size="3">
												<textarea class="input" name="content" style="width:620px;height:90px" id="text_talk_comment_form${talk.talkId }"></textarea>
											</font>
											<input type="hidden" name="talkId" value="${talk.talkId }">
											<input type="hidden" name="commentType" value="talk">
										</td>
									</tr>
									<tr>
										<td>
											<input type="button" class="btn btn-primary btn-sm" value="提交评论" onclick="checkform3(${talk.talkId })">
										</td>
									</tr>
								</table>
							</form>
						</div>
						<c:if test="${talk.commentList!=null }">
							<c:forEach items="${talk.commentList }" var="comment">
								<div class="comment"> 
									<table>
										<tr>
											<td rowspan="2" width="35px">
												<img src="${comment.user.head }" width="30px" height="30px">
											</td>
											<td>
												&nbsp;&nbsp;<a href="#">${comment.author }</a>&nbsp;&nbsp;:
											</td>
											<td rowspan="2">
												<div align="left">
													${comment.content }
												</div>	
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
											<td colspan="2">
												<font color="gray">&nbsp;&nbsp; 
													<fmt:formatDate value="${comment.publishTime }" pattern="yyyy-MM-dd" />
												</font>&nbsp;&nbsp;
												<a href="javascript:void(0);" onclick="reply1(${comment.commentId})">回复</a>
											</td>
										</tr>
									</table>
								</div>
								<div class="comment_reply" id="comment_reply${comment.commentId }">
									<form action="talk.do?action=addReply" method="post" id="comment_reply_form${comment.commentId }">
										<table style="width: 80%">
											<tr>
												<td>
													<font size="3">
														<textarea class="input" name="content" style="width:620px;height:90px" id="text_comment_reply_form${comment.commentId }" ></textarea>
													</font> 
													<input type="hidden" name="commentId" value="${comment.commentId }"> 
													<input type="hidden" name="lastReplayerId" value="${comment.user.userId }">
												</td>
											</tr>
											<tr>
												<td>
													<input type="button" class="btn btn-primary btn-sm" value="提交回复" onclick="checkform4(${comment.commentId});">
												</td>
											</tr>
										</table>
									</form>
								</div>

								<div class="reply">
									<c:if test="${comment.replyList!=null }">
										<c:forEach items="${comment.replyList }" var="reply">
											<table>
												<tr>
													<td rowspan="3" width="10%">&nbsp;</td>
													<td rowspan="3" width="10%">
														<img src="${reply.user.head }" width="30px" height="30px" alt="">
													</td>
													<td>&nbsp;&nbsp; 
														<a href="#">${reply.author }</a>
														&nbsp;&nbsp;回复：<a href="#">${reply.lastReplayer.userNickName }</a>&nbsp;&nbsp;&nbsp;&nbsp;${reply.content }
													</td>
												</tr>
												<tr>
													<td>
														<font color="gray">&nbsp;&nbsp;
															<fmt:formatDate value="${reply.publishTime }" type="both" />
														</font>&nbsp;&nbsp;
														<a href="javascript:void(0);" onclick="reply2(${reply.replyId})">回复</a>
													</td>
												</tr>
											</table>
											<div class="reply_reply" id="reply_reply${reply.replyId }">
												<form action="talk.do?action=addReply" method="post" id="reply_reply_form${reply.replyId }">
													<table >
														<tr>
															<td>
																<font size="3"> 
																	<textarea class="input" name="content" style="width:580px;height:90px;" id="text_reply_reply_form${reply.replyId }"></textarea>
																</font> 
																<input type="hidden" name="commentId" value="${comment.commentId }"> 
																<input type="hidden" name="lastReplayerId" value="${comment.user.userId }">
															</td>
														</tr>
														<tr>
															<td>
																<input type="button" class="btn btn-primary btn-sm" onclick="checkform5(${reply.replyId });" value="提交回复">
															</td>
														</tr>
													</table>
												</form>
											</div>
										</c:forEach>
									</c:if>
								</div>
							</c:forEach>
						</c:if>
					</div>
					<br>
					<br>
				</c:forEach>
				<div align="center">
					<jsp:include page="../../public/pager.jsp">
						<jsp:param value="talk.do?action=showTalk" name="path" />
					</jsp:include>
				</div>
			</c:if>
			<c:if test="${empty talkList }">
				<div align="center">
					<h3>你没发表过说说...</h3>
					<br>
				</div>
			</c:if>
			
		</div>
	</div>
	<jsp:include page="../../public/ownner.jsp"></jsp:include>
	<script type="text/javascript">
	$(function(){
		$("div.edit_talk").hide();
		$("div.talk_comment").hide();
		$("div.comment_reply").hide();
		$("div.reply_reply").hide();
		
		var fckeditor=new FCKeditor('content');
    	fckeditor.BasePath= getRootPath() + "/fckeditor/";//相对路径，相对于当前页面
    	fckeditor.ToolbarSet="smile";
    	fckeditor.Width="100%";
    	fckeditor.Height="120";
    	fckeditor.ReplaceTextarea();
    	document.all.content.value="";
		
	});
	
	function edit(id){
		$("#show_talk"+id).toggle();
		$("#edit_talk"+id).toggle();
	}
	
	function comment(id){
		$("#talk_comment"+id).toggle();
	}
	
	function reply1(id){
		$("#comment_reply"+id).toggle();
	}
	
	function reply2(id){
		$("#reply_reply"+id).toggle();
	}
	
	function checkform(){
		var oEditor = FCKeditorAPI.GetInstance('content');  
		editorValue = oEditor.GetHTML();
		if(editorValue != "" && editorValue != "&nbsp;" && editorValue != "<br />"){
			$("#publishTalkForm").submit();
		}
		else{
			alert("说说内容不能为空");
		}
	}
	
	function checkform2(id){
		
			$("#edit_talk_form"+id).submit();
		
	}
	
	function checkform3(id){
		var str=document.getElementById("text_talk_comment_form"+id).value;
		if(str != "" && str != "&nbsp;" && str != "<br />"){
			$("#talk_comment_form"+id).submit();
		}
		else{
			alert("评论内容不能为空");
		}
	}
	
	function checkform4(id){
		var str=document.getElementById("text_comment_reply_form"+id).value;
		if(str != "" && str != "&nbsp;" && str != "<br />"){
			$("#comment_reply_form"+id).submit();
		}
		else{
			alert("回复内容不能为空");
		}
	}
	
	function checkform5(id){
		var str=document.getElementById("text_reply_reply_form"+id).value;
		if(str != "" && str != "&nbsp;" && str != "<br />"){
			$("#reply_reply_form"+id).submit();
		}
		else{
			alert("回复内容不能为空");
		}
	}
	
</script>
</body>
</html>