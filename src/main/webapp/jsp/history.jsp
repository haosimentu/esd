<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	var checkedIndex= 1;
	var basePath = '<%=basePath%>';
</script>
<script type="text/javascript" src="<%=basePath%>js/jquery-1.10.1.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery.whpage.js"></script>
<script type="text/javascript" src="<%=basePath%>js/public.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/menu.js"></script>
<script type="text/javascript" src="<%=basePath%>js/history.js"></script>
<script type="text/javascript" src="<%=basePath%>js/laydate.js"></script>
</head>
<body>

	<div class="outer-header">
		<div class="inner-banner">
			<div class="inner-banner-user">
				欢迎您， <span class="user-username">${name}</span>
			</div>
		</div>
	</div>

	<div class="ny-header">
		<div class="ny-header-z">
			<ul class="ny-menu">
				<c:forEach items="${menus}" var="menu" varStatus="status">
					<li id="${status.index}"><a href="<%=basePath %>${menu.url}"
						target="_self">${menu.name}</a> <c:if
							test="${menu.subMenus==null}">
							<ul class="sub-menu">
								<c:forEach items="${menu.subMenus}" var="subMenu"
									varStatus="subStatus">
									<li id="SI${subStatus.index}"><a id="S${menu.id}"
										href="<%=basePath %>${menu.url}" target="_self">${menu.name}</a></li>
								</c:forEach>
							</ul>
						</c:if></li>
				</c:forEach>
			</ul>
			<div class="ny-logo"></div>
			<ul class="ny-nav">
				<li><span class="ny-icons"></span><span class="ny-yhm"></span></li>
				<li><span class="ny-fgx"></span></li>
				<li><span class="ny-icons ny-icons2"></span><a
					href="<%=basePath%>logout" class="ny-tc">退出</a></li>
			</ul>
		</div>
	</div>
	<div class="ny-container">
		<div style="height: 48px; line-height: 40px; margin: 0 60px;">
			<span style="padding: 0 10px;"><label>流水线：</label><input
				id="keyword" type="text" class="input_on" value=""
				placeholder="请输入流水线名称或工位名称" /></span> <span style="padding: 0 10px;"><label>开始日期：</label><input
				id="start_time" type="text" class="input_on" value=""
				placeholder="请输入流水线名称" /></span> <span style="padding: 0 10px;"><label>结束日期：</label><input
				id="end_time" type="text" class="input_on" value=""
				placeholder="请输入流水线名称" /></span> <span style="padding: 0 10px;"><button
					type="button" class="btn btn-default" id="searchHistory">搜索</button></span>

			<span style="padding: 0 20px;"><button type="button"
					class="btn btn-default" id="exportHistory">导出</button></span>
		</div>

		<div class="list-container">
			<div class="list01">
				<div class="table">
					<table border="0">
						<thead>
							<tr>
								<td>序号</td>
								<td>流水线</td>
								<td>工位</td>
								<td>测量时间</td>
								<td>持续时长(分钟)</td>
								<td>测量结果</td>
								<td>测量值</td>
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