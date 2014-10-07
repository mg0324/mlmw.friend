<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
<jsp:include page="../../public/common.jsp"></jsp:include>
<link href="<%=basePath%>css/bbs/topic_comment.css" type="text/css"
	rel="stylesheet" />
<style type="text/css">
body {
	background-color: white;
}
</style>
<div style="height: 30px; line-height: 30px; font-size: 15px;">
	论坛管理 - <a
		href="${pageContext.request.contextPath}/module.do?action=findAllModule&toWhere=back">板块管理</a>
	- <a
		href="${pageContext.request.contextPath}/topic.do?action=showTopicInModule&moduleId=${module.moduleId}&toWhere=back">帖子管理</a>
	- 审核帖子

</div>
<div class="mgang_toolbar">
	<a class="btn btn-primary btn-sm" onclick="return confirmDelete();"
		href="${pageContext.request.contextPath}/topic.do?action=deleteTopic&topicId=${topic.topicId}&moduleId=${module.moduleId}&toWhere=back">删除帖子</a>
</div>

<div id="floor_ownner" style="margin-top: 10px; border: none;">
	<div style="float: left;">
		<span>发帖人：<br /> 用户名:${topic.user.userName}<br />
			昵称:${topic.user.userNickName }
		</span>
	</div>

	<div class="ownner_topic_right">
		<div class="ownner_topic_title">${topic.topicTitle }</div>
		<div class="ownner_topic_content">${topic.content }</div>
		<div class="self_info_title"></div>
		<div class="ownner_topic_attachment">
			<c:if test="${empty topic.attachmentList }">无附件</c:if>
			<c:forEach items="${topic.attachmentList }" var="att">
				<span class="attachment_one">附件:${att.attachmentName } <a
					href="${pageContext.request.contextPath}/attachment.do?action=downloadAttachment&attachmentId=${att.attachmentId}">
						下载 </a> <br /> 大小: <fmt:formatNumber
						value="${att.attachmentSize div 1024}" pattern="##.##"
						minFractionDigits="2"></fmt:formatNumber> KB <br /> 上传时间:<fmt:formatDate
						value="${att.uploadTime }" type="both" /><br />
				</span>
			</c:forEach>
		</div>
		<div class="ownner_topic_other_info">
			<fmt:formatDate value="${topic.publishTime }" type="both" />
		</div>
	</div>
</div>
<div class="mgang_toolbar">
	<a class="btn btn-primary btn-sm" onclick="return confirmDelete();"
		href="${pageContext.request.contextPath}/topic.do?action=deleteTopic&topicId=${topic.topicId}&moduleId=${module.moduleId}&toWhere=back">删除帖子</a>
</div>
<script type="text/javascript">
	function confirmDelete() {
		if (confirm("您真的要删除该帖子吗?\n[删除后将不可恢复]")) {
			return true;
		} else {
			return false;
		}
	}
</script>
