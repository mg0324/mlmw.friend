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
	<title>查询结果</title>
	<link href="<%=basePath%>css/user/user_selfInfo.css" type="text/css" rel="stylesheet"/>
	<link href="<%=basePath%>css/friend/friend_user_list.css" type="text/css" rel="stylesheet"/>
	<link href="<%=basePath%>css/friend/prompt_window.css" type="text/css" rel="stylesheet"/>
	<script type="text/javascript" src="<%=basePath%>js/prompt-window.js"></script>
	
	<jsp:include page="../../public/common.jsp"></jsp:include>
	</head>
    <body>
	<jsp:include page="../../public/nav.jsp"></jsp:include>
	<jsp:include page="../../public/menu.jsp"></jsp:include>
	<div style="margin-bottom:-4px;height:416px;" id="self_main_content">
		<div id="self_main_content_left">
			<div id="self_main_content_headArea">
				<span class="self_mgang_test_head">
					<img alt="" src="${user.head }"
					width="160px" height="160px">
				</span>
				<ul id="main_content_left_function_list">
					<li><a href="${pageContext.request.contextPath}/friend.do?action=showFriendSort">好友分组</a></li>
					<li><a href="<%=basePath%>frontClient/friend/searchUser.jsp">查找好友</a></li>
					<li><a href="#" onclick="showPanel('self_info')">可能认识的人</a></li>
				</ul>
			</div>
		</div>
		<div style="height:405px;" id="self_main_content_center">
			<div style="margin-bottom:0px;" class="panel panel-default" id="self_info">
			  <div class="panel-heading">
			    <h3 class="panel-title">搜索到的用户</h3>
			  </div>
			  <div id="list">
			  	<c:if test="${empty users}"><br/>您输入的搜索条件可能有问题，请重新搜索......</c:if>
			    <c:forEach items="${users }" var="u">
			    <div class="user-unit">
			    	<div class="user-unit-left">
			    		<img src="${u.head }" title="${u.selfWrite }"/>
			    	</div>
			    	<div class="user-unit-right">
			    		<div class="message">
			    			<div>${u.userNickName}</div>
			    			<div style="margin-top:4px;margin-bottom:5px;">
				    			<script>
				    				var birth = new Date("${u.birthday}");
				    				var current = new Date();
				    				document.write(current.getYear()-birth.getYear()+"岁");//显示年龄
				    				
				    				var homeTown = "${u.homeTown}";
				    				var reg1 = new RegExp(/\S+省\S+市\S+/g);  //xx省xx市xx
				    				var reg2 = new RegExp(/(\S+市){2}\S+/g);  //xx市xx市xx（直辖市）
				    				var reg3 = new RegExp(/\S+市\S+/g);           //xx市xx（例如特别行政区等）
				    				var h = "", h1, h2;
				    				if (homeTown.match(reg1) != null){
				    					homeTown = homeTown.replace(/-/g, "");   //替换所有的“-”符
					    				var index1 = homeTown.search("省");
					    				var index2 = homeTown.search("市");
				    					var h1 = homeTown.substring(0, index1);
				    					var h2 = homeTown.substring(index1 + 1, index2);
				    					h += h1 + " " + h2;
				    				}else if (homeTown.match(reg2) != null){
				    					homeTown = homeTown.replace(/-/g, "");   //替换所有的“-”符
				    					homeTown = homeTown.split("市");
				    					h1 = homeTown[0];
				    					h2 = homeTown[2].replace("区", "");
				    					h += h1 + " " + h2;
				    				}else if (homeTown.match(reg3) != null){
				    					homeTown = homeTown.split("-");
				    					h1 = homeTown[0];
				    					h2 = homeTown[1].replace("市", "");
				    					h += h1 + " " + h2;
				    				}else{
				    					homeTown = homeTown.split("-");
				    					h = homeTown[0];
				    				}
				    				if (homeTown != "") document.write("|&nbsp;"+h);   //显示籍贯
				    			</script>
			    			</div>
			    		</div>
			    		<div id="add${u.userId}" class="add_div" onclick="addFriend(${u.userId})" title="点击添加好友" onmouseover="change_color(${u.userId})" onmouseout="recover_color(${u.userId})">
				    		<form id="fm${u.userId}" method="post">
				    			<span style="color:white;" class="glyphicon glyphicon-plus"></span>
				    			<span style="color:white;">好友</span>
			    			</form>
			    		</div>
			    	</div>
			    </div>
			    </c:forEach>
			    <%
			    	String action = "friend.do?action=";
				    if (request.getAttribute("method").equals("searchByNickname")){
			    		action += "searchByNickname&userNickName="+request.getAttribute("param");
			    	}
			    	if (request.getAttribute("method").equals("searchBySex")){
			    		action += "searchBySex&sex="+request.getAttribute("param");
			    	}
				    if (request.getAttribute("method").equals("searchByAge")){
			    		action += "searchByAge&age="+request.getAttribute("param");
			    	}
				    if (request.getAttribute("method").equals("searchByHomeTown")){
			    		action += "searchByHomeTown&homeTown="+request.getAttribute("param");
			    	}
				    action += "&transcoding=true";
			    %>
			  </div>
			  <div id="prompt">
		    	<div id="message"><span class="glyphicon glyphicon-ok-sign"></span>&nbsp;&nbsp;操作成功</div>
		      </div>
		    </div>
		    <div align="center">
				<jsp:include page="../../public/pager.jsp">
					<jsp:param value="<%=action %>" name="path"/>
				</jsp:include>
			</div> 
		 </div>
	  </div>
	  <jsp:include page="../../public/ownner.jsp"></jsp:include>
	  <script type="text/javascript">
	  	  function addFriend(x){
	  		  var action = "${pageContext.request.contextPath}/friend.do?action=addFriend&userId="+x;
	  		  $("#fm"+x).attr("action", action).submit();
	  		  show_wind();
	  	  }
		  function change_color(x){
		  	$("#add"+x).css("background-color","#93DBE8");
		  }
		  function recover_color(x){
		  	document.getElementById("add"+x).style.backgroundColor="#8FC0E7";
		  }
      </script>
    </body>
</html>
