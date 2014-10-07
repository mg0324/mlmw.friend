<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<head>
<link href="<%=basePath%>backClient/css/common.css" type="text/css" rel="stylesheet"/>
</head>
<div id="bTopDiv">

	<div style="float: right;margin-right: 10px;margin-top: 45px;">
		<jsp:include page="../../public/timer.jsp"></jsp:include>
	</div>
	<div style="float: right; font-size: 15px;line-height: 160px;color:gray;">
		[ ${user.userName } - 
		<c:if test="${user.adminGrade == 4}">
			超级管理员
		</c:if>
		<c:if test="${user.adminGrade == 3}">
			用户管理员
		</c:if>
		<c:if test="${user.adminGrade == 2}">
			系统管理员
		</c:if>
		<c:if test="${user.adminGrade == 1}">
			论坛管理员
		</c:if>]
	</div>
</div>
