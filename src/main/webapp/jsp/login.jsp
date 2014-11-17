<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + "/" + path + "/";
	if (request.getServerPort() != 80) {
		basePath = request.getScheme() + "://"
				+ request.getServerName() + ":"
				+ request.getServerPort() + path + "/";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=basePath%>css/main.css" rel="stylesheet" type="text/css" />
<!--javascript-->
<script type="text/javascript" src="<%=basePath%>js/jquery-1.10.1.js"></script>
<title>欢迎登录采集系统平台</title>
</head>
<body style="background-color: #ccc">
<div id="login_header"><img src="<%=basePath%>images/top_logo.png"></img></div>
<div id="login_body">
	<div id="login_body_content" class="body_center">
	<form action='<c:url value="/j_spring_security_check"/>' method="post">
		<div class="halign_center"><span>帐户：</span> <input type="text" name='j_username' class="input_onblur" onblur="this.className='input_onblur'" onfocus="this.className='input_onfocus'"></input></div>
		<div class="halign_center" style="margin-top:24px;"><span>密码：</span> <input type="password" name='j_password' class="input_onblur" onblur="this.className='input_onblur'" onfocus="this.className='input_onfocus'"></input></div>
		<div class="login_button"><input type="submit" value="立 即 登 陆"></input></div>
	</form>
	</div>
</div>

</body>
</html>


