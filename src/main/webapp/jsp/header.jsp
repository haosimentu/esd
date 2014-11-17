<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
	String path1 = request.getContextPath();
	String basePath1 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link type="text/css" rel="stylesheet" href="<%=basePath1%>css/reset.css" />
	<link type="text/css" rel="stylesheet" href="<%=basePath1%>css/style.css" />
	<script type="text/javascript" src="<%=basePath1 %>js/jquery-1.10.1.js"></script>
	<script type="text/javascript" src="<%=basePath1 %>js/menu.js"></script>
	<script type="text/javascript" src="<%=basePath1%>js/jquery.whpage.js"></script>
	<script type="text/javascript" src="<%=basePath1%>js/public.min.js"></script>
	<script language="javascript">
	$(function(){
			var checkedIndex=0;
			if(checkedIndex==null)
			{
				checkedIndex=0;
			}
			
			var checkedId="#" + checkedIndex;
			$(checkedId).addClass("li_mouseover");

			$(".ny-header-z .ny-menu li").each(function(){
				$(this).mouseenter(function(){
					if($(this).attr("id") != checkedIndex)
						$(this).addClass("li_mouseover");
				});
				
				$(this).mouseleave(function(){
					if($(this).attr("id") != checkedIndex )
						$(this).removeClass("li_mouseover");
				});
			});
		});
	</script>
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
			<li  id="${status.index}"><a href="<%=basePath1 %>${menu.url}" target="_self">${menu.name}</a>
			<c:if test="${menu.subMenus==null}">
				<ul class="sub-menu">
					<c:forEach items="${menu.subMenus}" var="subMenu" varStatus="subStatus">
						<li  id="SI${subStatus.index}"><a id="S${menu.id}" href="<%=basePath1 %>${menu.url}" target="_self">${menu.name}</a></li>
					</c:forEach>
				</ul>
			</c:if>
			</li>
			</c:forEach>
          </ul>
          <div class="ny-logo"></div>
          <ul class="ny-nav"> 
            <li><span class="ny-icons"></span><span class="ny-yhm"></span></li>
            <li><span class="ny-fgx"></span></li>
            <li><span class="ny-icons ny-icons2"></span><a href="<%=basePath1%>logout" class="ny-tc">退出</a></li>
          </ul>
      </div>	
 </div>
 
</body>
</html>