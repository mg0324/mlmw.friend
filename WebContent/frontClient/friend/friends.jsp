<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" import="java.util.ArrayList,com.friend.vo.FriendSort;"%>
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
	<title>${friendSort.sortName }-分组</title>
	<link href="<%=basePath%>css/user/user_selfInfo.css" type="text/css" rel="stylesheet"/>
	<link href="<%=basePath%>css/friend/friend_list.css" type="text/css" rel="stylesheet"/>
	
	<jsp:include page="../../public/common.jsp"></jsp:include>
	</head>
    <body>
	<jsp:include page="../../public/nav.jsp"></jsp:include>
	<jsp:include page="../../public/menu.jsp"></jsp:include>
	<div id="self_main_content">
		<div id="self_main_content_left">
			<div id="self_main_content_headArea">
				<span class="self_mgang_test_head">
					<img alt="" src="${user.head }"
					width="160px" height="160px">
				</span>
				<ul id="main_content_left_function_list">
					<li><a href="<%=basePath%>frontClient/friend/searchUser.jsp">查找好友</a></li>
					<li><a href="${pageContext.request.contextPath}/friend.do?action=showFriendSort">好友分组</a></li>
					<li><a href="#" onclick="showPanel('self_info')">可能认识的人</a></li>
				</ul>
			</div>
		</div>
		<div style="height:432px;" id="self_main_content_center">
			<div style="margin-bottom:0px;height:380px;" class="panel panel-default" id="self_info">
			  <div class="panel-heading">
			    <h3 class="panel-title">${friendSort.sortName }</h3>
			  </div>
			  <div id="list">
			  	<div id="div-case">
				  	<c:forEach items="${users }" var="u">
				  	<div class="case-unit">
				  	  <div class="friend-left">
				      	<img title="${u.selfWrite }" class="picture" src="${pageContext.request.contextPath}/${u.head}"/>
				      </div>
				      <div class="friend-right">
						<a  href="${pageContext.request.contextPath}/friend.do?action=toFriendMainUI&userId=${u.userId}">${u.userNickName }</a> 
						<div id="focu${u.userId}" class="focus" onmouseover="change_color(${u.userId})" onmouseout="recover_color(${u.userId})">
							<c:if test="${u.isFocused == \"1\"}">
								<span id="${u.userId }" class="ff" onclick="focus1(${u.userId })">
									<span style="color:white;">已关注</span>
								</span>
							</c:if>
							<c:if test="${u.isFocused == \"0\"}">
								<span id="${u.userId }" class="uff" onclick="focus2(${u.userId })">
									<span style="color:white;">加关注</span>
								</span>
							</c:if>
						</div>
				        <div class="operation">						  
						  <ul id="oper${u.userId}" class="operation-ul">
						  	  <button onclick="tog1(${u.userId})" class="oper" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							     更多<span class="caret"></span>
							  </button>
						  	  <li style="background-color:#8FC0E7;color:white;position:relative;z-index:8;">  
							 	  <ul id="move${u.userId}" class="move-ul">
							 	 	  <span class="a1" title="点击展开/折叠" onclick="tog2(${u.userId})">&nbsp;&nbsp;移动到&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
									  <c:forEach items="${friendSorts }" var="fs">
									  	 <li>
									  	 	<a class="a2" href="${pageContext.request.contextPath}/friend.do?action=moveTo&sortId=${fs.friendSortId}&userId=${u.userId}">&nbsp;&nbsp;&nbsp;${fs.sortName }</a>
									  	 </li>
									  </c:forEach>	
								  </ul>
							  </li>
							  <c:if test="${u.isBlacked == \"0\" }">
							  <li style="background-color:#8FC0E7;color:white;">
							  	<a class="a1" href="${pageContext.request.contextPath}/friend.do?action=setBlack&userId=${u.userId }" id="black-btn" class="btn btn-primary btn-sm" role="button">&nbsp;&nbsp;拉黑&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
							  </li>
							  </c:if>
						  </ul>
						</div>
				      </div>
					</div>
					</c:forEach>
				</div>
			  </div>  
		    </div>
		    <div align="center">
				<jsp:include page="../../public/pager.jsp">
					<jsp:param value="friend.do?action=showFriends&sortId=${friendSort.friendSortId }" name="path"/>
				</jsp:include>	
			</div>
		 </div>
	    </div>
	  <jsp:include page="../../public/ownner.jsp"></jsp:include>
	  <script type="text/javascript">
	  	var status = 0;
	  	function tog1(x){
	  		$("#oper"+x).children("li").toggle();	
	  	}
		function tog2(x){
			$("#move"+x).children("li").toggle();
			if (status == 0){
				
			}
	  	}
		
	  	var flag1 = 1;
	  	var flag2 = 0;
	  	function focus1(x){
	  	    if (flag1 == 1){
	  	    	unFocusFriend(x);
	  	    	flag1 = 0;
	  	    } else{
	  	    	focusFriend(x);
	  	    	flag1 = 1;
	  	    }
	  	}
	  	function focus2(x){
	  		if (flag2 == 1){
	  			unFocusFriend(x);
	  			flag2 = 0;
	  		}else{
	  			focusFriend(x);
	  			flag2 = 1;
	  		}
	  	}
	  	function focusFriend(x){
			$.ajax({
				url: getRootPath() + "/ajaxServlet",
				data:{"action":"focusFriend","userId":x},
				type:'post',
				dataType:"json",
				success:function(response){
					var msg = response.msg;
					if(msg == "T"){
						$('#'+x).html("已关注");
						$('#'+x).css({"color": "white"});
					}else{
						alert('关注失败');
					}
				}
			});
		}
	  	function unFocusFriend(x){
			$.ajax({
				url: getRootPath() + "/ajaxServlet",
				data:{"action":"unFocusFriend","userId":x},
				type:'post',
				dataType:"json",
				success:function(response){
					var msg = response.msg;
					if(msg == "T"){
						$('#'+x).html("加关注");
						$('#'+x).css({"color": "white"});
					}else{
						alert('取消关注失败');
					}
				}
			});
		}
	  	function change_color(x){
	  		$("#focu"+x).css("background-color","#93DBE8");
	  	}
	  	function recover_color(x){
	  		document.getElementById("focu"+x).style.backgroundColor="#8FC0E7";
	  	}
	  </script>
    </body>
</html>
