<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!-- 
	author : meigang
	2014-6-11
 -->
<!DOCTYPE HTML>
<html>
  <head>
    <title>首页</title>
	<jsp:include page="../../public/common.jsp"></jsp:include>
	<style type="text/css">
		
	</style>
  </head>
  
  <body>
	<!-- 加入导航 -->
	<jsp:include page="../../public/nav.jsp"></jsp:include>
	<div id="main_content">
		<div class="panel panel-default widthP50">
			  <div class="panel-heading panelHandle">
			    <h4 class="panel-title panelTitle">朋友社区动态</h4>
			  </div>
			  <div class="panel-body noDis">
			  	<pre>
2014-6-18 -- 2014-7-15 分析及编写项目指导书。
2014-7-16 -- 2014-8-7 团队合作编写项目代码，初步验收。
2014-8-8 -- 2014-9-10 在冲忙的迈博答辩过后，我们才发现bug真的多不胜
		      数。各组员回家前，都有说在家干掉bug.

			  	</pre>
			  </div>
		</div>
		<div class="panel panel-default widthP40">
			  <div class="panel-heading panelHandle">
			    <h3 class="panel-title panelTitle">最新会员</h3>
			  </div>
			  <div class="panel-body">
			  	<table class="table table-striped">
				 	<tr>
				 		<th>用户名</th>
				 		<th>昵称</th>
				 		<th>加入时间</th>
				 	</tr>
				 	<c:forEach items="${vips }" var="vip">
					 	<tr>
					 		<td>${vip.userName }</td>
					 		<td>${vip.userNickName }</td>
					 		<td>
					 			<fmt:formatDate value="${vip.createTime}" type="both"/>
					 		</td>
					 	</tr>
				 	</c:forEach>
				</table>
			  </div>
		</div>
		
		
		<div class="panel panel-default widthP50">
			  <div class="panel-heading panelHandle">
			    <h4 class="panel-title panelTitle">关于朋友社区</h4>
			  </div>
			  <div class="panel-body noDis">
			  	<pre>
    本网站由 梅刚，陈强，周皓，李跃磊，余瑞，宋子豪，宋启东 
以上7个人，共同开发完成。
    目前，正处在第一版的开发阶段。
    在开发的过程中，组员们都有感觉到有很多功能没有实现。
    所以，作为该项目的组长，我真诚地希望大家能够同心协力，
    来构建好属于我们的朋友社区。
    谢谢!!!
						2014-7-23 晚
			  	</pre>
			  </div>
		</div>
		
	</div>
	<jsp:include page="../../public/ownner.jsp"></jsp:include>
	<script type="text/javascript">
		$(function(){
			
		});
		
	</script>

</body>
</html>
				