<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<link type="text/css" rel="stylesheet" href="<%=basePath%>css/reset.css" />
	<link type="text/css" rel="stylesheet" href="<%=basePath%>css/style.css" />
	<script language="javascript">
	var checkedIndex= 4;
	var basePath = '<%=basePath%>';
	</script>
	<script type="text/javascript" src="<%=basePath%>js/jquery-1.10.1.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jquery.whpage.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/public.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/menu.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/admin.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/validator.js"></script>
</head>
<body>

	<div class="outer-header">
		<div class="inner-banner">
			<div class="inner-banner-user">欢迎您， <span class="user-username">${name}</span></div>
		</div>
	</div>

<div class="ny-header">
	<div class="ny-header-z">
		  <ul class="ny-menu">
          	<c:forEach items="${menus}" var="menu" varStatus="status">
			<li  id="${status.index}"><a href="<%=basePath %>${menu.url}" target="_self">${menu.name}</a>
			<c:if test="${menu.subMenus==null}">
				<ul class="sub-menu">
					<c:forEach items="${menu.subMenus}" var="subMenu" varStatus="subStatus">
						<li  id="SI${subStatus.index}"><a id="S${menu.id}" href="<%=basePath %>${menu.url}" target="_self">${menu.name}</a></li>
					</c:forEach>
				</ul>
			</c:if>
			</li>
			</c:forEach>
          </ul>
          <ul class="ny-nav"> 
            <li><span class="ny-icons"></span><span class="ny-yhm"></span></li>
            <li><span class="ny-fgx"></span></li>
            <li><span class="ny-icons ny-icons2"></span><a href="<%=basePath%>logout" class="ny-tc">退出</a></li>
          </ul>
      </div>	
 </div>
<div class="ny-container">
	<div style="height:48px; line-height:40px; margin:0 60px;">
		<span style="padding:10px;"><label>用户名：</label><input type="text" value="" class="input_on" placeholder="请输入用户名或帐号" id="keyword"/></span>
		<span style="padding:10px;"><button class="btn btn-default" type="button" id="searchUser"> 搜索用户</button></span>
		<span style="float:right;"><button type="button" class="btn btn-default" id="addUser"> 添加用户</button></span>
	</div>
	<div class="list-container">
		<div class="list01">
			<div class="table">
				<table border="0">
					<thead>
						<tr>
							<td>序号</td>
							<td>姓名</td>
							<td>帐号</td>
							<td>密码</td>
							<td>邮箱</td>
							<td>手机</td>
							<td>角色岗位</td>
							<td>用户操作</td>
						</tr>
					</thead>
					<tbody id="tableList">
					</tbody>
				</table>
			</div>
		</div>
		<div class="page_v1">
		   <div class="right" id="ajax_list_page"></div>
	 	</div>	
	</div>
</div>

</body>
</html>