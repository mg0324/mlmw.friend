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
<title>${friend.userNickName }的主页</title>
<link href="<%=basePath%>css/user/user_selfInfo.css" type="text/css"
	rel="stylesheet" />
<link href="<%=basePath%>css/user/user_selfMain.css" type="text/css"
	rel="stylesheet" />
<link href="<%=basePath%>css/talk/talk.css" type="text/css"
	rel="stylesheet" />

<jsp:include page="../../public/common.jsp"></jsp:include>

</head>
<body>
	<jsp:include page="../../public/nav.jsp"></jsp:include>
	<jsp:include page="../../public/menu.jsp"></jsp:include>
	<div id="main_content" style="padding-top: 20px;">

		<jsp:include page="friendLeft.jsp"></jsp:include>

		<div id="self_main_center_div">
			<div id="self_info_div">好友说说</div>

			<div id="my_talk_div">
				<c:forEach items="${friendTalkList }" var="talk">
					<div>
						<div id="show_talk${talk.talkId }" class="talk">
							<table>
								<tr>
									<td rowspan="2"><img src="${talk.user.head }" width="30px"
										height="30px" alt=""></td>
									<td><strong> &nbsp;&nbsp;${talk.user.userNickName }
									</strong></td>
								</tr>
								<tr>
									<td><font color="gray"> &nbsp;&nbsp;时间： <fmt:formatDate
												value="${talk.publishTime }" type="both" />&nbsp;&nbsp;
											浏览次数：${talk.viewCount }
									</font></td>
								</tr>
								<tr>
									<td colspan="2">
										<p>
											<font size="3">${talk.content }</font>
										</p>
									</td>
								</tr>
							</table>
						</div>

						<div class="gongneng">
							<table>
								<tr>
									<td><c:if test="${talk.praiseFlag }">
											<a
												href="${pageContext.request.contextPath}/talk.do?action=deleteTalkPraise&from=friendMain&userId=${friend.userId }&talkId=${talk.talkId }">取消赞(${talk.praiseCount })</a>&nbsp;&nbsp;
								</c:if> <c:if test="${talk.praiseFlag!=true }">
											<a
												href="${pageContext.request.contextPath}/talk.do?action=addTalkPraise&from=friendMain&userId=${friend.userId }&talkId=${talk.talkId }">赞(${talk.praiseCount })</a>&nbsp;&nbsp;
								</c:if></td>
									<td><a href="javascript:void(0);"
										onclick="comment(${talk.talkId})">评论(${talk.commentCount })</a>&nbsp;&nbsp;
									</td>

								</tr>
							</table>
						</div>
						<div class="talk_comment" id="talk_comment${talk.talkId }">
							<form
								action="${pageContext.request.contextPath}/talk.do?action=addComment&from=friendMain&userId=${friend.userId }"
								method="post" id="talk_comment_form${talk.talkId }">
								<table style="width: 80%">
									<tr>
										<td><font size="3"> <textarea class="input"
													name="content" style="width: 100%"
													id="text_talk_comment_form${talk.talkId }" cols="" rows=""></textarea>
										</font> <input type="hidden" name="talkId" value="${talk.talkId }">
											<input type="hidden" name="commentType" value="talk"></td>
									</tr>
									<tr>
										<td><input type="button" value="提交评论"
											onclick="checkform3(${talk.talkId })"></td>
									</tr>
								</table>
							</form>
						</div>
						<c:if test="${talk.commentList!=null }">
							<c:forEach items="${talk.commentList }" var="comment">
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
								<div class="comment_reply"
									id="comment_reply${comment.commentId }">
									<form
										action="${pageContext.request.contextPath}/talk.do?action=addReply&from=friendMain&userId=${friend.userId }"
										method="post" id="comment_reply_form${comment.commentId }">
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
																value="${reply.publishTime }" type="both" /></font>&nbsp;&nbsp;
														<a href="javascript:void(0);"
														onclick="reply2(${reply.replyId})">回复</a></td>
												</tr>
											</table>
											<div class="reply_reply" id="reply_reply${reply.replyId }">
												<form
													action="${pageContext.request.contextPath}/talk.do?action=addReply&from=friendMain&userId=${friend.userId }"
													method="post" id="reply_reply_form${reply.replyId }">
													<table style="width: 80%">
														<tr>
															<td><font size="3"> <textarea class="input"
																		name="content" style="width: 100%"
																		id="text_reply_reply_form${reply.replyId }"></textarea>
															</font> <input type="hidden" name="commentId"
																value="${comment.commentId }"> <input
																type="hidden" name="lastReplayerId"
																value="${comment.user.userId }"></td>
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
						</c:if>
					</div>
					<br>
					<br>
				</c:forEach>
				<div align="center">
					<jsp:include page="../../public/pager.jsp">
						<jsp:param
							value="friend.do?action=toFriendMainUI&userId=${friend.userId }"
							name="path" />
					</jsp:include>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../../public/ownner.jsp"></jsp:include>
	<script type="text/javascript">
		$(function(){
			
			$("div.talk_comment").hide();
			$("div.comment_reply").hide();
			$("div.reply_reply").hide();

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