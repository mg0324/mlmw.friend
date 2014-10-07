<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<meta charset="utf-8"/>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="朋友,社区,朋友社区,friends">
<meta http-equiv="description" content="这是由梦来梦往团队开发的web项目">

<link rel="stylesheet" href="<%=basePath%>bootstrap3/css/bootstrap.min.css" type="text/css"></link>
<link rel="stylesheet" href="<%=basePath%>bootstrap3/css/bootstrap-theme.min.css"type="text/css"></link>
<link rel="stylesheet" href="<%=basePath%>css/common.css" type="text/css"></link>


<script type="text/javascript" src="<%=basePath%>js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>bootstrap3/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/common.js"></script>

<link rel="stylesheet" href="<%=basePath%>easyui1.3/themes/icon.css"/>
<link rel="stylesheet" href="<%=basePath%>easyui1.3/themes/gray/easyui.css"/>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>easyui1.3/jquery.easyui.min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>easyui1.3/locale/easyui-lang-zh_CN.js"></script>


