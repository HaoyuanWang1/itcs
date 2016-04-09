<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="page" uri="/WEB-INF/tags/pagerTag.tld"%>
<c:set var="ctx" value="${basePath}" />

<div style="width: 1000px; margin: 10px auto;">
	<ul class="breadcrumb">
	  <li><a href="#">流程管理</a> <span class="divider">/</span></li>
	  <li class="active">任务控制</li>
	</ul>
	<form id="searchForm" class="form-inline" action="${ctx}/admin/flowCtrl/applyPage">
	  	<label class="control-label" >实体ID</label>
	  	<input type="text" name="search_EQ_entityId" class="span2" value="${searchParam.search_EQ_entityId}">
		<label class="control-label" >申请标号</label>
	  	<input type="text" name="search_LIKE_applyNum" class="span2" value="${searchParam.search_LIKE_applyNum}">
	  	<label class="control-label" >流程ID</label>
	  	<input type="text" name="search_EQ_processId" class="span2" value="${searchParam.search_EQ_processId}">
	  	<input type="hidden" name="search_EQ_taskState" value="1">
	    <input type="submit" class="btn" value="查询"/>
	</form>
	<table class="table table-bordered table-hover table-condensed" style="font-size:12px;">
		<thead>
			<tr>
				<th>实体ID</th>
				<th>申请编号</th>
				<th>申请名称</th>
				<th>申请流程</th>
				<th>状态</th>
				<th>环节</th>
				<th>流程实例ID</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th width="110">
					操作
				</th>
			</tr>
		</thead>
		<tbody id="userReuslt">
		  <c:forEach items="${page.content}" var="apply">
		    <tr>
		    	<td>${apply.applyId}</td>
				<td>${apply.applyNum}</td>
				<td>${apply.applyName}</td>
				<td>${apply.flowDesc}</td>
				<td>${apply.state}</td>
				<td>${apply.nodeDesc}</td>
				<td>${apply.processId}</td>
				<td>${apply.createUser.uid}</td>
				<td>${apply.createTime}</td>
			 	<td>
			 		<a href="${ctx}${apply.applyUrl}" target="_blank">查看</a>
				</td> 	
			</tr>
		  </c:forEach>
		</tbody>
	</table>
	<page:outpage page="${page}" params="${searchParam}" url="/admin/flowCtrl/applyPage" />

</div>