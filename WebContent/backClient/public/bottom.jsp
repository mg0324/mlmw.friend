<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<meta charset="utf-8"/>
<jsp:include page="../../public/common.jsp"></jsp:include>
<style type="text/css">
	.bownner{
		width:100%;
		height:50px;
		text-align:center;
		line-height: 50px;
		background-color: #DDEDF6;
		margin-left:12px;
	}
</style>

<div class="bownner">
	Copyright © 2014.7 - 2014.9 DreamWorkHome <a href="#">梦来梦往工作室 </a>版权所有 | 朋友社区 | Friend Community

</div>
