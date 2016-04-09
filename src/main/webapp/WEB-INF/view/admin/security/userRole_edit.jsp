<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	<h3>人员角色维护</h3>
</div>
<div class="modal-body">
	<form class="form-horizontal" name="userRoleForm" id="userRoleForm">
		<table class="table table-bordered table-hover table-condensed">
			<thead>
				<tr>
					<th width="15%"><input type="checkbox" class="check-all"></th>
					<th width="90%">角色名称</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${allRoles}" var="role">
					<tr>
						<td><input type="checkbox" class="check-one" value="${role.id}" data-id="${role.id}"></td>
						<td>${role.name}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<input type="hidden" name="userId" value="${userId}"/>
		<input type="hidden" id="roleIds" name="roleIds" value="${roleIds}"/>
	</form>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-primary" data-listen="saveUserRole" data-target="#userRoleForm" >保存</button>
<!-- 	<input type="button" class="btn" data-dismiss="modal" value="关闭"/> -->
</div>

