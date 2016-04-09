 <!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="page" uri="/WEB-INF/tags/pagerTag.tld"%>
<c:set var="ctx" value="${basePath}" />
<html>
	<head>
		<title>角色用户管理</title>
		<script type="text/javascript">F.run('static/js/role/role_user_edit')</script>
	</head>
	<body>
		<form id="userRoleForm" class="form-horizontal" >
			<h3>角色名称：${role.name}</h3>
			<div class="control-group">
				<label class="control-label" for="user.id">用户：</label>
				<div class="controls">
					<input type="hidden" name="user.id" id="roleUser"/>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<input type="button" value="保存" class="btn btn-diy" data-listen="saveUserRole" data-target="#userRoleForm"/>
				</div>
			</div>
			<input type="hidden" name="role.id" value="${role.id}" />
		</form>
		<c:choose>
			<c:when test="${fn:length(page.content)>0}">
				<div class="search-result">
					<table class="table table-bordered table-hover table-condensed">
						<thead>
							<tr>
								<th>用户</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
						  <c:forEach items="${page.content}" var="ru" varStatus="status">
						    <tr>
								<td>${ru.user.userText}</td>
							 	<td><a href="${ctx}/userRole/deleteUserRole?id=${ru.id}" data-remove="">删除</a></td> 	
							</tr>
						  </c:forEach>
						</tbody>
					</table>
				</div>
				<page:outpage page="${page}" params="${searchParam}" url="/userRole/loadRoleUser?roleId=${role.id}" />
			</c:when>
			<c:otherwise>
				<div class="search-result">
					<div class="alert">该角色下没有用户，请先添加。</div>
				</div>
			</c:otherwise>
		</c:choose>
	</body>
</html> 






