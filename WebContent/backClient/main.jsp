<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>朋友社区后台管理系统</title>
<frameset rows="101px,*,50px" cols="*" frameborder="no" border="0" framespacing="0" style="padding:0px;">
  <frame  style="padding:0px;" src="${pageContext.request.contextPath}/backClient/public/top.jsp" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" title="topFrame" />
  <frameset  style="padding:0px;" cols="213,*" frameborder="no" border="0" framespacing="0">
    <frame  style="padding:0px;" src="${pageContext.request.contextPath}/backClient/public/left.jsp" name="leftFrame" scrolling="No" noresize="noresize" id="leftFrame" title="leftFrame" />
    <frame  style="padding:0px;" src="${pageContext.request.contextPath}/backClient/welcome.jsp" name="mainFrame" id="mainFrame" title="mainFrame" />
  </frameset>
  <frame style="padding:0px;" src="${pageContext.request.contextPath}/backClient/public/bottom.jsp" name="topFrame" scrolling="No" noresize="noresize" id="bottomFrame" title="bottomFrame" />
</frameset>
<noframes>
</noframes>
</head>
</html>