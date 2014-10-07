<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<!--  
@author 周皓 2014/07/23
-->
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>${user.userNickName }的好友分组列表</title>
	<link href="<%=basePath%>css/user/user_selfInfo.css" type="text/css" rel="stylesheet"/>
	<link href="<%=basePath%>css/friend/friend_sort_list.css" type="text/css" rel="stylesheet"/>
	<link href="<%=basePath%>css/friend/prompt_window.css" type="text/css" rel="stylesheet"/>
	<script type="text/javascript" src="<%=basePath%>js/prompt-window.js"></script>
	<jsp:include page="../../public/common.jsp"></jsp:include>
	</head>
    <body>
	<jsp:include page="../../public/nav.jsp"></jsp:include>
	<jsp:include page="../../public/menu.jsp"></jsp:include>
	<div id="self_main_content" style="height:441px;">
		<div id="self_main_content_left">
			<div id="self_main_content_headArea">
				<span class="self_mgang_test_head">
					<img alt="" src="${user.head }" width="160px" height="160px"/>
				</span>
				<ul id="main_content_left_function_list">
					<li><a href="#" onclick="showPanel('self_info')">可能认识的人</a></li>
				</ul>
			</div>
		</div>
		<div style="height:430px;" id="self_main_content_center">	
			<div style="height:333px;margin-bottom:8px;" class="panel panel-default" id="self_info">
			  <div class="panel-heading">
			    <h3 class="panel-title">分组列表</h3>
			  </div>
			  <div id="list">
				<table class="table table-hover">
					<tr>
						<td id="td_width1">分组名</td>
						<td>人数</td>
						<td id="td_width2">操作</td>
					</tr>
					<c:forEach items="${friendSorts}" var="fs">
						<tr>
					    	<td>
					    	<c:if test="${fs.friendCount > 0}">
					    		<a title="${fs.sortName}" href="${pageContext.request.contextPath}/friend.do?action=showFriends&sortId=${fs.friendSortId}">${fs.sortName }</a>
					    	</c:if>
					    	<c:if test="${fs.friendCount == 0}">
					    		<span title="${fs.sortName}">${fs.sortName}</span>
					    	</c:if>
					    	</td>
					    	<td>${fs.friendCount}</td>
					    	<td>
					    	<c:if test="${fs.sortName eq \"我的好友\" || fs.sortName eq \"黑名单\"}">&nbsp;</c:if>
					    	<c:if test="${fs.sortName ne\"我的好友\" && fs.sortName ne \"黑名单\"}">
					    		<div style="float:left;" class="btn-group">
						    		<%
										long delToken = System.currentTimeMillis();
										request.getSession().setAttribute("delToken", delToken);
									%>
									<a href="${pageContext.request.contextPath}/friend.do?action=deleteSort&sortId=${fs.friendSortId}&delToken=<%=delToken %>" onclick="return delSort()" title="删除该好友分组" class="btn btn-primary btn-sm btn-sm">删除</a>
									<input type="button" class="btn btn-default btn-sm" title="修改分组名称" value="重命名" onclick="showDiv('${fs.friendSortId}')"/>
								</div>
								<div id="rename-div" class="${fs.friendSortId}">
									<form onkeypress="return keyRename(event,${fs.friendSortId})" id="fm1${fs.friendSortId}" action="${pageContext.request.contextPath}/friend.do?action=reNameSort&sortId=${fs.friendSortId}" method="post">
										<div id="input-div" class="input-group">
										  <input id="input_size1${fs.friendSortId}" style="width:90px;height:28px;margin-top:1px;" class="form-control" name="sortName" placeholder="新分组名"/>
										  <%
												long renToken = System.currentTimeMillis();
												request.getSession().setAttribute("renToken", renToken);
										  %>
										  <input type="hidden" name="renToken" value="<%=renToken %>"/>
										  <input type="button" id="input_size2" onclick="submit_form1(${fs.friendSortId})" class="input-group-addon" value="确定"/>
										</div>
									</form>
								</div>
							</c:if>
							</td>
		    			</tr>
		   			</c:forEach>
			    </table>
			    <a id="a_btn" class="btn btn-primary btn-sm" href="<%=basePath%>frontClient/friend/searchUser.jsp" title="查找新朋友">查找好友</a>
			 	<div id="create_div">
			 		<form id="fm2" onkeypress="return keyCreate(event)" action="${pageContext.request.contextPath}/friend.do?action=createSort" method="post">
				 		分组名&nbsp;&nbsp;<input id="div_text" name="sortName" placeholder=" 新分组名"/><br/>
				 		<%
							long creToken = System.currentTimeMillis();
							request.getSession().setAttribute("creToken", creToken);
						%>
				 		<input type="hidden" name="creToken" value="<%=creToken %>"/>
				 		<input onclick="submit_form2()" type="button" class="btn btn-primary btn-sm" value="确定"/>
				 		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				 		<input type="reset" class="btn btn-primary btn-sm" value="取消" onclick="hide_create_div()"/>
			 		</form>
			 	</div> 
			  </div> 
		    </div>
		    <input id="create_btn" type="button" title="创建一个新的好友分组" class="btn btn-primary btn-sm" value="新建分组" />
		    <div align="center">
				<jsp:include page="../../public/pager.jsp">
					<jsp:param value="friend.do?action=showFriendSort" name="path"/>
				</jsp:include>	
		    </div>
		 </div>
	     <div id="prompt">
	    	<div id="message"><span class="glyphicon glyphicon-ok-sign"></span>&nbsp;&nbsp;操作成功</div>
	     </div>
	    </div>
	    <jsp:include page="../../public/ownner.jsp"></jsp:include>
	    <script type="text/javascript">
		    var create_friend_sort_dialog = $("#create_div");
			create_friend_sort_dialog.hide();
			$("#create_btn").click(function(){
				create_friend_sort_dialog.show().dialog({
					title: "新建分组",    
				    width: 260,    
				    height: 160, 
				    modal: true   	
				});
				$("#div_text").focus();
				$("#div_text").val("");
			});
		    function showDiv(x){
		    	$('.'+x).toggle();
		    	$("#input_size1"+x).focus();
		    	$("#input_size1"+x).val("");
		    }
		    function hide_create_div(){
		    	$("#create_div").dialog('close');
		    }
		    function delSort(){
		    	if (confirm("该操作会把该分组内的所有好友移动到:\r\n'我的好友'分组，您确定要删除吗？")){
		    		show_wind();
		    		return true;
		    	}
		    	else
		    		return false;
		    }
		    function check_form(x){
		    	var flag = "0";
		    	$.ajax({
					url: getRootPath() + "/ajaxServlet",
					data:{"action":"checkSortName","sortName":$('#'+x).val()},
					type:'post',
					async:false,
					dataType:"json",
					success:function(response){
						var msg = response.msg;
						if(msg == "T"){
							alert("该分组名已存在");
						}else{
							flag = "1";
						}
					}
				});
		    	if (flag == "1"){
		    		return true;
		    	}
		    	else{
		    		return false;
		    	}
		    }
		    function submit_form1(x){
		    	if ($("#input_size1"+x).val() == ""){
		    		alert("分组名不能为空");
		    		return;
		    	}
		    	if ($("#input_size1"+x).val().length > 4){
					alert("分组名长度不能大于4");
					return;
				}
		    	var r1 = check_form('input_size1'+x);
		    	if (r1 == true){
		    		$('.'+x).toggle();
		    		show_wind();
		    		$('#fm1'+x).submit();
		    	}
		    	return;
		    }
			function submit_form2(){	
				if ($("#div_text").val() == ""){
					alert("分组名不能为空");
		    		return;
				}
				if ($("#div_text").val().length > 4){
					alert("分组名长度不能大于4");
					return;
				}
				var r2 = check_form('div_text');
				if (r2 == true){
					setTimeout("$('#fm2').submit()", 300);
					hide_create_div();
					show_wind();
				}
				return;
		    }
			function keyRename(event, x){
				if (event.keyCode == 13){
					submit_form1(x);
					return false;
				}
			}
			function keyCreate(event){
				if (event.keyCode == 13){
					//submit_form2();
					$("#fm2 input:eq(2)").click();
					return false;
				}
			}
	    </script>
    </body>
</html>
