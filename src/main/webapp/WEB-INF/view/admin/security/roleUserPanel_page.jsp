<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="page" uri="/WEB-INF/tags/pagerTag.tld"%>
<c:set var="ctx" value="${basePath}" />

<form id="searchUserForm" class="form-inline">
	<label class="control-label"> 用户ITCODE</label> 
	<input type="text" name="search_LIKE_uid" class="span2" value="${searchParam.search_LIKE_uid} ">
	<label class="control-label">用户姓名 </label> 
	<input type="text" name="search_LIKE_userName" class="span2" value="${searchParam.search_LIKE_userName} "> 
	<input type="hidden" name="roleId" value="${roleId }" />
	<input type="button" class="btn search-user" value="查询" />
</form>
<form class="form-horizontal" name="roleUserForm" id="roleUserForm">
	<table class="table table-bordered table-hover table-condensed">
		<thead>
			<tr>
				<th class="span1">
				<input type="checkbox" class="box-all"></th>
				<th>UID</th>
				<th>用户名</th>
				<th>是否可用</th>
			</tr>
		</thead>
		<tbody id="userReuslt">
			<c:forEach items="${page.content}" var="userr">
				<tr>
					<td><input type="checkbox" class="box-item" value="${userr.id }"></td>
					<td>${userr.uid}</td>
					<td>${userr.userName}</td>
					<td>${userr.enabled}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<input type="hidden" name="role.id" value="${roleId} " /> 
	<input type="hidden" id="userRoleStr" name="userRoleStr" value="${roleStr} " />
</form>
<page:outpage page="${page}" params="${searchParam}" url="/userRole/findUserPage/panel" />

<!-- <script type = "text/javascript"> -->
<!-- // 	$(function() { -->
<!-- // 		$("#checkAll").click(function() { -->
<!-- // 			$('input[name="userCheck"]').prop("checked", this.checked); -->
<!-- // 		}); -->
<!-- // 		var userStr = $("#userRoleStr").val(); -->
<!-- // 		if (userStr && (userStr = userStr.split(','))) { -->
<!-- // 			$.each(roleStr, -->
<!-- // 					function(i, r) { -->
<!-- // 						$userRoleForm.find('[name="userCheck"][value="' + r -->
<!-- // 								+ '"]')[0].checked = true; -->
<!-- // 					}); -->
<!-- // 		} -->
<!-- // 	}); -->
<!-- </script > -->
