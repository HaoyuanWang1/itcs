<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="page" uri="/WEB-INF/tags/pagerTag.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${basePath}" />
<html>
	<head>
		<title>用户信息管理</title>
	</head>
	<body>
		<ul class="breadcrumb">
   			<li><a href="${ctx}/">系统设置</a> <span class="divider">/</span></li>
   			<li class="active">用户管理</li>
   		</ul>
		
		<form class="form-horizontal" id="searchForm" method="post" action="${ctx}/user/page">
			<div class="control-groups">
				<div class="control-group">
					<label class="control-label">用户ID：</label>
					<div class="controls"><input type="text" name="search_LIKE_uid" value="${searchParam.search_LIKE_uid}" /></div>
				</div>
				<div class="control-group">
					<label class="control-label">用户姓名：</label>
					<div class="controls"><input type="text" name="search_LIKE_userName" value="${searchParam.search_LIKE_userName}" /></div>
				</div>
			</div>
			<div class="control-groups">
				<div class="control-group">
					<label class="control-label">是否可用：</label>
					<div class="controls">
						<select name="search_EQ_enabled">
							<option value="">请选择</option>
							<option value="1"
								<c:if test="${searchParam.search_EQ_enabled=='1' }">selected="selected" </c:if>>是</option>
							<option value="0"
								<c:if test="${searchParam.search_EQ_enabled=='0' }">selected="selected" </c:if>>否</option>
						</select>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">所属客户：</label>
					<div class="controls"><input type="text" name="search_LIKE_tenant_name" value="${searchParam.search_LIKE_tenant_name}" /></div>
				</div>
			</div>
			<div class="control-groups">
				<div class="control-group" >
					<div class="controls" >
						<button type="submit" class="btn" >查询</button>
				   		<button type="reset" class="btn" data-reset="#searchForm">重置</button>
				   		<a class="btn" href="${ctx}/user/panel/edit/0" data-load="#userInfoEditBox" data-load-callback="initUserInfoEditBox">新增用户</a>
					</div>
				</div>
			</div>
		</form>
		
		<c:choose>
			<c:when test="${fn:length(page.content)>0}">
				<table class="table table-bordered table-hover">
					<thead>
						<tr>
							<th>用户ID</th>
							<th>用户姓名</th>
							<th>隶属组织</th>
							<th>是否可用</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
					  <c:forEach items="${page.content}" var="user">
					    <tr>
							<td>${user.uid}<input type="hidden" value="${user.uid}" id="uid"></td>
							<td>${user.userName}</td>
							<td>${user.tenant.name}</td>
							<td>${user.enabledText}</td>
						 	<td>
								<a class="btn-link" href="${ctx}/userRole/loadUserRole/panel/${user.id}" data-load="#user_role_modal" data-load-callback="completeRoleCheckbox">角色维护</a>
								&nbsp;|&nbsp;
								<a class="btn-link" href="${ctx}/user/panel/edit/${user.uid}" data-load="#userInfoEditBox" data-load-callback="initUserInfoEditBox">编辑</a>
								 <!-- 普通用户才有密码重置功能 -->
								<c:if test="${!user.isAdUser}">  
									&nbsp;|&nbsp;
									<a class="btn-link" href="javascript:;"  data-listen="pwdReset" data-id="${user.uid }">密码重置</a>
								</c:if> 
							</td>
						</tr>
					  </c:forEach>
					</tbody>
				</table>
				<page:outpage page="${page}" params="${searchParam}" url="/user/page" />
			</c:when>
			<c:otherwise>
				<div class="alert">暂无记录</div>
			</c:otherwise>
		</c:choose>
		
		<div class="modal fade hide"  id="userEditBox"></div>
		<div class="modal fade hide"  id="userInfoEditBox"></div>
		<div id="user_role_modal" class="modal hide fade"></div>
		<script type="text/javascript">F.run('modules/security/user-edit');</script>
	</body>
</html>







