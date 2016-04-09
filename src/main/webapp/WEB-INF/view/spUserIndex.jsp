<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${basePath}" />
	<head>
		<title>首页</title>
		<script type="text/javascript">F.run('static/js/event/organizationIndex');</script>
		<script type="text/javascript">
			var tenantId = [];	//客户ID数组
			var tenantData = "";	//按客户分组事件数量数组
			var tenantGroupEvents = [];	//按客户分组事件ID集合数组
			
			var spUser = [];	//处理客户经理或服务经理
			var spUserData = [];//处理客户经理或服务经理待处理事件数量数组
			var userGroupEvents = [];	//按客户分组事件ID集合数组
		</script>
	 	<c:forEach items="${tenantGroupDatas }" var="tenantGroupData" varStatus="status">
			<script type="text/javascript">
				tenantData += ',{label:\'${tenantGroupData.tenant.name}\',data:[${tenantGroupData.unSloveCount}]}';
				tenantId['${status.index }'] = ['${tenantGroupData.tenant.id}'];
				tenantGroupEvents['${status.index }'] = ['${tenantGroupData.unSloveEventIdStr}'];
			</script>
		</c:forEach>
		<c:forEach items="${userGroupDatas }" var="userGroupData" varStatus="status">
			<script type="text/javascript">
				spUserData['${status.index }'] = ['${status.index }'*1,0,'${userGroupData.unSloveCount}'];
				spUser['${status.index }'] = ['${status.index }'*1,'${userGroupData.spUser.userName}','${userGroupData.spUser.id}'];
				userGroupEvents['${status.index }'] = ['${userGroupData.unSloveEventIdStr}'];
			</script>
		</c:forEach>
	</head>
	<body>
		<h4>所有待处理的问题：</h4>
		
		<c:choose>
         	<c:when test="${!empty tenantGroupDatas }">
         		<div id="placeholderT" class="placeholderTDiv"></div>
         	</c:when>
         	<c:otherwise>
         		<div class="alert">暂无图形记录。</div>
         	</c:otherwise>
         </c:choose>
         
         <c:choose>
         	<c:when test="${!empty userGroupDatas }">
         		<div id="placeholderTM" class="placeholderTMDiv"></div>
         	</c:when>
         	<c:otherwise>
         		<div class="alert">暂无图形记录。</div>
         	</c:otherwise>
         </c:choose>
		
		<div class="unconfirmed">
			<h4>待您处理的问题：</h4>
			<div id="taskSignerPage"></div>
		</div>
		<div class="modal fade hide" id="eventResuleTable"></div>
	</body>
</html>