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
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>浏览照片</title>
<link href="<%=basePath%>css/album/browsePhoto.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<div class="mod18">
	<span id="prev" class="btn prev"></span>
	<span id="next" class="btn next"></span>
	<span id="prevTop" class="btn prev"></span>
	<span id="nextTop" class="btn next"></span>
	<div id="picBox" class="picBox">
		<ul class="cf">
			<li>
				<img width="680px" height="380px" src="${pageContext.request.contextPath}/image/photoFirst.png" alt="" />
				<span></span>
			</li>
			<c:forEach items="${requestScope.photoList }" var="photo">
			<li>
				<div id="pic_list" style="width:680px; text-align: center; height: 510px;margin:0px;padding:0px;">
					<div id="move_color" onfocus="changeColor()">
						<img src="${photo.photoPath }" alt="" />
						<script type="text/javascript">
							$(document).ready(function(){
								    $("#pic_list img").each(function(){    
								        var width = 670;
								        var height = 150;
								        var image = $(this);
								        if (image.width() > image.height()){
								           if(image.width()>width){
								            image.width(width);
								            image.height(width/image.width()*image.height());
								           }
								        }
								        else{
								           if(image.height()>height){
								            image.height(height);
								            image.width(height/image.height()*image.width());
								           }
								        }
								    });
								});
					</script>
					</div>
					<span><fmt:formatDate value="${photo.uploadTime}" pattern="yyyy-MM-dd"/></span>
				</div>
			</li>
			</c:forEach>
		</ul>
	</div>
	
	<div id="listBox" class="listBox">
		<ul class="cf">
			<li class="on"><i class="arr2"></i><img width="118" height="64" src="${pageContext.request.contextPath}/image/photoFirst.jpg"/></li>
			<c:forEach items="${requestScope.photoList }" var="photo">
			<li><i class="arr2"></i><img width="118" height="64" src="${photo.photoPath }"/></li>
			</c:forEach>
		</ul>
	</div>
	
</div>
<script type="text/javascript">

function changeColor()
{
	$(this).style.backgroundColor="green"
}
(function(){
	
	function G(s){
		return document.getElementById(s);
	}
	
	function getStyle(obj, attr){
		if(obj.currentStyle){
			return obj.currentStyle[attr];
		}else{
			return getComputedStyle(obj, false)[attr];
		}
	}
	
	function Animate(obj, json){
		if(obj.timer){
			clearInterval(obj.timer);
		}
		obj.timer = setInterval(function(){
			for(var attr in json){
				var iCur = parseInt(getStyle(obj, attr));
				iCur = iCur ? iCur : 0;
				var iSpeed = (json[attr] - iCur) / 5;
				iSpeed = iSpeed > 0 ? Math.ceil(iSpeed) : Math.floor(iSpeed);
				obj.style[attr] = iCur + iSpeed + 'px';
				if(iCur == json[attr]){
					clearInterval(obj.timer);
				}
			}
		}, 30);
	}

	var oPic = G("picBox");
	var oList = G("listBox");
	
	var oPrev = G("prev");
	var oNext = G("next");
	var oPrevTop = G("prevTop");
	var oNextTop = G("nextTop");

	var oPicLi = oPic.getElementsByTagName("li");
	var oListLi = oList.getElementsByTagName("li");
	var len1 = oPicLi.length;
	var len2 = oListLi.length;
	
	var oPicUl = oPic.getElementsByTagName("ul")[0];
	var oListUl = oList.getElementsByTagName("ul")[0];
	var w1 = oPicLi[0].offsetWidth;
	var w2 = oListLi[0].offsetWidth;

	oPicUl.style.width = w1 * len1 + "px";
	oListUl.style.width = w2 * len2 + "px";

	var index = 0;
	
	var num = 5;
	var num2 = Math.ceil(num / 2);

	function Change(){

		Animate(oPicUl, {left: - index * w1});
		
		if(index < num2){
			Animate(oListUl, {left: 0});
		}else if(index + num2 <= len2){
			Animate(oListUl, {left: - (index - num2 + 1) * w2});
		}else{
			Animate(oListUl, {left: - (len2 - num) * w2});
		}

		for (var i = 0; i < len2; i++) {
			oListLi[i].className = "";
			if(i == index){
				oListLi[i].className = "on";
			}
		}
	}
	
	oNextTop.onclick = oNext.onclick = function(){
		index ++;
		index = index == len2 ? 0 : index;
		Change();
	}

	oPrevTop.onclick = oPrev.onclick = function(){
		index --;
		index = index == -1 ? len2 -1 : index;
		Change();
	}

	for (var i = 0; i < len2; i++) {
		oListLi[i].index = i;
		oListLi[i].onclick = function(){
			index = this.index;
			Change();
		}
	}
	
})()
</script>
</body>
</html>