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
	<title>查找好友</title>
	<link href="<%=basePath%>css/user/user_selfInfo.css" type="text/css" rel="stylesheet"/>
	<link href="<%=basePath%>css/col_menu.css" type="text/css" rel="stylesheet"/>
	<link href="<%=basePath%>css/friend/friend_search.css" type="text/css" rel="stylesheet"/>
	<jsp:include page="../../public/common.jsp"></jsp:include>
	</head>
    <body>
	<jsp:include page="../../public/nav.jsp"></jsp:include>
	<jsp:include page="../../public/menu.jsp"></jsp:include>
	<div id="self_main_content">
		<div id="self_main_content_left">
			<div id="self_main_content_headArea">
				<span class="self_mgang_test_head">
					<img alt="" src="${pageContext.request.contextPath}/${user.head }"
					width="160px" height="160px">
				</span>
				<ul id="main_content_left_function_list">
					<li><a href="${pageContext.request.contextPath}/friend.do?action=showFriendSort">好友分组</a></li>
					<li><a href="#" onclick="showPanel('self_info')">可能认识的人</a></li>
				</ul>
			</div>
		</div>
		<div id="self_main_content_center">
			<div class="panel panel-default" id="self_info">
			  <div class="panel-heading">
			    <h3 class="panel-title">查找条件</h3>
			  </div>
			  <div id="list" >
			  	  <div class="btn-group">
			  	  	<button type="button" id="select_btn" class="btn btn-info" onclick="show_nickname()">按昵称</button>
			  	  	<button type="button" id="select_btn" class="btn btn-default" onclick="show_sex()">按性别</button>
			  	  	<button type="button" id="select_btn" class="btn btn-info" onclick="show_age()">按年龄</button>
			  	  	<button type="button" id="select_btn" class="btn btn-default" onclick="show_homeTown()">按籍贯</button>
			  	  </div>
			  	  <div id="div_nickname">
					  <form onkeypress="return keySubmit(event)" id="form1" action="${pageContext.request.contextPath}/friend.do?action=searchByNickname" method="post">
						<span id="font_size">昵称</span>&nbsp;
						<input id="nick_name" name="userNickName" placeholder=" 昵称"/><br/>
					  </form>
				  </div>
				  <div id="div_sex">
				   <form onkeypress="return keySubmit(event)" id="form2" action="${pageContext.request.contextPath}/friend.do?action=searchBySex" method="post">
					<span>性别</span>&nbsp;
					<input id="sex" type="radio" name="sex" value="男"/>
					<span>男</span>&nbsp;
					<input type="radio" name="sex" value="女"/> 
					<span>女</span><br/>				  
				   </form>
				 </div>
				 <div id="div_age">
				   <form onkeypress="return keySubmit(event)" id="form3" action="${pageContext.request.contextPath}/friend.do?action=searchByAge" method="post">
					<span>年龄</span>&nbsp;
					<input id="age" name="age" placeholder=" 年龄"/><br/>
				  </form>
				 </div>
				 <div id="div_homeTown">
				   <form onkeypress="return keySubmit(event)" id="form4" action="${pageContext.request.contextPath}/friend.do?action=searchByHomeTown" method="post">
					<span>籍贯</span>&nbsp;
					 <select name="homeTown">
						<option value="">---选择---</option>
						<option value="北京市">&nbsp;&nbsp;北京市</option>
						<option value="天津市 ">&nbsp;&nbsp;天津市</option>
						<option value="上海市">&nbsp;&nbsp;上海市</option>
						<option value="重庆市">&nbsp;&nbsp;重庆市</option>
						<option value="河北省">&nbsp;&nbsp;河北省</option>
						<option value="山西省">&nbsp;&nbsp;山西省</option>
						<option value="内蒙古">&nbsp;&nbsp;内蒙古</option>
						<option value="辽宁省">&nbsp;&nbsp;辽宁省</option>
						<option value="吉林省">&nbsp;&nbsp;吉林省</option>
						<option value="黑龙江省">&nbsp;&nbsp;黑龙江省</option>
						<option value="江苏省">&nbsp;&nbsp;江苏省</option>
						<option value="浙江省">&nbsp;&nbsp;浙江省</option>
						<option value="安徽省">&nbsp;&nbsp;安徽省</option>
						<option value="福建省">&nbsp;&nbsp;福建省</option>
						<option value="江西省">&nbsp;&nbsp;江西省</option>
						<option value="山东省">&nbsp;&nbsp;山东省</option>
						<option value="河南省">&nbsp;&nbsp;河南省</option>
						<option value="湖北省">&nbsp;&nbsp;湖北省</option>
						<option value="湖南省">&nbsp;&nbsp;湖南省</option>
						<option value="广东省">&nbsp;&nbsp;广东省</option>
						<option value="广西">&nbsp;&nbsp;广西</option>
						<option value="海南省">&nbsp;&nbsp;海南省</option>
						<option value="四川省">&nbsp;&nbsp;四川省</option>
						<option value="贵州省">&nbsp;&nbsp;贵州省</option>
						<option value="云南省">&nbsp;&nbsp;云南省</option>
						<option value="西藏">&nbsp;&nbsp;西藏</option>
						<option value="陕西省">&nbsp;&nbsp;陕西省</option>	
						<option value="甘肃省">&nbsp;&nbsp;甘肃省</option>
						<option value="青海省">&nbsp;&nbsp;青海省</option>
						<option value="宁夏">&nbsp;&nbsp;宁夏</option>
						<option value="新疆">&nbsp;&nbsp;新疆</option>
						<option value="香港">&nbsp;&nbsp;香港</option>
						<option value="澳门">&nbsp;&nbsp;澳门</option>
						<option value="台湾省">&nbsp;&nbsp;台湾省</option>
					 </select><br/>
				  </form>
				 </div>
				 <button id="input_mar" class="btn btn-primary btn-sm" onclick="submit_form()">搜索</button>&nbsp;&nbsp;&nbsp;
				 <button id="input_mar" class="btn btn-primary btn-sm" onclick="clear_form()">清空</button>
			  </div>  
		    </div>
		 </div>
	    </div>
	  <jsp:include page="../../public/ownner.jsp"></jsp:include>
	  <script type="text/javascript">
	  	jQuery(document).ready(function(){
	  		$("nick_name").focus();
	  	});
	  	var status = "name";
	  	function $(id){
	  		return document.getElementById(id);
	  	}
	  	function check_nickname(){
	  		if ($('nick_name').value.length > 5){
	  			alert("您输入的昵称太长！");
	  			return false;
	  		}
	  		var flag = 0;
	  		jQuery.ajax({
				url: getRootPath() + "/ajaxServlet",
				data:{"action":"checkNickName","userNickName":$('nick_name').value},
				type:'post',
				async:false,
				dataType:"json",
				success:function(response){
					var msg = response.msg;
					if(msg == "T"){
						flag = 1;
					}else{
						alert("该昵称不存在");
					}
				}
			});
	  		if (flag == 1) return true;
	  		else
	  			return false;
	  	}
	  	function check_age(){
	  		if ($("age").value.length > 3){
	  			alert("请输入一个合法的年龄！");
	  			return false;
	  		}
	  		var age = $("age").value.toString();
	  		var reg = new RegExp(/[1-9]([0-9]){0,2}/);
	  		//alert(age.match(reg));
	  		if (age.match(reg) != null || age == "0") return true;
	  		alert("请输入一个合法的年龄！");
	  		return false;
	  	}
	  	function show_nickname(){
	  		clearForm("form1");
	  		clearForm("form2");
	  		clearForm("form3");
	  		clearForm("form4");
	  		status = "name";
	  		$("div_nickname").style.display="";
	  		$("nick_name").focus();
	  		$("div_sex").style.display="none";
	  		$("div_age").style.display="none";
	  		$("div_homeTown").style.display="none";
	  	}
	  	function show_sex(){
	  		status = "sex";
	  		clearForm("form1");
	  		clearForm("form2");
	  		clearForm("form3");
	  		clearForm("form4");
	  		$("div_sex").style.display="inherit";
	  		$("sex").checked=true;
	  		$("sex").focus();
	  		$("div_nickname").style.display="none";
	  		$("div_age").style.display="none";
	  		$("div_homeTown").style.display="none";
	  	}
	  	function show_age(){
	  		status = "age";
	  		clearForm("form1");
	  		clearForm("form2");
	  		clearForm("form3");
	  		clearForm("form4");
	  		$("div_age").style.display="inherit";
	  		$("age").focus();
	  		$("div_sex").style.display="none";
	  		$("div_nickname").style.display="none";
	  		$("div_homeTown").style.display="none";
	  	}
	  	function show_homeTown(){
	  		status = "home_town";
	  		clearForm("form1");
	  		clearForm("form2");
	  		clearForm("form3");
	  		clearForm("form4");
	  		$("div_homeTown").style.display="inherit";
	  		$("div_sex").style.display="none";
	  		$("div_age").style.display="none";
	  		$("div_nickname").style.display="none";
	  	}
	  	function submit_form(){
	  		if (status == "name"){
	  			if(check_nickname() == true){
	  				$("form1").submit();
	  			}
	  		}
	  		if (status == "sex") $("form2").submit();
	  		if (status == "age"){
	  			if (check_age() == true){
	  				$("form3").submit();
	  			}
	  		}
	  		if (status == "home_town") $("form4").submit();
	  		
	  	}
	  	function clear_form(){
	  		if (status == "name") clearForm("form1");
	  		if (status == "sex") clearForm("form2");
	  		if (status == "age") clearForm("form3");
	  		if (status == "home_town") clearForm("form4");
	  	}
	  	function clearForm(id){
	  		var formObj = document.getElementById(id);
	  		if(formObj == undefined)
	  		{
	  			return;
	  		}
	  		for(var i=0; i<formObj.elements.length; i++)
	  		{
		  		if(formObj.elements[i].type == "text")
		  		{
		  			formObj.elements[i].value = "";
		  		}
	  			else if(formObj.elements[i].type == "password")
	  			{
		  			formObj.elements[i].value = "";
		  		}
	  			else if(formObj.elements[i].type == "radio")
	  			{
		  			formObj.elements[i].checked = false;
		  		}
		  		else if(formObj.elements[i].type == "checkbox")
		  		{
		  			formObj.elements[i].checked = false;
		  		}
		  		else if(formObj.elements[i].type == "select-one")
		  		{
		  			formObj.elements[i].options[0].selected = true;
		  		}
		  		else if(formObj.elements[i].type == "select-multiple")
		  		{
			  		for(var j = 0; j < formObj.elements[i].options.length; j++)
			  		{
				  		formObj.elements[i].options[j].selected = false;
			  		}
		  		}
		  		else if(formObj.elements[i].type == "file")
		  		{
			  		var file = formObj.elements[i];
			  		if(file.outerHTML)
			  		{
			  			file.outerHTML = file.outerHTML;
			  		}
			  		else
			  		{
			  			file.value = ""; // FF(包括3.5)
			  		}
		  		}
		  		else if(formObj.elements[i].type == "textarea")
		  		{
		  			formObj.elements[i].value = "";
		  		}
	  		} 
	  	}
	  	function keySubmit(event){
	  		if (event.keyCode == 13){
	  			//jQuery("#list #input_mar:eq(0)").click();
	  			submit_form();
	  			return false;
	  		}
	  	}
	  </script>
    </body>
</html>
