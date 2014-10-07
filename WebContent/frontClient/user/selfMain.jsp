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
<title>个人中心</title>
<link href="<%=basePath%>css/user/user_selfMain.css" type="text/css" rel="stylesheet"/>
<link href="<%=basePath%>css/talk/talk.css" type="text/css"
	rel="stylesheet" />
<jsp:include page="../../public/common.jsp"></jsp:include>

</head>
<body>
	<jsp:include page="../../public/nav.jsp"></jsp:include>
	<jsp:include page="../../public/fckeditor.jsp"></jsp:include>
	<div id="main_content">
		<div id="main_content_left">
			<div id="main_content_headArea">
				<span class="mgang_test_head">
					<img alt="" src="${user.head}" width="120" height="120">
				</span>
				<span class="main_left_user_nickname">
					用户昵称:${user.userNickName }
				</span>
			</div>
			<ul id="main_content_left_function_list">
				<li><a href="${pageContext.request.contextPath}/user.do?action=toSelfInfoUI" ><img src="${pageContext.request.contextPath}/image/person_logo.gif"/>个人资料</a></li>
				<li><a href="${pageContext.request.contextPath}/friend.do?action=showFriendSort"><img src="${pageContext.request.contextPath}/image/friend_logo.gif"/>我的好友</a></li>
				<li><a href="${pageContext.request.contextPath}/talk.do?action=showTalk"><img src="${pageContext.request.contextPath}/image/talk_logo.gif"/>说说</a></li>
				<li><a href="${pageContext.request.contextPath}/log.do?action=toLogMain&userId=${user.userId}"><img src="${pageContext.request.contextPath}/image/log_logo.gif"/>日志</a></li>
				<li><a href="${pageContext.request.contextPath}/album.do?action=browseAlbum&userId=${user.userId}"><img src="${pageContext.request.contextPath}/image/album_logo.gif"/>相册</a></li>
				<li><a href="${pageContext.request.contextPath}/module.do?action=findAllModule"><img src="${pageContext.request.contextPath}/image/bbs_logo.gif"/>论坛</a></li>
			</ul>
		</div>
		
		<div id="self_main_center_div">
			<div id="my_talk_div">
				<!-- 发表说说的框 -->
				<form action="${pageContext.request.contextPath}/talk.do?action=addTalk" method="post"
					id="publishTalkForm">
					<div class="com_form">
						<textarea class="input" id="talkText" name="content" cols=""
							rows=""></textarea>
						<p>
							<input type="button" class="btn btn-primary btn-sm" value="发表说说"
								onclick="checkform();">
						</p>
					</div>
				</form>
			</div>
			<div id="show_friend_talk_div">
			
			<c:forEach items="${friendTalkList }" var="talk">
				<div>
					<div id="show_talk${talk.talkId }" class="talk">
						<table>
							<tr>
								<td rowspan="2"><img src="${talk.user.head }" width="30px"
									height="30px" alt=""></td>
								<td><strong> &nbsp;&nbsp;${talk.user.userNickName } </strong></td>
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
											href="${pageContext.request.contextPath}/talk.do?action=deleteTalkPraise&from=selfMain&talkId=${talk.talkId }">取消赞(${talk.praiseCount })</a>&nbsp;&nbsp;
								</c:if> <c:if test="${talk.praiseFlag!=true }">
										<a
											href="${pageContext.request.contextPath}/talk.do?action=addTalkPraise&from=selfMain&talkId=${talk.talkId }">赞(${talk.praiseCount })</a>&nbsp;&nbsp;
								</c:if></td>
								<td><a href="javascript:void(0);"
									onclick="comment(${talk.talkId})">评论(${talk.commentCount })</a>&nbsp;&nbsp;
								</td>
								
							</tr>
						</table>
					</div>
					<div class="talk_comment" id="talk_comment${talk.talkId }">
						<form action="${pageContext.request.contextPath}/talk.do?action=addComment&from=selfMain" method="post"
							id="talk_comment_form${talk.talkId }">
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
								<form action="${pageContext.request.contextPath}/talk.do?action=addReply&from=selfMain" method="post"
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
															value="${reply.publishTime }" type="both" /></font>&nbsp;&nbsp;
													<a href="javascript:void(0);"
													onclick="reply2(${reply.replyId})">回复</a></td>
											</tr>
										</table>
										<div class="reply_reply" id="reply_reply${reply.replyId }">
											<form action="${pageContext.request.contextPath}/talk.do?action=addReply&from=selfMain" method="post"
												id="reply_reply_form${reply.replyId }">
												<table style="width: 80%">
													<tr>
														<td><font size="3"> <textarea class="input"
																	name="content" style="width: 100%"
																	id="text_reply_reply_form${reply.replyId }"
																	></textarea>
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
			<c:if test="${empty friendTalkList }">
				您的好友没有发表说说，或者您还没有好友，请尽快添加好友。
			</c:if>
			</div>
			<!-- 不显示好友日志
			<div id="friend_log">
				<ul id="show_friend_new_log">
					<c:forEach items="${friendLogList }" var="log">
						<li>
							<div class="log_author">
								<img src="${pageContext.request.contextPath}/${log.author.head }" width="60px"
									height="60px" alt="">
									<div align="center"><b>${log.author.userNickName }</b></div>
							</div>
							<div class="log_info_right">
								<span class="log_title">
									日志标题:
									<span><a href="#">${log.logTitle }</a></span>
									<span class="pull-right"><fmt:formatDate value="${log.publishTime }" type="both" /></span>
								</span>
								<div class="log_content">${log.content }</div>
								<div class="log_friend_short_function">
									<c:if test="${isPraise eq 1}">
										<li><a href="${pageContext.request.contextPath}/log.do?action=clickLogPraise&from=friend&logId=${log.logId }&userId=${user.userId}">赞(${praiseNum })</a></li>
									</c:if >
									<c:if test="${isPraise eq 0}">
										<li><a href="${pageContext.request.contextPath}/log.do?action=clickLogPraise&from=friend&logId=${log.logId }&userId=${user.userId}">取消赞(${praiseNum })</a></li>
									</c:if>
									<c:if test="${isTransfer eq 0}">
										<li>已转(${transferNum })</li>
									</c:if >
									<c:if test="${isTransfer eq 1}">
										<li><a href="${pageContext.request.contextPath}/log.do?action=transferLog&from=friend&logId=${log.logId }&transferId=${user.userId}">转(${transferNum })</a></li>
									</c:if>
								</div>
							</div>
							
								
							
						</li>
						
					</c:forEach>
				</ul>
			</div>
			 -->
		</div>
		<div id="self_main_right">
			
		</div>
	</div>
	<jsp:include page="../../public/ownner.jsp"></jsp:include>
	<script type="text/javascript">
		$(function(){
			
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