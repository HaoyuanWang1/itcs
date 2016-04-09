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
		<li class="active">定时任务</li>
	</ul>
	<table class="table table-bordered table-hover table-condensed">
		<thead>
			<tr>
				<th>任务ID</th>
				<th>执行规则</th>
				<th>是否在用</th>
				<th>描述信息</th>
				<th>上次执行时间</th>
				<th>下次执行时间</th>
				<th>执行完毕状态</th>
				<th width="110">
					<a href="${ctx}/system/scheduler/panel/schedulerEdit/0" data-load="#schedulerEidtDiv">新增</a>
				</th>
			</tr>
		</thead>
		<tbody>
		  <c:forEach items="${list}" var="item">
		    <tr>
				<td>${item.jobId}</td>
				<td>${item.cronExpression}</td>
				<td>${item.jobStatus}</td>
				<td>${item.jobName}</td>
				<td>${item.lastExecuteTime}</td>
				<td>${item.nextExecuteTime}</td>
				<td>${item.lastStatus}</td>
			 	<td width="150">
			 		<a href="${ctx}/system/scheduler/panel/schedulerEdit/${item.jobId}" data-load="#schedulerEidtDiv">编辑</a>
				</td>
			</tr>
		  </c:forEach>
		</tbody>
	</table>
	<div id="schedulerEidtDiv" class="modal hide fade"></div>
	<script type="text/javascript">F.run('modules/system/scheduler');</script>
	</body>
</html>