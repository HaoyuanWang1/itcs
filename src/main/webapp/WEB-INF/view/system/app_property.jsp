<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${basePath}" />
<html>
	<head>
		<title>系统属性设置</title>
	</head>
	<body>
		<ul class="breadcrumb">
   			<li><a href="javascript:void(0)">系统设置</a> <span class="divider">/</span></li>
   			<li class="active">系统属性设置</li>
   		</ul>
	
		<table class="table table-bordered table-hover table-condensed">
			<thead>
				<tr>
					<th>属性名</th>
					<th>属性描述</th>
					<th>属性值</th>
					<th><a href="${ctx}/system/property/panel/edit/0" data-load="#propertyEditModal">新增</a></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="item">
					<tr>
						<td>${item.name}</td>
						<td>${item.descn}</td>
						<td class="bd">${item.encrytFlag == 1 ? '已加密' : item.value}</td>
						<td><a href="${ctx}/system/property/panel/edit/${item.id}" data-load="#propertyEditModal">编辑</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	
		<div id="propertyEditModal" class="modal hide fade"></div>
		<script type="text/javascript">F.run('modules/system/app_property');</script>
	</body>
</html>