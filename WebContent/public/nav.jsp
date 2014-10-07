<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<meta charset="utf-8"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="<%=basePath%>css/user/user_main.css" type="text/css" rel="stylesheet"/>
<nav id="navbar_size" class="navbar navbar-default navbar-fixed-top"
	role="navigation">
	<div class="container" id="main_navContent">
		<div class="navbar-header" id="navbar_header_update">
			<a id="navbar_brand" class="navbar-brand"
				href="${pageContext.request.contextPath}"><img src="${pageContext.request.contextPath}/image/friend_community_logo_blue.png" />
			</a>
		</div>

		<div class="collapse navbar-collapse" id="main_navMainContent">
			<ul class="nav navbar-nav" id="main_headLink">
				<li class="active"><a href="${pageContext.request.contextPath}"><span class="mgang_home_logo main_logoAlign"></span><span class="main_navLinkTitle"> 首页</span></a></li>
				<li><a href="${pageContext.request.contextPath}/friend.do?action=showFriendSort"><span class="glyphicon glyphicon-user"></span> 好友</a></li>
				<li><a href="${pageContext.request.contextPath}/module.do?action=findAllModule"><img src="${pageContext.request.contextPath}/image/bbs_gray_logo.gif" style="margin-top: -1px;margin-right: -4px;"/>论坛</a></li>
			
			</ul>
			
			<ul class="nav navbar-nav navbar-right" id="main_rightHeadLink">
				<c:if test="${empty user }">
					<li><a href="${pageContext.request.contextPath}/user.do?action=regUserUI">注册</a></li>
					<li><a href="${pageContext.request.contextPath}/user.do?action=loginUI">登录</a></li>
				</c:if>
				<c:if test="${not empty user }">
					
					<li><a href="${pageContext.request.contextPath}/user.do?action=toSelfMainUI"><red>${user.userNickName }</red> 主页</a></li>
					<li><a  onclick="return confirmLogout();"
					href="${pageContext.request.contextPath}/user.do?action=logout">退出</a></li>
					<li class="dropdown" id="main_rightDropdownLink">
					<a href="#" class="dropdown-toggle" id="main_settingLogoLink"
						data-toggle="dropdown"><span class="mgang_setting_logo main_settingAlign"></span> </a>
						<ul class="dropdown-menu" id="main_dropdownLinkItem">
							<li><a href="#">欢迎您,<red>${user.userNickName }</red></a></li>
							
							
						</ul>
					</li>
				</c:if>
				
			</ul>
		</div>
	</div>
	<!-- /.navbar-collapse -->
</nav>
<script type="text/javascript">
	function confirmLogout(){
		if(confirm("您真的要退出吗？")){
			return true;
		}else{
			return false;
		}
	}
</script>

