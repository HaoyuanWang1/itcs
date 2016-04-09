<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="page" uri="/WEB-INF/tags/pagerTag.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${basePath}" />
<html>
	<head>
		<title>任务委派</title>
	</head>
	<body>
		<ul class="breadcrumb">
   			<li><a href="javascript:void(0)">流程引擎</a> <span class="divider">/</span></li>
   			<li class="active">任务委派</li>
   		</ul>
		
		<form id="searchForm" class="form-inline" action="${ctx}/flow/ctrl/taskPage" method="post">
			<label>实体ID：</label>
		  	<input type="text" name="search_EQ_entityId" class="span2" value="${searchParam.search_EQ_entityId}">
			<label>任务ID：</label>
		  	<input type="text" name="search_EQ_taskId" class="span2" value="${searchParam.search_EQ_taskId}">
		  	<label>流程ID：</label>
		  	<input type="text" name="search_EQ_processId" class="span2" value="${searchParam.search_EQ_processId}">
		    <input type="submit" class="btn" value="查询"/>
		</form>
		
		<c:choose>
			<c:when test="${fn:length(page.content)>0}">
				<div class="search-result">
					<table class="table table-bordered table-hover table-condensed table-detail">
						<thead>
							<tr>
								<th>任务ID</th>
								<th>进入时间</th>
								<th>实体ID</th>
								<th>环节</th>
								<th>流程定义</th>
								<th>实例ID</th>
								<th>状态</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
						  <c:forEach items="${page.content}" var="item">
						    <tr>
								<td>${item.taskId}</td>
								<td>${item.createTime}</td>
								<td>${item.entityId}</td>
								<td>${item.nodeDesc}</td>
								<td>${item.pdName}</td>
								<td>${item.processId}</td>
								<td>${item.taskState}</td>
							 	<td>
							 		<c:if test="${item.taskState==1}">
									<div class="btn-group">
										<a type="button" class="btn btn-small" href="${ctx}${item.entity.applyUrl}" target="_blank">界面</a>
										<a class="btn btn-small" data-load="#taskCtrlSign" href="${ctx}/flow/ctrl/panel/sign/${item.taskId}">审批</a>
										<a class="btn btn-small" data-load="#taskCtrlBack" href="${ctx}/flow/ctrl/panel/back/${item.taskId}">驳回</a>
										<a class="btn btn-small" data-load="#taskCtrlEnd" href="${ctx}/flow/ctrl/panel/end/${item.taskId}">撤单</a>
										<a class="btn btn-small" data-load="#taskCtrlTransfer" href="${ctx}/flow/ctrl/panel/transfer/${item.taskId}">转办</a>
										<a class="btn btn-small" data-load="#taskCtrlFree" href="${ctx}/flow/ctrl/panel/free/${item.taskId}">自由</a>
									</div>
									</c:if>
								</td> 	
							</tr>
						  </c:forEach>
						</tbody>
					</table>
				</div>
				<page:outpage page="${page}" params="${searchParam}" url="/flow/ctrl/taskPage" />
			</c:when>
			<c:otherwise>
				<div class="search-result">
					<div class="alert">未检索到满足条件的任务</div>
				</div>
			</c:otherwise>
		</c:choose>
		
		<div class="modal fade hide" id="taskCtrlSign"></div>
		<div class="modal fade hide" id="taskCtrlBack"></div>
		<div class="modal fade hide" id="taskCtrlEnd"></div>
		<div class="modal fade hide" id="taskCtrlTransfer"></div>
		<div class="modal fade hide" id="taskCtrlFree"></div>
		
		<script type="text/javascript">F.run('modules/flow/flow_admin_ctrl')</script>
	</body>
</html>