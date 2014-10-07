<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!-- 
	author : chenqiang
	2014-7-22
 -->
 <%@page import="com.friend.vo.Module" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>我的日志</title>
	<jsp:include page="../../public/common.jsp"></jsp:include>
	<link href="<%=basePath%>css/log/log_main.css" type="text/css" rel="stylesheet"/>
	<script type="text/javascript" src="<%=basePath%>js/jquery.validate.min.js"></script>
	<style type="text/css">
		
	</style>
	
  </head>
  
  <body>
	<jsp:include page="../../public/nav.jsp"></jsp:include>
	<jsp:include page="../../public/menu.jsp"></jsp:include>
	
	<div id="main_log_content">
		<div id="log_list_title">
			日志管理 | ${user.userNickName }
		</div>
		<div id="log_list_content">
			<div class="log_btns">
				<a href="${pageContext.request.contextPath}/log.do?action=toAddLogUI" class="btn btn-primary btn-sm">写日志</a> 
				<a id="create_btn" class="btn btn-primary btn-sm">添加日志分类</a>
			</div>
			<div id="log_list">
				<div class="div_title">
					日志列表
				</div>
				<div id="list_div">
					<ul class="log_ul">
					<c:if test="${empty page.list }">
						您还没有发表过日志
					</c:if>
					<c:forEach items="${page.list }" var="log">
						<li>
							<c:if test="${log.topFlag eq 1 }">
								<red>[顶]</red>
							</c:if>
							<c:if test="${log.author.userId != user.userId }">
								<red>[转]</red>
							</c:if>
							<a href="${pageContext.request.contextPath}/log.do?action=showLogInfo&logId=${log.logId }">${log.logTitle }</a>
							<span class="pull-right">
							<c:if test="${log.author.userId == user.userId }">
								<c:if test="${log.topFlag eq 1 }">
									<a href="${pageContext.request.contextPath}/log.do?action=cancelLogTop&logId=${log.logId}">取消置顶</a>
								</c:if>
								<c:if test="${log.topFlag eq 0 }">
									<a href="${pageContext.request.contextPath}/log.do?action=setLogTop&logId=${log.logId}">置顶</a>
								</c:if>
							</c:if>
								<c:if test="${log.author.userId != user.userId }">
									<a href="${pageContext.request.contextPath}/log.do?action=deleteTransfer&logId=${log.logId}&userId=${user.userId}&logSortId=${logSortId}">删除转发</a>
								</c:if>
								<c:if test="${log.author.userId == user.userId }">
									<a href="${pageContext.request.contextPath}/log.do?action=deleteLog&logId=${log.logId}">删除</a>
								</c:if>
								<c:if test="${log.author.userId == user.userId }">
									<a href="${pageContext.request.contextPath}/log.do?action=updateLogUI&logId=${log.logId}">编辑</a>
								</c:if>
								<c:if test="${log.author.userId == user.userId }">
									<a href="javascript:void(0);" class="moveLogBtn"
										logId="${log.logId }" lsId="${log.logSort.logSortId }">移动</a>
								</c:if>
							</span>
							<span class="pull-right"><fmt:formatDate value="${log.publishTime }" type="date"/></span>
							<span class="pull-right">(${log.viewCount }/${log.commentCount })</span>
							
						</li>
					</c:forEach>
					</ul>
				</div>
				<div align="center">
					<jsp:include page="../../public/pager.jsp">
						<jsp:param value="log.do?action=showLogList&logSortId=${logSort.logSortId }" name="path"/>
					</jsp:include>	
				</div>
			</div>
			
			<div id="log_sort_list">
				<div class="div_title">
					日志分类列表
				</div>
				<ul>
					<c:if test="${empty logSorts }">
						您还没有日志分类<a href="javascript:void(0);" id="a_new_sort">新建分类</a>
					</c:if>
					<c:forEach items="${logSorts }" var="logSort">
						<li>
							<c:if test="${logSort.sortName != '我的日志' }">
								<a href="${pageContext.request.contextPath}/log.do?action=showLogList&logSortId=${logSort.logSortId}">${logSort.sortName }</a>(${logSort.logCount })
							</c:if>
							<c:if test="${logSort.sortName == '我的日志' }">
								<a href="${pageContext.request.contextPath}/log.do?action=showTransferLogList&logSortId=${logSort.logSortId}">${logSort.sortName }</a>(${logSort.logCount })
							</c:if>
							<c:if test="${logSort.sortName != '我的日志' }">
							<span class="pull-right">
								<a href="${pageContext.request.contextPath}/log.do?action=deleteLogSort&logSortId=${logSort.logSortId}&userId=${user.userId}">删除</a>
								<a sortName="${logSort.sortName }" 
									lsId="${logSort.logSortId }" href="javascript:void(0);" class="renameBtn">重命名</a>
							</span>
							</c:if>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
	<div id="create_div">
 		<form action="${pageContext.request.contextPath}/log.do?action=createLogSort" onsubmit="return goSubmit();" method="post" id="logSortId">
	 		分组名&nbsp;&nbsp;
	 		<input id="div_text" required="required" onkeyup="javascript:checkLogSortName(this);"   name="sortName"/>
	 		<input type="submit" class="btn btn-primary btn-sm" style="margin-left:35px" value="添加"/>
	 		<div id="res_sort_name_hava" class="error"></div>
	 		<input type="hidden" value="${user.userId }" name="userId"/>
	 		<input type="hidden" id="check_sort_name" value=""/>
	 		
 		</form>
 	</div> 
 	<!-- 重命名日志分类 -->
 	<div id="rename_dialog">
 		<form action="${pageContext.request.contextPath}/log.do?action=reNameLogSort" onsubmit="return reGoSubmit();"
 			 method="post" id="newLogSortId">
	 		分组名&nbsp;&nbsp;
	 		<input id="input_sort_name" required="required" onkeyup="javascript:checkreLogSortName(this);" name="sortName"/>
	 		<input type="submit" class="btn btn-primary btn-sm" style="margin-left:35px" value="修改"/>
	 		<div id="res_resort_name_hava" class="error"></div>
	 		<input type="hidden" id="lsId" value="" name="logSortId"/>
	 		<input type="hidden" id="check_resort_name" value=""/>
	 		
 		</form>
 	</div> 
 	<!-- 移动日志 -->
 	<div id="move_log_to_sort_dialog">
 		<form action="${pageContext.request.contextPath}/log.do?action=moveLog"
 			 method="post">
	 		移动到:<select name="logSortId">
	 				<c:forEach items="${logSorts }" var="ls">
	 					<option value="${ls.logSortId }">${ls.sortName }</option>
	 				</c:forEach>
	 			</select>
	 		<input type="hidden" id="moveLogId" value="" name="logId"/>
	 		<input type="submit" class="btn btn-primary btn-sm" value="移动"/>
 		</form>
 	</div> 
	<jsp:include page="../../public/ownner.jsp"></jsp:include> 
	<script type="text/javascript">
		$(function(){
			var create_friend_sort_dialog = $("#create_div");
			var rename_dialog =$("#rename_dialog");
			var move_log_to_sort_dialog = $("#move_log_to_sort_dialog");
			create_friend_sort_dialog.hide();
			rename_dialog.hide();
			move_log_to_sort_dialog.hide();
			
			$("#create_btn,#a_new_sort").click(function(){
				create_friend_sort_dialog.show().dialog({
					title: "新建日志分类",    
				    width: 340,    
				    height: 150, 
				    modal: true   	
				});
			});
			$(".moveLogBtn").click(function(){
				move_log_to_sort_dialog.show().dialog({
					title: "移动日志",    
				    width: 340,    
				    height: 120, 
				    modal: true   	
				});
				$("#moveLogId").val($(this).attr("logId"));
			});
			
			$(".renameBtn").click(function(){
				rename_dialog.show().dialog({
					title: "重命名日志分类",    
				    width: 340,    
				    height: 150, 
				    modal: true   	
				});
				$("#input_sort_name").val($(this).attr("sortName"));
				$("#lsId").val($(this).attr("lsId"));
			});
		});
		$("#logSortId").validate({
			rules:{
				sortName:{
					
					maxlength:10
				}
			},
			messages:{
				sortName:{
					
					maxlength:"您的标题长度不能大于10"
				}
			}
		});
		$("#newLogSortId").validate({
			rules:{
				sortName:{
					
					maxlength:10
				}
			},
			messages:{
				sortName:{
					
					maxlength:"您的标题长度不能大于10"
				}
			}
		});
		function checkLogSortName(t){
  			var sortName = $(t).val();
  			$("#res_sort_name_hava").text("");
  			if(sortName != ""){
  				$.ajax({
  					url: getRootPath() + "/ajaxServlet",
  					data:{"action":"checkLogSortName","sortName":$(t).val()},
  					type:'post',
  					dataType:"json",
  					success:function(res){
  						var msg = res.msg;
  						if(msg == "T"){
  							$("#res_sort_name_hava").text("该日志分类已存在!");
  							$("#check_sort_name").attr('value',"该日志分类已经存在，无法再次创建!");
  						}else{
  							$("#check_sort_name").attr('value',"");	
  						}
  					}
  				});
  			}
		}
		function checkreLogSortName(t){
  			var sortName = $(t).val();
  			$("#res_sort_name_hava").text("");
  			if(sortName != ""){
  				$.ajax({
  					url: getRootPath() + "/ajaxServlet",
  					data:{"action":"checkLogSortName","sortName":$(t).val()},
  					type:'post',
  					dataType:"json",
  					success:function(res){
  						var msg = res.msg;
  						if(msg == "T"){
  							$("#res_resort_name_hava").text("该日志分类已存在!");
  							$("#check_resort_name").attr('value',"该日志分类已经存在，无法再次创建!");
  						}else{
  							$("#res_resort_name_hava").text("");
  							$("#check_resort_name").attr('value',""); 							
  						}
  					}
  				});
  			}
		}
		function goSubmit(){
 			var isExistInfo = $("#check_sort_name").val();
 			if(isExistInfo.length > 0){
 				alert(isExistInfo);
 				return false;
 			}
 			return true;
 		}
		function reGoSubmit(){
 			var isExistInfo = $("#check_resort_name").val();
 			if(isExistInfo.length > 0){
 				alert(isExistInfo);
 				return false;
 			}
 			return true;
 		}
	</script>
	
</body>
</html>
