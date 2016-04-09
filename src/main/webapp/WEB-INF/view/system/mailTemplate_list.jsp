<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${basePath}" />
<html>
	<head>
		<title>邮件模板管理</title>
	</head>
	<body>
		<ul class="breadcrumb">
   			<li><a href="javascript:void(0)">系统设置</a> <span class="divider">/</span></li>
   			<li class="active">邮件模板</li>
   		</ul>
   		
		<table class="table table-bordered table-hover table-condensed">
			<thead>
				<tr>
					<th>模板ID</th>
					<th>名称描述</th>
					<th>邮件标题</th>
					<th>
						<a href="${ctx}/system/mailTemplate/edit/0">新增</a>
					</th>
				</tr>
			</thead>
			<tbody>
			  <c:forEach items="${list}" var="item">
			    <tr>
					<td>${item.code}</td>
					<td>${item.descn}</td>
					<td>${item.title}</td>
				 	<td>
				 		<a href="${ctx}/system/mailTemplate/edit/${item.id}">编辑</a>
					</td>
				</tr>
			  </c:forEach>
			</tbody>
		</table>
	</body>
</html>