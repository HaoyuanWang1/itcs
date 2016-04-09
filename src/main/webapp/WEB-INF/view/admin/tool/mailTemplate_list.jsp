<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${basePath}" />
<div style="width: 1000px; margin: 10px auto;">
	<ul class="breadcrumb">
	  <li><a href="#">系统设置</a> <span class="divider">/</span></li>
	  <li class="active">邮件模板管理</li>
	</ul>
	<table class="table table-bordered table-hover table-condensed">
		<thead>
			<tr>
				<th>模板ID</th>
				<th>名称描述</th>
				<th>邮件标题</th>
				<th width="110">
					操作<a href="${ctx}/admin/mailTemplate/edit/0" class="table-btn">新增</a>
				</th>
			</tr>
		</thead>
		<tbody>
		  <c:forEach items="${list}" var="item">
		    <tr>
				<td>${item.code}</td>
				<td>${item.descn}</td>
				<td>${item.title}</td>
			 	<td width="150">
			 		<a href="${ctx}/admin/mailTemplate/edit/${item.id}" class="table-btn"> 编辑 </a>
				</td>
			</tr>
		  </c:forEach>
		</tbody>
	</table>
</div>