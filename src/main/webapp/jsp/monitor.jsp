<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link type="text/css" rel="stylesheet" href="<%=basePath%>css/reset.css" />
	<link type="text/css" rel="stylesheet" href="<%=basePath%>css/style.css" />
	<script language="javascript">
	var checkedIndex= 0;
	var basePath = '<%=basePath%>';
	</script>
	
	<script type="text/javascript" src="<%=basePath %>js/jquery-1.10.1.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/menu.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jquery.whpage.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/public.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/monitor.js"></script>
	<style type="text/css">
	table th{font-weight: bold;}
	</style>
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
          <div class="ny-logo"></div>
          <ul class="ny-nav"> 
            <li><span class="ny-icons"></span><span class="ny-yhm"></span></li>
            <li><span class="ny-fgx"></span></li>
            <li><span class="ny-icons ny-icons2"></span><a href="<%=basePath%>logout" class="ny-tc">退出</a></li>
          </ul>
      </div>	
 </div>

<div class="ny-container">
	<div class="list-container">
	<div class="diagram-container" id="diagram-container">
		<c:forEach items="${pipelines}" var="pipeline" varStatus="status">
			<div class="diagram"><img onclick="showPipelineDetail(${pipeline.id})" src="<c:url value='/brefDesc.chart?pipeLineId=${pipeline.id}' />"></img></div>
		</c:forEach>
	</div>	
	
		<!-- 翻页 -->
		<div class="fanye">
          <div class="right">
		  </div>
        </div>
	</div>
</div>

<c:forEach items="${stationsMap}" var="stations" varStatus="status">
<c:set value="${fn:length(stations.value)}" var="stationLength"></c:set> 
<c:choose>
<c:when test="${stationLength%2==0}">
<c:set value="${stationLength}" var="xbase"></c:set>
<c:set value="${stationLength/2}" var="baseNum"></c:set>
</c:when>
<c:when test="${stationLength%2!=0}">
<c:set value="${stationLength+1}" var="xbase"></c:set>
<c:set value="${(stationLength+1)/2}" var="baseNum"></c:set>
</c:when>
</c:choose>
<c:set value="${2000/xbase-20}" var="xoffset"></c:set>
<c:set value="${1000/xbase+980}" var="yoffset"></c:set>

<div id="lineDetail_${stations.key}" style="display: none;">
<div class="list-container">
	<div class="list01" style="min-width: 500px; margin-top: 10px;">
		<div class="list-line-name"><h2>Line ${stations.key}</h2></div>
		<div class="tr-proline-top">
			<c:forEach items="${stations.value}" varStatus="mStatus" var="station">
				
				<c:if test="${station.number-baseNum>0}">
				<div id="station_${station.pipelineId}_${station.number}" style="width:22px; height:22px; position:relative; bottom:10px; float:left; left:${2000*station.number/xbase-yoffset}px">
				<c:choose>
				<c:when test="${station.state==0}"><img src="<c:url value='/images/gray.png' />" /> </c:when>
				<c:when test="${station.state==1}"><img src="<c:url value='/images/green.png' />" /> </c:when>
				<c:when test="${station.state==2}"><img src="<c:url value='/images/red.png' />" /> </c:when>
				</c:choose>
				${station.number} 
				</div>
				</c:if>
			</c:forEach>
		</div>
		<div class="tr-proline-bottom">
			<c:forEach items="${stations.value}" varStatus="mStatus" var="station">
				<c:if test="${station.number-baseNum<=0}">
				<div id="station_${station.pipelineId}_${station.number}" style="width:22px; height:22px; position:relative; top:10px; float:left; left:${2000*station.number/xbase-xoffset}px">
				<c:choose>
				<c:when test="${station.state==0}"><img src="<c:url value='/images/gray.png' />" /> </c:when>
				<c:when test="${station.state==1}"><img src="<c:url value='/images/green.png' />" /> </c:when>
				<c:when test="${station.state==2}"><img src="<c:url value='/images/red.png' />" /> </c:when>
				</c:choose>
				${station.number} 
				</div>
				</c:if>
			</c:forEach>
		</div>
		<fmt:formatNumber type="number" value="${stationLength/6}" maxFractionDigits="0" var="baseLoop"></fmt:formatNumber>
		
		<div class="summary-table" >
		<table style="width:1000px; margin:auto;" align="center">
		<c:forEach begin="1" end="${baseLoop}" varStatus="status">
			<tr><th>No</th>
			<c:forEach items="${stations.value}" begin="${(status.index-1)*6}" end="${status.index*6-1}" var="station">
			<td>${station.number}</td>
			</c:forEach>
			</tr>
			
			<tr><th>Name</th>
			<c:forEach items="${stations.value}" begin="${(status.index-1)*6}" end="${status.index*6-1}" var="station">
			<td>${station.name}</td>
			</c:forEach>
			</tr>
			
		</c:forEach>
		</table>
		</div>
	</div>
	</div>
	</div>
</c:forEach>
</body>
</html>
