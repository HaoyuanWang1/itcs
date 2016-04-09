<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${basePath}" />
<html>
	<head>
		<meta charset="utf-8" />
		<title>IT客户支持中心 <sitemesh:title /></title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta http-equiv="Cache-Control" content="no-store" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<link rel="shortcut icon" href="${ctx}/static/images/favicon.ico" type="image/x-icon" />
		<link rel="bookmark" href="${ctx}/static/images/favicon.ico" type="image/x-icon" />
		<script type="text/javascript">
			var webContext= '${basePath}';
		</script>
		<link href="${ctx}/static/core/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
		<link href="${ctx}/static/css/global.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="${ctx}/static/core/seajs/sea.js"></script>
		<script type="text/javascript" src="${ctx}/static/config/main.js"></script>
		<sitemesh:head />
	</head>

	<body>
		<c:choose>
			<c:when test="${isUser}">
				<jsp:include page="/WEB-INF/view/customerUserHeader.jsp"></jsp:include>
			</c:when>
			<c:otherwise>
				<jsp:include page="/WEB-INF/view/organizationHeader.jsp"></jsp:include>
			</c:otherwise>
		</c:choose>
		
		<div class="container">
			<sitemesh:body />
		</div>
		
		<c:if test="${!empty flowAuth}">
			<jsp:include page="/WEB-INF/view/workflow/workflow_operate.jsp"></jsp:include>
		</c:if>
	</body>
</html>