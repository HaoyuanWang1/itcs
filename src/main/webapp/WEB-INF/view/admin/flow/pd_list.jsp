<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="page" uri="/WEB-INF/tags/pagerTag.tld"%>
<c:set var="ctx" value="${basePath}" />

<div style="width: 1000px; margin: 10px auto;">
	<ul class="breadcrumb">
	  <li><a href="#">流程引擎管理</a> <span class="divider">/</span></li>
	  <li class="active">流程定义列表</li>
	</ul>
	<form id="searchForm" class="form-inline" action="${ctx}/admin/pd/page">
	  	<label class="control-label" >流程KEY：</label>
	  	<input type="text" name="search_LIKE_pid" class="span2" value="${searchParam.search_LIKE_pid}" placeholder="英文">
		<label class="control-label" >流程名称：</label>
	  	<input type="text" name="search_LIKE_name" class="span2" value="${searchParam.search_LIKE_name}" placeholder="中文">
	  	<label class="control-label" >流程状态：</label>
	  	<select name="search_EQ_state">
	  		<option value="">请选择</option>
			<option value="0" <c:if test="${searchParam.search_EQ_state==0}">selected</c:if>>草稿</option>
			<option value="1" <c:if test="${searchParam.search_EQ_state==1}">selected</c:if>>发布</option>
			<option value="2" <c:if test="${searchParam.search_EQ_state==2}">selected</c:if>>过期</option>
		</select>
	    <input type="submit" class="btn" value="查询"/>
	    <div style="margin-top:10px;">
		<a href="${ctx}/admin/pd/edit/0" target="_blank" class="btn">新建</a>
	    <input type="button" class="btn" onclick="reFresh();" value="刷新"/>
	    </div>
	</form>
	<table class="table table-bordered table-hover table-condensed">
		<thead>
			<tr>
				<th>流程ID</th>
				<th>流程名称</th>
				<th>流程版本</th>
				<th>流程状态</th>
				<th>创建时间</th>
				<th width="110">
					操作
				</th>
			</tr>
		</thead>
		<tbody id="userReuslt">
		  <c:forEach items="${page.content}" var="fdItem">
		    <tr>
				<td>${fdItem.pid}</td>
				<td>${fdItem.name}</td>
				<td>${fdItem.version}</td>
				<td>${fdItem.stateText}</td>
				<td>${fdItem.createTime}</td>
			 	<td width="150">
				<a href="${ctx}/admin/pd/edit/${fdItem.id}" class="table-btn"> 编辑 </a>
				<a href="${ctx}/admin/processProgress/list?pdId=${fdItem.id}" class="table-btn"> 流程进度编辑 </a>
				</td>
			</tr>
		  </c:forEach>
		</tbody>
	</table>
	<page:outpage page="${page}" params="${searchParam}" url="/admin/pd/page" />

</div>
<script type="text/javascript" >
	function reFresh(){
		$("#searchForm").submit();
	}
</script>