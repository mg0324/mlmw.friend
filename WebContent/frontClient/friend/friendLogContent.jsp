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
<title>${log.logTitle }</title>
<jsp:include page="../../public/common.jsp"></jsp:include>
<link href="<%=basePath%>css/log/log_content.css" type="text/css"
	rel="stylesheet" />
<link href="<%=basePath%>css/talk/talk.css" type="text/css"
	rel="stylesheet" />
<jsp:include page="../../public/fckeditor.jsp"></jsp:include>
<style type="text/css">
</style>

</head>

<body>
	<jsp:include page="../../public/nav.jsp"></jsp:include>
	<jsp:include page="../../public/menu.jsp"></jsp:include>

	<div id="main_log_content">
		<div id="log_main_div">

			<div class="div_title">
				[ ${friend.userNickName }日志 ] <span class="pull-right"> <a
					href="${pageContext.request.contextPath}/log.do?action=toLogMain
							&from=friend&userId=${friend.userId}">返回日志列表</a>
				</span>
			</div>
			<div class="log_content">
				<div id="log_title">
					<c:if test="${log.topFlag eq 1 }">
						<red>[顶]</red>
					</c:if>
					${log.logTitle } &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span
						class="font12px"> <fmt:formatDate
							value="${log.publishTime }" type="both" />&nbsp;&nbsp;
						阅读(${log.viewCount })
					</span>
				</div>
				<ul class="function_ul">
					<c:if test="${isPraise eq 1}">
						<li><a href="${pageContext.request.contextPath}/log.do?action=clickLogPraise&from=friend&logId=${log.logId }&friendId=${friend.userId}&userId=${user.userId}">赞(${praiseNum })</a></li>
					</c:if >
					<c:if test="${isPraise eq 0}">
						<li><a href="${pageContext.request.contextPath}/log.do?action=clickLogPraise&from=friend&logId=${log.logId }&friendId=${friend.userId}&userId=${user.userId}">取消赞(${praiseNum })</a></li>
					</c:if>
					<li><a href="#add_log_comment">评论</a></li>
					<c:if test="${isTransfer eq 0}">
						<li>已转(${transferNum })</li>
					</c:if >
					<c:if test="${isTransfer eq 1}">
						<li><a href="${pageContext.request.contextPath}/log.do?action=transferLog&from=friend&logId=${log.logId }&friendId=${friend.userId}&transferId=${user.userId}">转(${transferNum })</a></li>
					</c:if>
				</ul>
				<div class="log_content_div">
					<center><h3><b>${log.logTitle }</b></h3></center>
					${log.content }
				</div>
			</div>
			<ul class="function_ul">

					<c:if test="${isPraise eq 1}">
						<li><a href="${pageContext.request.contextPath}/log.do?action=clickLogPraise&from=friend&logId=${log.logId }&friendId=${friend.userId}&userId=${user.userId}">赞(${praiseNum })</a></li>
					</c:if >
					<c:if test="${isPraise eq 0}">
						<li><a href="${pageContext.request.contextPath}/log.do?action=clickLogPraise&from=friend&logId=${log.logId }&friendId=${friend.userId}&userId=${user.userId}">取消赞(${praiseNum })</a></li>
					</c:if>
					<li><a href="#add_log_comment">评论</a></li>
					<c:if test="${isTransfer eq 0}">
						<li>已转(${transferNum })</li>
					</c:if >
					<c:if test="${isTransfer eq 1}">
						<li><a href="${pageContext.request.contextPath}/log.do?action=transferLog&from=friend&logId=${log.logId }&friendId=${friend.userId}&transferId=${user.userId}">转(${transferNum })</a></li>
					</c:if>
				</ul>
			<div class="log_other_info">
				<a href="#">${log.logSort.sortName }</a> 公开 | 原创
				${log.author.userNickName }
			</div>
		</div>
		<!-- 显示评论列表 回复列表 分页显示-->

		<div id="log_comment_reply">
			<c:forEach items="${page.list }" var="comment">
				<div class="comment">
					<table>
						<tr>
							<td rowspan="4"><img src="${comment.user.head }"
								width="30px" height="30px" alt=""></td>
							<td rowspan="2"><a href="#">&nbsp;&nbsp;${comment.author }&nbsp;&nbsp;</a>:${comment.content }
							</td>
						</tr>
						<tr>
						</tr>
						<tr>
							<td><font color="gray">&nbsp;&nbsp; <fmt:formatDate
										value="${comment.publishTime }" pattern="yyyy-MM-dd" />
							</font>&nbsp;&nbsp; <a href="javascript:void(0);"
								onclick="reply1(${comment.commentId})">回复</a></td>
						</tr>
						<tr>

						</tr>
					</table>
				</div>
				<div class="comment_reply" id="comment_reply${comment.commentId }">
					<form action="${pageContext.request.contextPath}/log.do?action=addReply&from=friend&friendId=${friend.userId}&logId=${log.logId}" method="post"
						id="comment_reply_form${comment.commentId }">
						<table style="width: 80%">
							<tr>
								<td><font size="3"> <textarea class="input"
											name="content" style="width: 100%"
											id="text_comment_reply_form${comment.commentId }" cols=""
											rows=""></textarea>
								</font> <input type="hidden" name="commentId"
									value="${comment.commentId }"> <input type="hidden"
									name="lastReplayerId" value="${comment.user.userId }">
								</td>
							</tr>
							<tr>
								<td><input type="button" value="提交回复"
									onclick="checkform4(${comment.commentId});"></td>
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
									<td rowspan="3"><img src="${reply.user.head }"
										width="30px" height="30px" alt=""></td>
									<td>&nbsp;&nbsp; <a href="#">${reply.author }</a>&nbsp;&nbsp;回复：<a
										href="#">${reply.lastReplayer.userNickName }</a>&nbsp;&nbsp;&nbsp;&nbsp;${reply.content }
									</td>
								</tr>
								<tr>
									<td><font color="gray">&nbsp;&nbsp;<fmt:formatDate
												value="${reply.publishTime }" type="both" /></font>&nbsp;&nbsp; <a
										href="javascript:void(0);" onclick="reply2(${reply.replyId})">回复</a></td>
								</tr>
							</table>
							<div class="reply_reply" id="reply_reply${reply.replyId }">
								<form action="${pageContext.request.contextPath}/log.do?action=addReply&from=friend&friendId=${friend.userId}&logId=${log.logId}" method="post"
									id="reply_reply_form${reply.replyId }">
									<table style="width: 80%">
										<tr>
											<td><font size="3"> <textarea class="input"
														name="content" style="width: 100%"
														id="text_reply_reply_form${reply.replyId }"></textarea>
											</font> <input type="hidden" name="commentId"
												value="${comment.commentId }"> <input type="hidden"
												name="lastReplayerId" value="${comment.user.userId }"></td>
										</tr>
										<tr>
											<td><input type="button"
												onclick="checkform5(${reply.replyId });" value="提交回复"></td>
										</tr>
									</table>
								</form>
							</div>
						</c:forEach>
					</c:if>
				</div>
			</c:forEach>
		</div>
		<center>
			<jsp:include page="../../public/pager.jsp">
				<jsp:param value="log.do?action=showLogInfo&from=friend&friendId=${friend.userId}&logId=${log.logId }" name="path" />
			</jsp:include>
		</center>
		
		<div id="add_log_comment">
			<div class="log_comment_headArea">
				<img class="log_head_img"
					src="${pageContext.request.contextPath}/${user.head}" width="120"
					height="120" />
				<center>${user.userNickName }</center>
			</div>
			<div class="comment_right">
				<form
					action="${pageContext.request.contextPath}/log.do?action=publishComment&from=friend&friendId=${friend.userId}"
					method="post">
					<input type="hidden" name="logId" value="${log.logId }" />
					<textarea id="logComment_publish" rows="8" cols="60" name="content"></textarea>
					<br />
					<button type="submit" class="btn btn-primary btn-sm">发表评论</button>
				</form>
			</div>
		</div>

	</div>

	<jsp:include page="../../public/ownner.jsp"></jsp:include>
	<script type="text/javascript">
		$(function() {
			$("div.talk_comment").hide();
			$("div.comment_reply").hide();
			$("div.reply_reply").hide();
			
			var fckeditor = new FCKeditor('logComment_publish');
			fckeditor.BasePath = getRootPath() + "/fckeditor/";//相对路径，相对于当前页面
			fckeditor.ToolbarSet = "smile";
			fckeditor.Width = "100%";
			fckeditor.ReplaceTextarea();
			document.all.content.value = "";

		});
		
		
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
			var str=document.getElementById("text_edit_talk_form"+id).value;
			if(str != "" && str != "&nbsp;" && str != "<br />"){
				$("#edit_talk_form"+id).submit();
			}
			else{
				alert("说说内容不能为空");
			}
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
