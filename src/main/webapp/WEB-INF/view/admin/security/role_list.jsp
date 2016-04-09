<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="page" uri="/WEB-INF/tags/pagerTag.tld"%>
<c:set var="ctx" value="${basePath}" />
<html>
	<head>
		<title>角色管理</title>
		<link type="text/css" rel="stylesheet" href="${ctx }/static/css/diff.css" />
	</head>
	<body>
		<ul class="breadcrumb">
			<li><a href="${ctx}/">安全管理</a><span class="divider">/</span></li>
			<li class="active">角色管理</li>
		</ul>
		<form id="searchForm" class="form-inline" action="${ctx}/role/page">
		  	<label class="control-label" >角色KEY</label>
		  	<input type="text" name="search_LIKE_rid" class="span2" value="${searchParam.search_LIKE_rid}">
			<label class="control-label" >角色名称</label>
		  	<input type="text" name="search_LIKE_name" class="span2" value="${searchParam.search_LIKE_name}">
		    <input type="submit" class="btn" value="查询"/>
		    <a href="${ctx}/role/edit/0" target="_blank" class="btn" >新建</a>
		</form>
		<table class="table table-bordered table-hover table-condensed">
			<thead>
				<tr>
					<th>角色KEY</th>
					<th>角色名称</th>
					<th class="span2">操作</th>
				</tr>
			</thead>
			<tbody id="userReuslt">
			  <c:forEach items="${page.content}" var="role">
			    <tr>
					<td>${role.rid}</td>
					<td>${role.name}</td>
				 	<td>
						<a href="${ctx}/role/edit/${role.id}" target="_blank" class="table-btn" > 编辑 </a>
							<a target="_blank" href="${ctx}/userRole/loadRoleUser?roleId=${role.id}">角色维护</a>
					</td> 	
				</tr>
			  </c:forEach>
			</tbody>
		</table>
		<page:outpage page="${page}" params="${searchParam}" url="/role/page" />
	
		<div id="user_modal" class="modal hide fade" tabindex="-1" >
		  <div class="modal-header">
		    <h3 id="myModalLabel">岗位信息</h3>
		  </div>
		  <div id="user_modal_body" class="modal-body">
		  </div>
		  <div class="modal-footer">
		  	<input type="button" class="btn btn-primary" onclick="saveRole();" value="保存"/>
		    <input type="button" class="btn" data-dismiss="modal" value="关闭"/>
		  </div>
		</div>
	
		<div id="role_modal" class="modal hide fade" tabindex="-1">
			<div class="modal-header">
				<h3 id="myModalLabel">资源信息</h3>
			</div>
			<div id="role_modal_body" class="modal-body">
				<form class="form-horizontal" id="roleForm">
					<input type="hidden" id="id" name="id">
					<input type="hidden" id="parentId" name="parentId">
					<div class="control-group">
						<label class="control-label" for="name">名称:</label>
						<div class="controls">
							<input type="text" id="name" name="name">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="pageUrl">链接地址:</label>
						<div class="controls">
							<input type="text" id="pageUrl" name="pageUrl">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="pageUrl">是否菜单:</label>
						<div class="controls">
							<select id="menuItem" name="menuItem">
								<option value="">请选择</option>
								<option value="1">是</option>
								<option value="0">否</option>
							</select>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="menuText">菜单名称:</label>
						<div class="controls">
							<input type="text" id="menuText" name="menuText">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="type">资源类型:</label>
						<div class="controls">
							<select id="type" name="type">
								<option value="">请选择</option>
								<option value="0">公共资源</option>
								<option value="1">用户资源</option>
								<option value="2">角色资源</option>
							</select>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="orderNum">排序号:</label>
						<div class="controls">
							<input type="text" id="orderNum" name="orderNum">
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<input type="button" class="btn btn-primary" data-listen="saveRole" value="保存" /> 
				<input type="button" class="btn" data-dismiss="modal" value="关闭" />
			</div>
		</div>

		<div id="roleUser_modal" class="modal hide fade role-user-modal"></div>
	<script type="text/javascript" >
			function editRole(roleId){
				$("#user_modal_body").load("${ctx}/user/panel/"+userId);
				$('#user_modal').modal('show');
			}
			function saveUser(){
				$.post("${ctx}/user/save",
					$('#userForm').serialize(),
					function(data){
						$('#user_modal').modal('hide');
						window.location.reload();
				});
			}
			function saveRoleUser(){
				var userStr = [];
				$this = $("#roleUserForm");
				$this.find('[name="userCheck"]').each(function() {
					if (this.checked) {
						userStr.push(this.value);
					}
				});
				$this.find('[name="userRoleStr"]').val(userStr.join(','));
				$.post(webContext + "/userRole/saveUserRole", $('#roleUserForm')
						.serialize(), function(data) {
					$('#roleUser_modal').modal('hide');
				});
			}
			
			function editRoleUser(url, roleId) {
				$._post(url, 'html')
					.done(function (html) {
						$('#roleUser_modal').modal('show').html(html);
						getUserPage({roleId: roleId});
					});
			}
			
			function getUserPage(param) {
				$._post(webContext + '/userRole/findUserPage/panel', param, 'html')
					.done(function (html) {
						$('#roleUserDiv').html(html);
					});
			}
		$(function () {
			$(document)
				.on('click.edit-role-user', '.edit-role-user', function () {
					editRoleUser(this.href, $(this).data('id'));
					return false;
				})
				;
			
			$('#roleUser_modal')
				.on('click.search-user-in-role', '.page-goto', function () {
					$('#roleUserDiv')._load(this.href);
					return false;
				})
				.on('click.search-user', '.search-user', function () {
					getUserPage($('#searchUserForm').serializeArray());
				})
				.on('click.save-role-user', '.save-role-user', function () {
					var $form = $('#roleUserForm'),
						$userRoleStr = $('#userRoleStr'),
						userRoleStr = [];
					$form.find('.box-item')
						.each(function () {
							if (this.checked) {
								userRoleStr.push(this.value);
							}
						});
					$userRoleStr.val(userRoleStr.join(','));
					
					$._post(webContext + '/userRole/saveUserRole', $form.serializeArray())
						.done(function (data) {
							$.alert('保存角色用户信息成功', 1500)
								.done(function () {
									$('#roleUser_modal').modal('hide');
								});
						});
				});
			
		});
		</script>
	</body>
</html>